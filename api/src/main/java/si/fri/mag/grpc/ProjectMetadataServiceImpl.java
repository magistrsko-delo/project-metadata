package si.fri.mag.grpc;

import com.google.protobuf.Empty;
import com.kumuluz.ee.grpc.annotations.GrpcService;
import grpc.ProjectMetadataGrpc;
import grpc.ProjectmetadataService;
import io.grpc.stub.StreamObserver;
import si.fri.mag.DTO.ProjectDTO;
import si.fri.mag.input.ProjectInput;
import si.fri.mag.services.ProjectMetadataService;

import javax.enterprise.inject.spi.CDI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ProjectMetadataServiceImpl extends ProjectMetadataGrpc.ProjectMetadataImplBase {

    ProjectMetadataService projectMetadataService;

    @Override
    public void getProject(ProjectmetadataService.ProjectIdRequest request, StreamObserver<ProjectmetadataService.ProjectResponse> responseObserver) {
        projectMetadataService = CDI.current().select(ProjectMetadataService.class).get();

        ProjectDTO projectDTO = projectMetadataService.getProjectMetadata(request.getProjectId());

        if (projectDTO == null) {
            responseObserver.onError(new Throwable("cant find project with id: " + request.getProjectId()));
            return;
        }

        ProjectmetadataService.ProjectResponse response = this.buildProjectResponse(projectDTO);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllProjects(Empty request, StreamObserver<ProjectmetadataService.ProjectResponseRepeated> responseObserver) {
        projectMetadataService = CDI.current().select(ProjectMetadataService.class).get();
        List<ProjectDTO> projectDTOS = projectMetadataService.getAllProjectsMetadata();

        ProjectmetadataService.ProjectResponseRepeated response = ProjectmetadataService.ProjectResponseRepeated.newBuilder()
                .addAllData(this.buildProjectResponseRepeated(projectDTOS))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateProject(ProjectmetadataService.UpdateProjectRequest request, StreamObserver<ProjectmetadataService.ProjectResponse> responseObserver) {
        ProjectInput projectInput = new ProjectInput();
        projectInput.setName(request.getName());
        projectInput.setThumbnail(request.getThumbnail());
        projectInput.setProjectId(request.getProjectId());
        projectInput.setCreatedAt(new Date(request.getCreatedAt()));
        projectMetadataService = CDI.current().select(ProjectMetadataService.class).get();

        ProjectDTO projectDTO = projectMetadataService.updateProject(projectInput);
        if (projectDTO == null) {
            responseObserver.onError(new Throwable("cant find project with id: " + request.getProjectId()));
            return;
        }

        ProjectmetadataService.ProjectResponse response = this.buildProjectResponse(projectDTO);
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

    @Override
    public void deleteProject(ProjectmetadataService.ProjectIdRequest request, StreamObserver<ProjectmetadataService.ProjectDeleteResponse> responseObserver) {

        ProjectmetadataService.ProjectDeleteResponse response = ProjectmetadataService.ProjectDeleteResponse.newBuilder()
                .setMessage("Delete for project not available: " + request.getProjectId())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private List<ProjectmetadataService.ProjectResponse> buildProjectResponseRepeated(List<ProjectDTO> projectDTOS) {

        List<ProjectmetadataService.ProjectResponse> projectResponseRepeateds = new ArrayList<>();
        for (ProjectDTO projectDTO : projectDTOS) {
            projectResponseRepeateds.add(this.buildProjectResponse(projectDTO));
        }

        return projectResponseRepeateds;
    }

    private ProjectmetadataService.ProjectResponse buildProjectResponse(ProjectDTO projectDTO) {
        return ProjectmetadataService.ProjectResponse.newBuilder()
                .setProjectId(projectDTO.getProjectId())
                .setName(projectDTO.getName())
                .setThumbnail(projectDTO.getThumbnail())
                .setCreatedAt(projectDTO.getCreatedAt().getTime())
                .setUpdatedAt(projectDTO.getUpdatedAt().getTime())
                .build();
    }
}
