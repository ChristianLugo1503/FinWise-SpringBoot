package com.finanzas_personales.FinanzasPersonales.Repositories;

import com.finanzas_personales.FinanzasPersonales.Models.GroupMembersModel;
import com.finanzas_personales.FinanzasPersonales.Models.GroupsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IGroupMembersRpository extends JpaRepository<GroupMembersModel, Long> {
    List<GroupMembersModel> findByUserId_id(Long UserId);
    List<GroupMembersModel> findByGroupId_id(Long groupId);
}

