package com.devconnect.controller;

import com.devconnect.dto.ProjectRequest;
import com.devconnect.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequest request) {
        return ResponseEntity.ok(projectService.createProject(request));
    }
    
    @GetMapping
    public ResponseEntity<?> getUserProjects() {
        return ResponseEntity.ok(projectService.getCurrentUserProjects());
    }
    
    @PostMapping("/{projectId}/members")
    public ResponseEntity<?> addMember(@PathVariable Long projectId, @RequestBody Long userId) {
        projectService.addMember(projectId, userId);
        return ResponseEntity.ok().build();
    }
}