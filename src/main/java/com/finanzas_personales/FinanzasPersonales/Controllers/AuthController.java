package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.Services.JwtUtilService;
import com.finanzas_personales.FinanzasPersonales.dto.AuthRequestDto;
import com.finanzas_personales.FinanzasPersonales.dto.AuthResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ICategoriesRepository categoriesRepository;

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {
        try {
            // Verificar si el correo existe en la base de datos
            UserModel userModel = userRepository.findByEmail(authRequestDto.getEmail());
            if (userModel == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Email not found",
                        "message", "The provided email does not exist in our records."
                ));
            }

            // Verificar la contraseña
            if (!passwordEncoder.matches(authRequestDto.getPassword(), userModel.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Incorrect password",
                        "message", "The password you entered is incorrect."
                ));
            }

            // Generar JWT
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            String jwt = this.jwtUtilService.generateToken(userDetails, userModel.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());

            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "refreshToken", refreshToken
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Unexpected error",
                    "message", e.getMessage()
            ));
        }
    }


    @PostMapping("/register")
    @Transactional // Garantiza que todas las operaciones sean atómicas
    public ResponseEntity<?> register(@RequestBody UserModel userModel) {
        try {
            // Verificar si el correo existe en la base de datos
            UserModel userModelres = userRepository.findByEmail(userModel.getEmail());
            if (userModelres != null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Email exist",
                        "message", "The provided email already exists."
                ));
            }

            // Encriptar la contraseña
            String encodedPassword = passwordEncoder.encode(userModel.getPassword());
            userModel.setPassword(encodedPassword);

            // Guardar el usuario
            UserModel savedUser = userRepository.save(userModel);
            if (savedUser == null || savedUser.getId() == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "error", "User not saved",
                        "message", "Failed to save the user."
                ));
            }

            // Crear las categorías predeterminadas para el nuevo usuario
            List<CategoriesModel> defaultCategories = new ArrayList<>();
            try {
                defaultCategories.add(new CategoriesModel("Alimentación", typeENUM.Gasto, loadImage("gastos/alimentacion.png"), savedUser, "#6DAFD4"));
                defaultCategories.add(new CategoriesModel("Casa", typeENUM.Gasto, loadImage("gastos/casa.png"), savedUser, "#3274C7"));
                defaultCategories.add(new CategoriesModel("Educación", typeENUM.Gasto, loadImage("gastos/educacion.png"), savedUser, "#D14E81"));
                defaultCategories.add(new CategoriesModel("Familia", typeENUM.Gasto, loadImage("gastos/familia.png"), savedUser, "#6B2DBA"));
                defaultCategories.add(new CategoriesModel("Otros", typeENUM.Gasto, loadImage("gastos/otros.png"), savedUser, "#2C9C23"));
                defaultCategories.add(new CategoriesModel("Regalos", typeENUM.Gasto, loadImage("gastos/regalos.png"), savedUser, "#92AC8B"));
                defaultCategories.add(new CategoriesModel("Salud", typeENUM.Gasto, loadImage("gastos/salud.png"), savedUser, "#E33939"));
                defaultCategories.add(new CategoriesModel("Transporte", typeENUM.Gasto, loadImage("gastos/transporte.png"), savedUser, "#30C2B0"));

                defaultCategories.add(new CategoriesModel("Interés", typeENUM.Ingreso, loadImage("ingresos/interes.png"), savedUser, "#63A448"));
                defaultCategories.add(new CategoriesModel("Regalo", typeENUM.Ingreso, loadImage("ingresos/regalo.png"), savedUser, "#CD4C83"));
                defaultCategories.add(new CategoriesModel("Salario", typeENUM.Ingreso, loadImage("ingresos/salario.png"), savedUser, "#3675C7"));
                defaultCategories.add(new CategoriesModel("Otros", typeENUM.Ingreso, loadImage("gastos/otros.png"), savedUser, "#2C9C23"));
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "error", "Image loading error",
                        "message", "Error loading images: " + e.getMessage()
                ));
            }

            // Guardar las categorías
            categoriesRepository.saveAll(defaultCategories);

            // Validar que las categorías se hayan guardado
            List<CategoriesModel> savedCategories = categoriesRepository.findByUserId(savedUser);
            if (savedCategories.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "error", "Categories not saved",
                        "message", "Failed to create default categories."
                ));
            }

            // Generar JWT
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(savedUser.getEmail());
            String jwt = this.jwtUtilService.generateToken(userDetails, savedUser.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, savedUser.getRole());

            return ResponseEntity.ok(Map.of(
                    "message", "success",
                    "token", jwt,
                    "refreshToken", refreshToken
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Unexpected error",
                    "message", e.getMessage()
            ));
        }
    }



    @PostMapping("/refresh")
    public ResponseEntity<?> auth(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtilService.extractUsername(refreshToken);
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Verificación de que el usuario exista
            UserModel userModel = userRepository.findByEmail(username);
            if (userModel == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not found.");
            }

            if (jwtUtilService.validateToken(refreshToken, userDetails)) {
                String newJwt = jwtUtilService.generateToken(userDetails, userModel.getRole());
                String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());

                AuthResponseDto authResponseDto = new AuthResponseDto();
                authResponseDto.setToken(newJwt);
                authResponseDto.setRefreshToken(newRefreshToken);

                return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error refresh token:::" + e.getMessage());
        }
    }


    public byte[] loadImage(String imagePath) throws IOException {
        // Cargar la imagen desde la carpeta de recursos
        return Files.readAllBytes(Paths.get("src/main/java/com/finanzas_personales/FinanzasPersonales/Images/" + imagePath));
    }



}
