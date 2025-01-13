package com.finanzas_personales.FinanzasPersonales.Controllers;

import com.finanzas_personales.FinanzasPersonales.Models.GroupContributionsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Services.GroupContributionService;
import com.finanzas_personales.FinanzasPersonales.dto.GroupContributionsDto;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/contributions")
public class GroupContributionsController {
    @Autowired
    private GroupContributionService groupContributionService;

    @PostMapping("/create")
    public GroupContributionsModel createContribution(@RequestBody GroupContributionsDto groupContributionsDto) {
        return groupContributionService.createContribution(groupContributionsDto);
    }

    @GetMapping("/group/{groupId}")
    public List<GroupContributionsModel> getContributionsByGroup(@PathVariable Long groupId) {
        return groupContributionService.getContributionsByGroupId(groupId);
    }
}
