package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsService {
    @Autowired
    private IGroupsRepository groupsRepository;

    //GET ALL GROUPS
    public List<GroupsModel> getAllGroups() {
        return groupsRepository.findAll();
    }

    //GET GROUP BY ID
    public GroupsModel getGroupsById(Long id) {
        return groupsRepository.findById(id).orElseThrow(()-> new RuntimeException("Grupo no encontrado") );
    }

    //SAVE GROUP
    public GroupsModel saveGroup(GroupsModel groupsModel){
        return groupsRepository.save(groupsModel);
    }

    //DELETE GROUP
    public void deleteGroup(Long id){
        groupsRepository.deleteById(id);
    }
}
