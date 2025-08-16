package com.backend.portfolio_ac.controller;

import com.backend.portfolio_ac.dto.ProjectCreateDto;
import com.backend.portfolio_ac.dto.ProjectDTO;
import com.backend.portfolio_ac.entity.Project;
import com.backend.portfolio_ac.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para la gestión de proyectos.
 *
 * @author bunnystring
 */
@RestController
@RequestMapping("/project")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Crea un nuevo proyecto a partir de los datos proporcionados.
     *
     * @param dto DTO con la información necesaria para crear un proyecto.
     * @return ResponseEntity con el proyecto creado y un código de estado 200 OK.
     */
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectCreateDto dto){
        Project project = projectService.create(dto);
        return ResponseEntity.ok(project);
    }

    /**
     * Obtiene la lista de todos los proyectos en formato DTO.
     *
     * @return ResponseEntity con la lista de {@link ProjectDTO} y un código de estado 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> project(){
        List<ProjectDTO> projectDTO = projectService.getAllProjects();
        return ResponseEntity.ok(projectDTO);
    }
}