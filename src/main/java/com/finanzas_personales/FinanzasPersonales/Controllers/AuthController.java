package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.Services.JwtUtilService;
import com.finanzas_personales.FinanzasPersonales.dto.AuthRequestDto;
import com.finanzas_personales.FinanzasPersonales.dto.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody AuthRequestDto authRequestDto) {
        try {
            // Verificar si el correo existe en la base de datos
            UserModel userModel = userRepository.findByEmail(authRequestDto.getEmail());
            if (userModel == null) {
                // Si el correo no existe, retornamos un error específico
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Email not found",
                        "message", "The provided email does not exist in our records."
                ));
            }

            // Autenticar credenciales
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequestDto.getEmail(), authRequestDto.getPassword()
            ));

            // Generar JWT
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(authRequestDto.getEmail());
            String jwt = this.jwtUtilService.generateToken(userDetails, userModel.getRole());
            String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, userModel.getRole());

            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "refreshToken", refreshToken
            ));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "error", "Incorrect password",
                    "message", "The password you entered is incorrect."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Unexpected error",
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserModel userModel) {
        try {
            // Verificar si el correo existe en la base de datos
            UserModel userModelres = userRepository.findByEmail(userModel.getEmail());
            if (userModelres != null) {
                // Si el correo no existe, retornamos un error específico
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                        "error", "Email exist",
                        "message", "The provided email already exist."
                ));
            }

            UserModel savedUser = userRepository.save(userModel);

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


}
