package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupContributionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupContributionsService {
    @Autowired
    private IGroupContributionsRepository groupContributionsRepository;

    //OBTENER LISTA COMPLETA DE LA TABLA CONTRIBUCIONES

    //OBTENER LISTA DE CONTRIBUCIONES CON EL ID DEL GRUPO (LISTA GENERAL DENTRO DEL GRUPO)

    //OBTENER LISTA DE CONTRIBUCIONES CON EL ID DEL GRUPO Y DEL USUARIO (LISTA CONTRIBUCIONES DEL USUARIO DENTRO DEL GRUPO)



}
