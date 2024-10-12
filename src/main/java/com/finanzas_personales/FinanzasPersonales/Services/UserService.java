package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final IUserRepository IUserRepository;


    //obtener todos los usuarios para probar
    public ArrayList<UserModel> getUsers(){
        return (ArrayList<UserModel>) IUserRepository.findAll();
    }

    //Actualizar usuario con el id
    public UserModel updateById(UserModel request, Long id){
        UserModel user = IUserRepository.findById(id).get();
        user.setName(request.getName());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        IUserRepository.save(user);
        return user;
    }

    //verifica si existe el usuario mediante su correo electrónico
    public boolean existsByEmail(String email) {
        return IUserRepository.findByEmail(email).isPresent();
    }

    //Guardar usuario
    public void saveUser(UserModel user) {
        IUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = IUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

}
