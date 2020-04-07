package si.fri.mag.services;

import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.converters.ProjectConverter;
import si.fri.mag.entities.ProjectEntity;
import si.fri.mag.input.ProjectInput;
import si.fri.mag.util_entity.EntityManagement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProjectMetadataService {
    @Inject
    private EntityManager em;

    @Inject
    private ProjectConverter projectConverter;

    @Inject
    private EntityManagement entityManagement;

    public List<ProjectDTO> getAllProjectsMetadata() {

        Query q = em.createNamedQuery("getAllProjects");
        List<ProjectEntity> projectEntities = (List<ProjectEntity>)q.getResultList();

        List<ProjectDTO> projectDTOS = new ArrayList<ProjectDTO>();

        for (ProjectEntity projectEntity : projectEntities) {
            projectDTOS.add(projectConverter.toDTO(projectEntity));
        }
        return projectDTOS;
    }

    public ProjectDTO getProjectMetadata(Integer projectId) {

        ProjectEntity projectEntity = em.find(ProjectEntity.class, projectId);

        if (projectEntity == null) {
            throw new EntityNotFoundException("Project with id: " + projectId + " does not exist");
        }

        return projectConverter.toDTO(projectEntity);
    }

    public ProjectDTO createProject(ProjectInput projectInput) {
        ProjectEntity projectEntity = projectConverter.toEntityNew(projectInput);
        projectEntity = (ProjectEntity) entityManagement.createNewEntity(projectEntity);

        if (projectEntity == null) {
            throw new InternalServerErrorException("error when creating entity");
        }
        return projectConverter.toDTO(projectEntity);
    }

    public ProjectDTO updateProject (ProjectInput projectInput) {
        ProjectEntity projectEntityFind = em.find(ProjectEntity.class, projectInput.getProjectId());
        if (projectEntityFind == null) {
            throw new EntityNotFoundException("Project with id: " + projectInput.getProjectId() + " does not exist");
        }

        ProjectEntity projectEntity = projectConverter.toEntityUpdate(projectInput);
        projectEntity.setProjectId(projectEntityFind.getProjectId());

        projectEntity = (ProjectEntity) entityManagement.updateEntity(projectEntity);
        if (projectEntity == null) {
            throw new InternalServerErrorException("error when upating entity");
        }

        return projectConverter.toDTO(projectEntity);
    }

}
