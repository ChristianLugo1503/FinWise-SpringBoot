package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.GroupContributionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IGroupContributionsRepository extends JpaRepository<GroupContributionsModel, Long> {
    List<GroupContributionsModel> findByGroup_Id(Long groupId);
}
