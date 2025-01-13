package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.GroupsDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GroupsService {
    @Autowired
    private IGroupsRepository groupsRepository;

    @Autowired
    private IUserRepository userRepository;

    //GET ALL GROUPS
    public List<GroupsModel> getAllGroups() {
        return groupsRepository.findAll();
    }

    //GET GROUPS BY USER ID
    public List<GroupsModel> getGroupsByUserId(Long createdBy) {
        if (!userRepository.existsById(createdBy)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return groupsRepository.findByCreatedById(createdBy);
    }

    //GET GROUP BY ID
    public GroupsModel getGroupsById(Long id) {
        return groupsRepository.findById(id).orElseThrow(()-> new RuntimeException("Grupo no encontrado") );
    }

    public GroupsModel createGroup(GroupsDto dto) {
        // Validar y obtener el usuario creador
        UserModel creator = userRepository.findById(dto.getCreatedBy())
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Crear y mapear el modelo desde el DTO
        GroupsModel group = new GroupsModel();
        group.setName(dto.getName());
        group.setDescription(dto.getDescription());
        group.setGoalAmount(dto.getGoalAmount());
        group.setSavedAmount(dto.getSavedAmount());
        group.setCreatedBy(creator);
        group.setCreatedAt(dto.getCreatedAt());
        group.setUpdatedAt(dto.getUpdatedAt());

        // Guardar y devolver el grupo creado
        return groupsRepository.save(group);
    }

    //Actualizar el dinero ahorrado
    public void updateSavedAmount(Long id, BigDecimal newAmount) {
        // Verificar si la meta de ahorro existe
        GroupsModel groupsModel = groupsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ahorro no encontrado"));

        // Actualizar el estado
        groupsModel.setSavedAmount(newAmount);

        // Guardar los cambios en la base de datos
        groupsRepository.save(groupsModel);
    }

    //DELETE GROUP
    public void deleteGroup(Long id){
        groupsRepository.deleteById(id);
    }
}
