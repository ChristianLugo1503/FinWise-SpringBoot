package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.GroupMembersModel;
import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupMembersRpository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.GroupsMembersDto;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMembersService {
    @Autowired
    private IGroupMembersRpository groupMembersRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IGroupsRepository groupsRepository;

    //GET LIST OF GROUP MEMBERS
    public List<GroupMembersModel> getAllGroupMembers(){
        return groupMembersRepository.findAll();
    }

    //GET GROUPS BY USER ID
    public List<GroupMembersModel> getGroupsByUserId(Long UserId) {
        if (!userRepository.existsById(UserId)) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return groupMembersRepository.findByUserId_id(UserId);
    }

    //GET GROUP MEMBER BY ID
    public GroupMembersModel getGroupMemberById(Long id){
        return groupMembersRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró al miembro del grupo."));
    }

    // Obtener miembros del grupo, solo información de usuario
    public List<UserModel> getMembersByGroupId(Long groupId) {
        // Verificar si el grupo existe
        if (!groupsRepository.existsById(groupId)) {
            throw new RuntimeException("Grupo no encontrado");
        }
        // Obtener los miembros del grupo y extraer solo los usuarios
        return groupMembersRepository.findByGroupId_id(groupId).stream()
                .map(GroupMembersModel::getUserId)
                .collect(Collectors.toList());
    }

    //SAVE MEMBER
    public GroupMembersModel createGroupMember(GroupsMembersDto dto) {
        // Mapeo manual de DTO a modelo
        GroupMembersModel groupMembersModel = new GroupMembersModel();

        groupMembersModel.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));

        groupMembersModel.setGroupId(groupsRepository.findById(dto.getGroupId()).orElseThrow(
                () -> new EntityNotFoundException("Grupo no encontrado")
        ));


        // Guardar y devolver el modelo persistido
        return groupMembersRepository.save(groupMembersModel);
    }

    //DELETE MEMBER
    public void deleteGroupMember(Long id) {
        if (!groupMembersRepository.existsById(id)) {
            throw new EntityNotFoundException("GroupMember no en contrado");
        }
        groupMembersRepository.deleteById(id);
    }
}
