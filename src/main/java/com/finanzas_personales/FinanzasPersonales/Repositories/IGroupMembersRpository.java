package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.GroupMembersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupMembersRpository extends JpaRepository<GroupMembersModel, Long> {
}
