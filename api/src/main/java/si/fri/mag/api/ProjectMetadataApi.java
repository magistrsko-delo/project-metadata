package si.fri.mag.api;

import si.fri.mag.api.controllers.MainController;
import si.fri.mag.api.controllers.RootController;
import si.fri.mag.api.controllers.v1.ProjectMetadataController;
import si.fri.mag.api.mappers.EntityNotFoundMapper;
import si.fri.mag.api.mappers.ForbiddenExceptionMapper;
import si.fri.mag.api.mappers.NotFoundExceptionMapper;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class ProjectMetadataApi extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(ForbiddenExceptionMapper.class);
        resources.add(NotFoundExceptionMapper.class);
        resources.add(EntityNotFoundMapper.class);
        resources.add(MainController.class);
        resources.add(RootController.class);
        resources.add(ProjectMetadataController.class);
        return resources;
    }
}
