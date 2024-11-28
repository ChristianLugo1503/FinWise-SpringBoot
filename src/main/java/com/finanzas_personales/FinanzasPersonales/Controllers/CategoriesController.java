package com.finanzas_personales.FinanzasPersonales.Controllers;

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
            @RequestParam("image") MultipartFile image) {
        try {
            // Convertir la imagen en bytes
            byte[] imageBytes = image.getBytes();

            // Crear la categoría
            CategoriesModel category = new CategoriesModel(name, type, imageBytes, user);
            CategoriesModel savedCategory = categoriesService.saveCategories(category);

            return ResponseEntity.ok(savedCategory);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar la imagen");
        }
    }

    /*
    // Obtener todas las categorias de un usuario en espesífico
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<CategoriesModel>> getCategoriesByUserId(@PathVariable String userID) {
        try {
            List<CategoriesModel> categories = categoriesService.getCategoriesByUserId(userID);

            // Convertir la imagen de cada categoría a Base64 si no es nula
            for (CategoriesModel category : categories) {
                if (category.getImage() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(category.getImage());
                    category.setImage(base64Image.getBytes()); // Establece la imagen como una cadena Base64
                }
            }

            if (categories.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(categories);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }*/

    //Método para obtener todas las categorias de un usuario en específico
    @GetMapping("/user/{userID}")
    public ResponseEntity<List<CategoriesModel>> getCategoriesByUserId(@PathVariable String userID) {
        try {
            List<CategoriesModel> categories = categoriesService.getCategoriesByUserId(userID);

            // No conviertas la imagen a Base64, déjala como un byte[] que se puede convertir en Blob en Angular
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
    public String deleteCategories(@PathVariable Long id){
        categoriesService.deleteCategories(id);
        return "Categoría eliminada exitosamente :)";
    }

    
}
