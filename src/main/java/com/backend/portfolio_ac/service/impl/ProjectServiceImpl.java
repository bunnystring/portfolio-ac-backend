package com.backend.portfolio_ac.service.impl;

import com.backend.portfolio_ac.dto.ProjectCreateDto;
import com.backend.portfolio_ac.dto.ProjectDTO;
import com.backend.portfolio_ac.dto.ProjectImageDTO;
import com.backend.portfolio_ac.entity.Project;
import com.backend.portfolio_ac.entity.ProjectImage;
import com.backend.portfolio_ac.exception.ProjectException;
import com.backend.portfolio_ac.repository.ProjectRepository;
import com.backend.portfolio_ac.service.ProjectService;
import com.backend.portfolio_ac.util.MessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;


    @Override
    public List<ProjectDTO> getAllProjects(){
        List<Project> projects = projectRepository.findAll();
        if (projects.isEmpty()){
            throw new ProjectException(MessageException.PROJECT_NOT_FOUND, ProjectException.Type.NOT_FOUND_PROJECTS);
        }

        List<ProjectDTO> rs = new ArrayList<>();
        for (Project project : projects) {
            ProjectDTO dto = ProjectDTO.builder()
                    .name(project.getName())
                    .description(project.getDescription())
                    .start_date(project.getStartDate())
                    .end_date(project.getEndDate())
                    .is_active(project.isActive() ? "Activo" : "Inactivo")
                    .repository_url(project.getRepositoryUrl())
                    .url(project.getUrl())
                    .ProjectImageDTO(
                            project.getImages().stream()
                                    .map(img -> ProjectImageDTO.builder()
                                            .image_url(img.getImageUrl())
                                            .build()
                                    ).collect(Collectors.toList())
                    )
                    .build();
            rs.add(dto);
        }
        return rs;
    }

    @Override
    public Project create(ProjectCreateDto dto) {

        // Validar si el proyecto existe
        if (this.projectRepository.findByName(dto.getName()) != null){
            throw new ProjectException(MessageException.PROJECT_ALREADY, ProjectException.Type.PROJECT_EXIST);
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
