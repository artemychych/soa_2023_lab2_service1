package se.ifmo.ru.soa_2023_lab2_service1.web.error_mappers;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import se.ifmo.ru.soa_2023_lab2_service1.util.ResponseUtils;

@Provider
@Slf4j
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    @Inject
    ResponseUtils responseUtils;

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);

        if (StringUtils.isEmpty(exception.getMessage())) {
            return responseUtils.buildResponseWithMessage(
                    Response.Status.BAD_REQUEST,
                    "Bad Request"
            );
        }
        return responseUtils.buildResponseWithMessage(
                Response.Status.BAD_REQUEST,
                exception.getMessage()
        );
    }

}
