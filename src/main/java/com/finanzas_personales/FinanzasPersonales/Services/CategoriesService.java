package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.ICategoriesRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    private ICategoriesRepository categoriesRepository;

    //Metodo para obtener una lista de categorias
    public List<CategoriesModel> getAllCategories(){
        return categoriesRepository.findAll();
    }

    //Metodo para obtener una categoria mediante el id
    public CategoriesModel getCategoryById(Long id){
        return categoriesRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Categoria no encontrada"));
    }

    public CategoriesModel saveCategories(CategoriesModel categoria) {
        return categoriesRepository.save(categoria);
    }

    public void deleteCategories(Long id) {
        categoriesRepository.deleteById(id);
    }
}
