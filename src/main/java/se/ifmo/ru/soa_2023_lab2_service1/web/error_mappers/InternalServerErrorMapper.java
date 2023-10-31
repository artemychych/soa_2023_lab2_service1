package se.ifmo.ru.soa_2023_lab2_service1.web.error_mappers;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import se.ifmo.ru.soa_2023_lab2_service1.util.ResponseUtils;

@Provider
@Slf4j
public class InternalServerErrorMapper implements ExceptionMapper<Throwable> {
    @Inject
    ResponseUtils responseUtils;

    @Override
    public Response toResponse(Throwable exception) {
        log.error(exception.getMessage(), exception);
        if (StringUtils.isEmpty(exception.getMessage())) {
            return responseUtils.buildResponseWithMessage(
                    Response.Status.INTERNAL_SERVER_ERROR,
                    "Something went wrong..."
            );
        }
        return responseUtils.buildResponseWithMessage(
                Response.Status.INTERNAL_SERVER_ERROR,
                exception.getMessage()
        );
    }
}
