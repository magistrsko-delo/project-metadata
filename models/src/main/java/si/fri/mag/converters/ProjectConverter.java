package si.fri.mag.converters;

import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.entities.ProjectEntity;
import si.fri.mag.input.ProjectInput;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Date;

@ApplicationScoped
public class ProjectConverter {

    public ProjectDTO toDTO(ProjectEntity projectEntity) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setProjectId(projectEntity.getProjectId());
        projectDTO.setName(projectEntity.getName());
        projectDTO.setThumbnail(projectEntity.getThumbnail());
        projectDTO.setCreatedAt(projectEntity.getCreatedAt());
        projectDTO.setUpdatedAt(projectEntity.getUpdatedAt());

        return projectDTO;
    }

    public ProjectEntity toEntityNew(ProjectInput projectInput) {
        ProjectEntity projectEntity = this.toEntity(projectInput);
        Date createdDate = new Date(System.currentTimeMillis());
        projectEntity.setCreatedAt(createdDate);
        projectEntity.setUpdatedAt(createdDate);
        return projectEntity;
    }

    public ProjectEntity toEntityUpdate (ProjectInput projectInput) {
        ProjectEntity projectEntity = this.toEntity(projectInput);
        projectEntity.setUpdatedAt(new Date(System.currentTimeMillis()));
        projectEntity.setCreatedAt(projectInput.getCreatedAt());
        return projectEntity;
    }

    private ProjectEntity toEntity(ProjectInput projectInput) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setName(projectInput.getName());
        projectEntity.setThumbnail(projectInput.getThumbnail());

        return projectEntity;
    }
}
