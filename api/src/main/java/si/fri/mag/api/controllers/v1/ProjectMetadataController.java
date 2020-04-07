package si.fri.mag.api.controllers.v1;

import jdk.nashorn.internal.objects.annotations.Getter;
import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.api.controllers.MainController;
import si.fri.mag.services.ProjectMetadataService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/v1/project/metadata")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectMetadataController extends MainController {

    @Inject
    private ProjectMetadataService projectMetadataService;

    @GET
    @Path("all")
    public Response getAllProjectsMetadata() {
        List<ProjectDTO> projectDTOS = projectMetadataService.getAllProjectsMetadata();
        return this.responseOk("", projectDTOS);
    }

    @GET
    @Path("{projectId}")
    public Response getOneProjectMetadata(@PathParam("projectId") Integer projectId) {

        ProjectDTO projectDTO = projectMetadataService.getProjectMetadata(projectId);
        return this.responseOk("", projectDTO);
    }

}
