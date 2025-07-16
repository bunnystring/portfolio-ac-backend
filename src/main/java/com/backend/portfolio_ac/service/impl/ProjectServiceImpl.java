package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.dto.ProjectCreateDto;
import com.backend.portfolio_ac.entity.Project;
import com.backend.portfolio_ac.entity.ProjectImage;
import com.backend.portfolio_ac.exception.ProjectException;
import com.backend.portfolio_ac.repository.ProjectRepository;
import com.backend.portfolio_ac.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    @Override
    public List<Project> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()){
            throw new ProjectException("No se encontraron projectos asociados", ProjectException.Type.NOT_FOUND_PROJECTS);
        }
        return projects;
    }

    @Override
    public Project create(ProjectCreateDto dto) {

        // Validar si el proyecto existe
        if (this.projectRepository.findByName(dto.getName()) != null){
            throw new ProjectException("Este proyecto ya existe", ProjectException.Type.PROJECT_EXIST);
        }

        try{
            Project.ProjectBuilder builder = Project.builder()
                    .name(dto.getName())
                    .description(dto.getDescription())
                    .url(dto.getUrl())
                    .repositoryUrl(dto.getRepositoryUrl())
                    .startDate(dto.getStartDate())
                    .endDate(dto.getEndDate())
                    .isActive(dto.isActive());

            if (dto.getImages() != null && !dto.getImages().isEmpty()){
                List<ProjectImage> images = dto.getImages().stream()
                        .map(url -> ProjectImage.builder()
                                .imageUrl(url)
                                .build())
                        .toList();
                builder.images(images);
            }
            Project project = builder.build();

            if (project.getImages() != null) {
                project.getImages().forEach(img -> img.setProject(project));
            }

            return projectRepository.save(project);

        } catch (ProjectException ex) {
            throw new ProjectException("Error creando proyecto", ProjectException.Type.ERROR_CREATE);
        }
    }

    @Override
    public Project getById(UUID id) {
        return null;
    }

    @Override
    public Project update(UUID id, Project project) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
