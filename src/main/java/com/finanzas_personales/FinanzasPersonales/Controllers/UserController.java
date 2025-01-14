package com.finanzas_personales.FinanzasPersonales.Controllers;


import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.Services.UserDetailsServiceImpl;
import com.finanzas_personales.FinanzasPersonales.dto.UserDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{email}")
    public UserModel getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "lastname", required = false) String lastname,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Verificar si el usuario existe
            UserModel user = iUserRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

            // Actualizar los campos del usuario solo si están presentes y no vacíos
            if (name != null && !name.isEmpty()) {
                user.setName(name);
            }
            if (lastname != null && !lastname.isEmpty()) {
                user.setLastname(lastname);
            }
            if (email != null && !email.isEmpty()) {
                user.setEmail(email);
            }

            // Verificar si se desea cambiar la contraseña
            if (password != null && !password.isEmpty() &&
                    newPassword != null && !newPassword.isEmpty()) {
                // Comparar la contraseña actual ingresada con la almacenada en la base de datos
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    // Lanzar error con mensaje personalizado
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of(
                            "error", "Incorrect current password",
                            "message", "La contraseña actual es incorrecta."
                    ));
                }
                // Si la contraseña actual coincide, actualizar con la nueva contraseña codificada
                String encodedNewPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedNewPassword);
            }

            // Validar tamaño de la imagen antes de procesarla
            if (image != null && !image.isEmpty()) {
                // Establecer un tamaño máximo (en bytes), 1 MB es 1,048,576 bytes
                long maxSizeInBytes = 1 * 1024 * 1024; // 1 MB

                if (image.getSize() > maxSizeInBytes) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                            "error", "Image size too large",
                            "message", "La imagen excede el tamaño máximo permitido de 1 MB."
                    ));
                }

                // Convertir la imagen en bytes solo si pasa la validación
                user.setImage(image.getBytes());
            }

            // Guardar y devolver el modelo persistido
            UserModel updatedUser = iUserRepository.save(user);
            return ResponseEntity.ok(updatedUser);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", "User not found",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Unexpected error",
                    "message", e.getMessage()
            ));
        }
    }






}
