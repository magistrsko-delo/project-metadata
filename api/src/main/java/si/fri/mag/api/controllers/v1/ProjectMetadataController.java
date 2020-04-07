package si.fri.mag.api.controllers.v1;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.api.controllers.MainController;
import si.fri.mag.grpc.client.AwsStorageServiceClientGrpc;
import si.fri.mag.input.ProjectInput;
import si.fri.mag.services.ProjectMetadataService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
@Path("/v1/project/metadata")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectMetadataController extends MainController {

    @Inject
    private ProjectMetadataService projectMetadataService;

    @Inject
    private AwsStorageServiceClientGrpc awsStorageServiceClientGrpc;

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

    @POST
    @Path("")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response createProject(
            @NotEmpty
            @FormDataParam("projectPicture") InputStream projectPictureStream,
            @FormDataParam("projectPicture") FormDataContentDisposition projectPictureDetails,
            @FormDataParam("projectName") String projectName
    ) {
        File mediaFile = new File(projectPictureDetails.getFileName());
        try {
            FileUtils.copyInputStreamToFile(projectPictureStream, mediaFile);
        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        awsStorageServiceClientGrpc.uploadFileToAWS(mediaFile, "mag20-projects", projectPictureDetails.getFileName());

        ProjectInput projectInput = new ProjectInput();
        projectInput.setName(projectName);
        projectInput.setThumbnail("v1/mediaManager/mag20-projects/" + projectPictureDetails.getFileName());

        ProjectDTO projectDTO = projectMetadataService.createProject(projectInput);

        return this.responseOk("", projectDTO);
    }

    @PUT
    @Path("")
    public Response updateProject(String body) {
        Gson gson = new Gson();
        ProjectInput projectInput = null;
        try {
            projectInput = gson.fromJson(body, ProjectInput.class);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }

        ProjectDTO projectDTO = projectMetadataService.updateProject(projectInput);
        return this.responseOk("", projectDTO);
    }

    @DELETE
    @Path("{projectId}")
    public Response deleteProject(@PathParam("projectId") Integer projectId) {

        return this.responseError(404, "Delete for project: " + projectId + " is not available");
    }

}
