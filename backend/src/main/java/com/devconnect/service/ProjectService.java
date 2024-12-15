package com.devconnect.service;

import com.devconnect.dto.ProjectRequest;
import com.devconnect.model.Project;
import com.devconnect.model.User;
import com.devconnect.repository.ProjectRepository;
import com.devconnect.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProjectService {
    
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }
    
    @Transactional
    public Project createProject(ProjectRequest request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(currentUser);
        
        return projectRepository.save(project);
    }
    
    public List<Project> getCurrentUserProjects() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return projectRepository.findByOwnerIdOrMembersId(currentUser.getId(), currentUser.getId());
    }
    
    @Transactional
    public void addMember(Long projectId, Long userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        project.getMembers().add(user);
        projectRepository.save(project);
    }
}