package com.devconnect.repository;

import com.devconnect.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerIdOrMembersId(Long ownerId, Long memberId);
}