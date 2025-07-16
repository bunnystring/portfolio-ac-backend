package com.backend.portfolio_ac.controller;

import com.backend.portfolio_ac.dto.ProjectCreateDto;
import com.backend.portfolio_ac.entity.Project;
import com.backend.portfolio_ac.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectCreateDto dto){
        Project project = projectService.create(dto);
        return ResponseEntity.ok(project);
    }
}
