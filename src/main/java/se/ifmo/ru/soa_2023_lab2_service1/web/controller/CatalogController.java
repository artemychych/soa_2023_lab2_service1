package se.ifmo.ru.soa_2023_lab2_service1.web.controller;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import se.ifmo.ru.soa_2023_lab2_service1.mapper.TicketMapper;
import se.ifmo.ru.soa_2023_lab2_service1.service.api.TicketService;
import se.ifmo.ru.soa_2023_lab2_service1.service.model.Ticket;
import se.ifmo.ru.soa_2023_lab2_service1.storage.model.Page;
import se.ifmo.ru.soa_2023_lab2_service1.util.ResponseUtils;
import se.ifmo.ru.soa_2023_lab2_service1.web.model.CountByPriceResponseDto;
import se.ifmo.ru.soa_2023_lab2_service1.web.model.TicketAddOrUpdateRequestDto;
import se.ifmo.ru.soa_2023_lab2_service1.web.model.TicketsListGetResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/service")
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class CatalogController {

    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok("PING").build();
    }
    @Inject
//    @Inject
    TicketService ticketService;
//    @Inject
    @Inject
    ResponseUtils responseUtils;

//    @Inject
    @Inject
    TicketMapper ticketMapper;

    @GET
    @Path("/tickets")
    public Response getTickets(@Context HttpServletRequest request) {
        String[] sortParameters = request.getParameterValues("sort");
        String[] filterParameters = request.getParameterValues("filter");

        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("pageSize");
        Integer page = null, pageSize = null;

        try {
            if (StringUtils.isNotEmpty(pageParam)) {
                page = Integer.parseInt(pageParam);
                if (page <= 0) {
                    throw new NumberFormatException();
                }
            }
            if (StringUtils.isNotEmpty(pageSizeParam)) {
                pageSize = Integer.parseInt(pageSizeParam);
                if (pageSize <= 0) {
                    throw new NumberFormatException();
                }
            }
        } catch (NumberFormatException numberFormatException) {
            return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Invalid query param value");
        }

        List<String> sort = sortParameters == null
                ? new ArrayList<>()
                : Stream.of(sortParameters).filter(StringUtils::isNotEmpty).collect(Collectors.toList());
        List<String> filter = filterParameters == null
                ? new ArrayList<>()
                : Stream.of(filterParameters).filter(StringUtils::isNotEmpty).collect(Collectors.toList());

        Page<Ticket> resultPage = ticketService.getTickets(
                sort,
                filter,
                page,
                pageSize
        );

        return Response
                .ok("Nice")
                .entity(new TicketsListGetResponseDto(
                        ticketMapper.toGetResponseDtoList(
                                resultPage.getObjects()
                        ),
                        resultPage.getPage(),
                        resultPage.getPageSize(),
                        resultPage.getTotalPages(),
                        resultPage.getTotalCount()
                )).build();


    }


    @GET
    @Path("/tickets/{id}")
    public Response getTicket(@PathParam("id") int id) {
        Ticket ticket = ticketService.getTicket(id);
        if (ticket == null) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND,
                    "Ticket with id: " + id + " not found!");
        }

        return Response.ok().entity(ticketMapper.toDto(ticket)).build();
    }


    @POST
    @Path("/tickets")
    public Response addTicket(TicketAddOrUpdateRequestDto requestDto) {
        Response validationResult = validateTicketAddOrUpdateRequestDto(requestDto);

        if (validationResult != null) {
            return validationResult;
        }

        Ticket ticket = ticketService.addTicket(requestDto);

        return Response.ok().entity(ticketMapper.toDto(ticket)).build();
    }

    @PUT
    @Path("/tickets/{id}")
    public Response updateFlat(@PathParam("id") int id, TicketAddOrUpdateRequestDto requestDto) {
        Response validationResult = validateTicketAddOrUpdateRequestDto(requestDto);

        if (validationResult != null) {
            return validationResult;
        }

        Ticket ticket = ticketService.updateTicket(id, requestDto);

        if (ticket == null) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND, "Flat with id " + id + " not found");
        }
        return Response
                .ok()
                .entity(ticketMapper.toDto(ticket))
                .build();
    }

    @DELETE
    @Path("/tickets/{id}")
    public Response deleteFlat(@PathParam("id") int id) {
        boolean deleted = ticketService.deleteTicket(id);

        if (!deleted) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND, "Flat with id " + id + " not found");
        }

        return Response.noContent().build();
    }

    @GET
    @Path("/tickets/minimum-type")
    public Response getMinimumType() {
        Ticket ticket = ticketService.getMinimumTypeTicket();
        if (ticket == null) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND,
                    "Ticket not found!");
        }

        return Response
                .ok()
                .entity(ticketMapper.toDto(ticket))
                .build();
    }

    @GET
    @Path("/tickets/name/{type}")
    public Response getGreaterType(@PathParam("type") String type) {
        List<Ticket> tickets = ticketService.getTicketsGreaterType(type);

        if (tickets == null) {
            return responseUtils.buildResponseWithMessage(Response.Status.NOT_FOUND,
                    "Tickets not found!");
        }


        return Response
                .ok()
                .entity(new TicketsListGetResponseDto(
                        ticketMapper.toGetResponseDtoList(tickets),
                        0,
                        20,
                        40,
                        30L
                ))
                .build();
    }

    @GET
    @Path("/tickets/count/{price}")
    public Response getCountByPrice(@PathParam("price") long price) {
        return Response.ok().entity(CountByPriceResponseDto.builder().count(ticketService.countTicketsByPrice(price)).build())
                .build();
    }


    private Response validateTicketAddOrUpdateRequestDto(TicketAddOrUpdateRequestDto requestDto) {
        if (StringUtils.isEmpty(requestDto.getName())) {
            return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Name can not be empty");
        }
        if (requestDto.getCoordinates() == null) {
            return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Coordinates cannot be null");
        }
        if (requestDto.getPrice() == null || requestDto.getPrice() <= 0) {
            return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Price must be grater than 0");
        }
        if (requestDto.getType() == null) {
            return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Type cannot be null");
        }
        if (requestDto.getPerson() != null) {
            if (requestDto.getPerson().getWeight() != null && requestDto.getPerson().getWeight() <= 0) {
                return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Weight of Person must be greater than 0");
            }
            if (requestDto.getPerson().getHairColor() == null) {
                return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Hair color of Person cannot be null");
            }

            if (requestDto.getPerson().getLocation() == null) {
                return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "Location of Person cannot be null");
            } else {
                if (requestDto.getPerson().getLocation().getX() == null && requestDto.getPerson().getWeight() != null) {
                    return responseUtils.buildResponseWithMessage(Response.Status.BAD_REQUEST, "X Location of Person cannot be null");
                }
            }


        }

        return null;
    }


}
