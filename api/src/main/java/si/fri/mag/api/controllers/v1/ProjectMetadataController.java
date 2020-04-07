package si.fri.mag.api.controllers.v1;

import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.api.controllers.MainController;
import si.fri.mag.services.ProjectMetadataService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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

}
