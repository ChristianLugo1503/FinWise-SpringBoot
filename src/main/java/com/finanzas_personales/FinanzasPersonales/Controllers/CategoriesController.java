package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public CategoriesModel getCategoriesById(@PathVariable Long id){
        return categoriesService.getCategoryById(id);
    }

    @PostMapping("/create")
    public CategoriesModel saveCategories(@RequestBody CategoriesModel categoriesModel){
        return categoriesService.saveCategories(categoriesModel);
    }

    @PutMapping("/update/{id}")
    public CategoriesModel updateCategories(@PathVariable Long id, @RequestBody CategoriesModel categoriesModel) {
        CategoriesModel existingCategory = categoriesService.getCategoryById(id);
        existingCategory.setName(categoriesModel.getName());
        return categoriesService.saveCategories(existingCategory);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCategories(@PathVariable Long id){
        return "Categoría eliminada exitosamente :)";
    }

    
}
