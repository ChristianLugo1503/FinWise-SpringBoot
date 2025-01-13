package com.finanzas_personales.FinanzasPersonales.Services;

import com.finanzas_personales.FinanzasPersonales.Models.GroupContributionsModel;
import com.finanzas_personales.FinanzasPersonales.Models.SavingsGoalModel;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupContributionsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IGroupsRepository;
import com.finanzas_personales.FinanzasPersonales.Repositories.IUserRepository;
import com.finanzas_personales.FinanzasPersonales.dto.GroupContributionsDto;
import com.finanzas_personales.FinanzasPersonales.dto.SavingGoalDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupContributionService {
    @Autowired
    private IGroupContributionsRepository groupContributionsRepository;

    @Autowired
    private IGroupsRepository groupRepository;

    @Autowired
    private IUserRepository userRepository;

    // Crear una contribucion
    public GroupContributionsModel createContribution(GroupContributionsDto dto) {
        // Mapeo manual de DTO a modelo
        GroupContributionsModel contribution = new GroupContributionsModel();

        contribution.setGroup(groupRepository.findById(dto.getGroup()).orElseThrow(
                () -> new EntityNotFoundException("Grupo no encontrado")
        ));
        contribution.setUser(userRepository.findById(dto.getUser()).orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado")
        ));
        contribution.setAmount(dto.getAmount());
        contribution.setStatus(dto.getStatus());
        contribution.setNote(dto.getNote());
        contribution.setCreatedAt(dto.getCreatedAt());

        // Guardar y devolver el modelo persistido
        return groupContributionsRepository.save(contribution);
    }


    //obtener contribuciones por el grupo id
    public List<GroupContributionsModel> getContributionsByGroupId(Long groupId) {
        return groupContributionsRepository.findByGroup_Id(groupId);
    }
}
