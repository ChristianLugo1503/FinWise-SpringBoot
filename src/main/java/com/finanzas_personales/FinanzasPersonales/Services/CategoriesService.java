package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesService {
    @Autowired
    private ICategoriesRepository categoriesRepository;

    @Autowired
    private IUserRepository userRepository;

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

    public void deleteCategories(Long id) {
        categoriesRepository.deleteById(id);
    }
}
