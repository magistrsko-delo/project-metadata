package si.fri.mag.input;

import si.fri.mag.ProjectAbstract;

import java.sql.Date;

public class ProjectInput extends ProjectAbstract {
    private Date createdAt;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
