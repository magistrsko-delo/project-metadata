package si.fri.mag;

public abstract class ProjectAbstract {
    protected String name;
    protected String thumbnail;
    protected Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }
    // getters
    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
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

}
