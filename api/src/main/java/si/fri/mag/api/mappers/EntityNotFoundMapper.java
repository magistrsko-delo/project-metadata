package si.fri.mag.api.mappers;

import si.fri.mag.api.controllers.MainController;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundMapper extends MainController implements ExceptionMapper<EntityNotFoundException> {
    public Response toResponse(EntityNotFoundException e) {
        return this.responseError(404, e.getMessage());
    }

}