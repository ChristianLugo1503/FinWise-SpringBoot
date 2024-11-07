package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.GroupMembersModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMembersService {
    @Autowired
    private IGroupMembersRepository groupMembersRepository;

    //GET LIST OF GROUP MEMBERS
    public List<GroupMembersModel> getAllGroupMembers(){
        return groupMembersRepository.findAll();
    }

    //GET GROUP MEMBER BY ID
    public GroupMembersModel getGroupMemberById(Long id){
        return groupMembersRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontró al miembro del grupo."));
    }

    //SAVE MEMBER
    public GroupMembersModel saveGroupMember(GroupMembersModel groupMembersModel){
        return groupMembersRepository.save(groupMembersModel);
    }

    //DELETE MEMBER
    public void deleteGroupMember(Long id){
        groupMembersRepository.deleteById(id);
    }
}
