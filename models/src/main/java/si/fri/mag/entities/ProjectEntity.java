package si.fri.mag.entities;

import si.fri.mag.MainEntity;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "project")


@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getAllProjects",
                query = "SELECT * FROM project",
                resultClass = ProjectEntity.class
        )
})

public class ProjectEntity implements MainEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date updatedAt;

    // getters

    public Integer getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // setters

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
