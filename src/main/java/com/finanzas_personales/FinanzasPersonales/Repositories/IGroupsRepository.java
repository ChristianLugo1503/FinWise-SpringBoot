package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupsRepository extends JpaRepository<GroupsModel, Long> {
}
