package com.finanzas_personales.FinanzasPersonales.Controllers;


import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/{email}")
    public UserModel getUserByEmail(@PathVariable String email){
        return userService.getUserByEmail(email);
    }

}
