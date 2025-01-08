package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.ENUMS.typeENUM;
import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/categories")
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;

    @GetMapping
    public List<CategoriesModel> getAllCategories(){
        return categoriesService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        return categoriesService.getCategoryById(id)
                .map(category -> ResponseEntity.ok().body(category))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("userId") UserModel user,
            @RequestParam("image") MultipartFile image,
            @RequestParam("color") String color) {
        try {
            // Convertir la cadena 'type' a un valor del enum typeENUM
            typeENUM categoryType = typeENUM.valueOf(type); // Esto lanza una IllegalArgumentException si el tipo no es válido

            // Convertir la imagen en bytes
            byte[] imageBytes = image.getBytes();

            // Crear la categoría
            CategoriesModel category = new CategoriesModel(name, categoryType, imageBytes, user, color);
            CategoriesModel savedCategory = categoriesService.saveCategories(category);

            return ResponseEntity.ok(savedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body("Tipo de categoría inválido: " + type);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar la imagen");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            // Obtener la categoría existente
            CategoriesModel category = categoriesService.getCategoryById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada " + id));

            // Actualizar solo los campos proporcionados
            if (name != null) {
                category.setName(name);
            }
            if (type != null) {
                category.setType(typeENUM.valueOf(type)); // Si el tipo es un enum
            }
            if (color != null) {
                category.setColor(color);
            }
            if (image != null && !image.isEmpty()) {
                // Convertir la imagen en bytes solo si se proporciona
                category.setImage(image.getBytes());
            }

            // Guardar los cambios
            CategoriesModel updatedCategory = categoriesService.saveCategories(category);
            return ResponseEntity.ok(updatedCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body("Error al actualizar la categoría: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar la imagen");
        }
    }

    //Método para obtener todas las categorias de un usuario en específico
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<CategoriesModel>> getCategoriesByUserId(@PathVariable String userID) {
        try {
            List<CategoriesModel> categories = categoriesService.getCategoriesByUserId(userID);

            if (categories.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(categories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCategories(@PathVariable Long id) {
        Map<String, Object> result = categoriesService.deleteCategories(id);
        if ((boolean) result.get("success")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }
}
