package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.ITransactionsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriesService {
    @Autowired
    private ICategoriesRepository categoriesRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ITransactionsRepository transactionRepository;

    //Metodo para obtener una lista de categorias
    public List<CategoriesModel> getAllCategories(){
        return categoriesRepository.findAll();
    }

    //Metodo para obtener una categoria mediante el id
    public Optional<CategoriesModel> getCategoryById(Long id) {
        return categoriesRepository.findById(id);
    }


    //Metodo para obtener todas las categorias de un usuario por medio de su ID
    public List<CategoriesModel> getCategoriesByUserId(String userId) {
        // Convertir el userId a Long y buscar el usuario
        try {
            Long userIdLong = Long.parseLong(userId);
            Optional<UserModel> userOpt = userRepository.findById(userIdLong);

            if (userOpt.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            UserModel user = userOpt.get();
            return categoriesRepository.findByUserId(user);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid user ID format");
        }
    }

    public CategoriesModel saveCategories(CategoriesModel categoria) {
        return categoriesRepository.save(categoria);
    }

    public Map<String, Object> deleteCategories(Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Recuperar la categoría por ID
            CategoriesModel category = categoriesRepository.findById(id).orElseThrow(() ->
                    new IllegalArgumentException("La categoría con el ID " + id + " no existe."));

            // Verificar si la categoría está siendo utilizada
            if (transactionRepository.existsBycategoryID(category)) {
                response.put("success", false);
                response.put("message", "No se puede eliminar la categoría porque está siendo utilizada en transacciones.");
                return response;
            }

            // Eliminar la categoría
            categoriesRepository.deleteById(id);
            response.put("success", true);
            response.put("message", "Categoría eliminada exitosamente :)");
            return response;

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Ocurrió un error inesperado: " + e.getMessage());
            return response;
        }
    }



}
