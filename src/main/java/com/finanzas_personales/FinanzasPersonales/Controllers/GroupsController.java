package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Services.GroupsService;
import com.finanzas_personales.FinanzasPersonales.dto.GroupsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/groups")
public class GroupsController {
    @Autowired
    private GroupsService groupsService;

    @GetMapping
    public List<GroupsModel> getAllGroups() {
        return groupsService.getAllGroups();
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<?> getGroupsByUserId(@PathVariable Long userID) {
        try {
            List<GroupsModel> groups = groupsService.getGroupsByUserId(userID);
            return ResponseEntity.ok(groups);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public GroupsModel getGroupById(@PathVariable Long id){
        return groupsService.getGroupsById(id);
    }

    @PostMapping("/create")
    public GroupsModel createGroup(@RequestBody GroupsDto groupsModel){
        return groupsService.createGroup(groupsModel);
    }

    @PutMapping("/saved/{id}")
    public void updateSavedAmount(@PathVariable Long id, @RequestBody BigDecimal newAmount) {
        groupsService.updateSavedAmount(id, newAmount);
    }

    /*
    @PutMapping("/update/{id}")
    public GroupsModel updateGroup(@PathVariable Long id, @RequestBody GroupsDto groupsModel){
        GroupsModel existingGroup = groupsService.getGroupsById(id);
        existingGroup.setName(groupsModel.getName());
        existingGroup.setDescription(groupsModel.getDescription());
        existingGroup.setGoalAmount(groupsModel.getGoalAmount());
        existingGroup.setDeadline(groupsModel.getDeadline());
        existingGroup.setCreatedBy(groupsModel.getCreatedBy());
        return  groupsService.createGroup(existingGroup);
    }*/
}