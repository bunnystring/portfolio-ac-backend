package com.backend.portfolio_ac.service;


import com.backend.portfolio_ac.dto.ProjectCreateDto;
import com.backend.portfolio_ac.entity.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectService {
    /**
     *
     * @param projectCreateDto
     * @return
     */
    Project create(ProjectCreateDto projectCreateDto);

    /**
     *
     * @param id
     * @return
     */
    Project getById(UUID id);

    /**
     *
     * @return
     */
    List<Project> getAllProjects();

    /**
     *
     * @param id
     * @param project
     * @return
     */
    Project update(UUID id, Project project);

    /**
     *
     * @param id
     */
    void delete(UUID id);
}
