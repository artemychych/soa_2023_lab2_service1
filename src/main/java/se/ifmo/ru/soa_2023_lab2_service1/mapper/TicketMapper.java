package se.ifmo.ru.soa_2023_lab2_service1.mapper;

import org.mapstruct.Mapper;
import se.ifmo.ru.soa_2023_lab2_service1.service.model.*;
import se.ifmo.ru.soa_2023_lab2_service1.storage.model.TicketEntity;
import se.ifmo.ru.soa_2023_lab2_service1.web.model.TicketGetResponseDto;

import java.util.List;

@Mapper(componentModel = "jakarta")
public interface TicketMapper {
    TicketGetResponseDto toDto(Ticket source);

    List<TicketGetResponseDto> toGetResponseDtoList(List<Ticket> source);

    default Ticket fromEntity(TicketEntity entity) {
        if (entity == null) {
            return null;
        }

        Ticket.TicketBuilder ticket = Ticket.builder();

        ticket.id(entity.getId());
        ticket.name(entity.getName());
        ticket.coordinates(
                Coordinates
                        .builder()
                        .x(entity.getCoordinatesX())
                        .y(entity.getCoordinatesY())
                        .build()
        );
        ticket.creationDate(entity.getCreationDate());
        ticket.price(entity.getPrice());
        ticket.type(entity.getType());
        ticket.person(Person.builder()
                        .weight(entity.getPersonWeight())
                        .hairColor(entity.getPersonHairColor())
                        .location(
                                Location.builder()
                                        .x(entity.getPersonLocationX())
                                        .y(entity.getPersonLocationY())
                                        .z(entity.getPersonLocationZ())
                                        .build()
                        )
                .build());
        return ticket.build();
    }

    List<Ticket> fromEntityList(List<TicketEntity> entities);

    default String fromTicketType(TicketType type) {

        return type.toString();
    }

    default String fromHairColor(Color color) {
        if (color == null) {
            return Color.NONE.toString();
        }
        return color.toString();
    }

}
