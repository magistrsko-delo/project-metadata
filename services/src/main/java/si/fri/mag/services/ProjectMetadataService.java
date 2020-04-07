package si.fri.mag.services;

import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.converters.ProjectConverter;
import si.fri.mag.entities.ProjectEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ProjectMetadataService {
    @Inject
    private EntityManager em;

    @Inject
    private ProjectConverter projectConverter;

    public List<ProjectDTO> getAllProjectsMetadata() {

        Query q = em.createNamedQuery("getAllProjects");
        List<ProjectEntity> projectEntities = (List<ProjectEntity>)q.getResultList();

        List<ProjectDTO> projectDTOS = new ArrayList<ProjectDTO>();

        for (ProjectEntity projectEntity : projectEntities) {
            projectDTOS.add(projectConverter.toDTO(projectEntity));
        }
        return projectDTOS;
    }

}
