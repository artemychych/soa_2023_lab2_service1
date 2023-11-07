package se.ifmo.ru.soa_2023_lab2_service1.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@Data
@XmlType
@XmlRootElement(name = "Ticket")
public class TicketAddOrUpdateRequestDto {
    private String name;
    private TicketCoordinatesAddResponseDto coordinates;
    private Float price;
    private String type;
    private TicketPersonAddResponseDto person;

    @Data
    @XmlType
    @XmlRootElement(name = "coordinates")
    public static class TicketCoordinatesAddResponseDto {
        private Integer x;
        private int y;
    }
    @Data
    @XmlType
    @XmlRootElement(name = "person")
    public static class TicketPersonAddResponseDto {
        private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private Long weight; //Поле может быть null, Значение поля должно быть больше 0
        private String hairColor; //Поле не может быть null
        private TicketGetResponseDto.TicketPersonLocationGetResponseDto location; //Поле не может быть null
    }

    @Data
    @XmlType
    @XmlRootElement(name = "location")
    public static class TicketPersonLocationAddResponseDto {
        private Float x; //Поле не может быть null
        private long y;
        private double z;
    }
}
