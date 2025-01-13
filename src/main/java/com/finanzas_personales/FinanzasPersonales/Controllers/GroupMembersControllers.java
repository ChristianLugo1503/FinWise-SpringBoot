package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.GroupMembersModel;
import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Models.UserModel;
import com.finanzas_personales.FinanzasPersonales.Services.GroupMembersService;
import com.finanzas_personales.FinanzasPersonales.dto.GroupsMembersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/user/{UserId}")
    public ResponseEntity<?> getGroupsByUserId(@PathVariable Long UserId) {
        try {
            List<GroupMembersModel> groups = groupMembersService.getGroupsByUserId(UserId);
            return ResponseEntity.ok(groups);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public GroupMembersModel getGroupMemberById(@PathVariable Long id){
        return groupMembersService.getGroupMemberById(id);
    }

    @PostMapping("/create")
    public GroupMembersModel createGroupMember(@RequestBody GroupsMembersDto groupMembersModel){
        return groupMembersService.createGroupMember(groupMembersModel);
    }

    /*
    @PutMapping("/update/{id}")
    public GroupMembersModel updateGroupMember(@PathVariable Long id, @RequestBody GroupMembersModel groupMembersModel){
        GroupMembersModel existingGroupMember = groupMembersService.getGroupMemberById(id);
        existingGroupMember.setGroupId(groupMembersModel.getGroupId());
        existingGroupMember.setUserId(groupMembersModel.getUserId());
        return groupMembersService.createGroupMember(existingGroupMember);
    }
*/
    // Endpoint para obtener los miembros del grupo (solo usuarios)
    @GetMapping("/group/{groupId}")
    public List<UserModel> getMembersByGroupId(@PathVariable Long groupId) {
        return groupMembersService.getMembersByGroupId(groupId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteGroupMember(@PathVariable long id){
        groupMembersService.deleteGroupMember(id);
    }
}
