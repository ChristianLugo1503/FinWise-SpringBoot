package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.GroupMembersModel;
import com.finanzas_personales.FinanzasPersonales.Services.GroupMembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/membersGroup")
public class GroupMembersControllers {
    @Autowired
    private GroupMembersService groupMembersService;

    @GetMapping
    public List<GroupMembersModel> getAllGroupMembers(){
        return groupMembersService.getAllGroupMembers();
    }

    @GetMapping("/{id}")
    public GroupMembersModel getGroupMemberById(@PathVariable Long id){
        return groupMembersService.getGroupMemberById(id);
    }

    @PostMapping("/create")
    public GroupMembersModel createGroupMember(@RequestBody GroupMembersModel groupMembersModel){
        return groupMembersService.saveGroupMember(groupMembersModel);
    }

    @PutMapping("/update/{id}")
    public GroupMembersModel updateGroupMember(@PathVariable Long id, @RequestBody GroupMembersModel groupMembersModel){
        GroupMembersModel existingGroupMember = groupMembersService.getGroupMemberById(id);
        existingGroupMember.setGroupId(groupMembersModel.getGroupId());
        existingGroupMember.setUserId(groupMembersModel.getUserId());
        return groupMembersService.saveGroupMember(existingGroupMember);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGroupMember(@PathVariable long id){
        groupMembersService.deleteGroupMember(id);
        return "Miembro eliminado con éxito :)";
    }
}
