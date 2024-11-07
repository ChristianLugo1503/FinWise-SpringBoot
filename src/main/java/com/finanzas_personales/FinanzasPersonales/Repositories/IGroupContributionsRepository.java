package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.GroupContributionsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupContributionsRepository extends JpaRepository<GroupContributionsModel, Long> {
}
