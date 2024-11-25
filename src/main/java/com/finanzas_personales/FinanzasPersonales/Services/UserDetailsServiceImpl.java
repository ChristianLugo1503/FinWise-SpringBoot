package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar en la base de datos el usuario
        UserModel userModel = this.iUserRepository.findByEmail(email);
        if (userModel == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + email);
        }

        // Crear la lista de autoridades (roles)
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(userModel.getRole())
        );

        // Devolver el User con username, password y autoridades
        return new User(userModel.getEmail(), userModel.getPassword(), authorities);
    }

    // Guardar usuario
    public UserModel saveUser(UserModel userModel){
        return iUserRepository.save(userModel);
    }

    //Optener usuario por email
    public UserModel getUserByEmail(String email){
        return iUserRepository.findByEmail(email);
    }

}
