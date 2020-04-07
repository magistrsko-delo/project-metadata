package si.fri.mag.api.mappers;

import si.fri.mag.api.controllers.MainController;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper extends MainController implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return this.responseError(404, e.getMessage());
    }
}
