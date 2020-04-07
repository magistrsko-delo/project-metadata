package si.fri.mag.DTO;

import si.fri.mag.ProjectAbstract;

import java.sql.Date;

public class ProjectDTO extends ProjectAbstract {

    private Date createdAt;
    private Date updatedAt;

    // getters

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    // setters

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
