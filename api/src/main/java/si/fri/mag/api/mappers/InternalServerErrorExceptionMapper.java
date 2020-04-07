package si.fri.mag.api.mappers;

import si.fri.mag.api.controllers.MainController;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class InternalServerErrorExceptionMapper extends MainController implements ExceptionMapper<InternalServerErrorException> {
    @Override
    public Response toResponse(InternalServerErrorException e) {
        return this.responseError(500, e.getMessage());
    }
}
