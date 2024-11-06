package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.CategoriesModel;
import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import com.finanzas_personales.FinanzasPersonales.Services.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public GroupsModel getGroupById(@PathVariable Long id){
        return groupsService.getGroupsById(id);
    }

    @PostMapping("/create")
    public GroupsModel createGroup(@RequestBody GroupsModel groupsModel){
        return groupsService.saveGroup(groupsModel);
    }

    @PutMapping("/update/{id}")
    public GroupsModel updateGroup(@PathVariable Long id, @RequestBody GroupsModel groupsModel){
        GroupsModel existingGroup = groupsService.getGroupsById(id);
        existingGroup.setName(groupsModel.getName());
        existingGroup.setDescription(groupsModel.getDescription());
        existingGroup.setGoalAmount(groupsModel.getGoalAmount());
        existingGroup.setDeadline(groupsModel.getDeadline());
        existingGroup.setCreatedBy(groupsModel.getCreatedBy());
        return  groupsService.saveGroup(existingGroup);
    }
}