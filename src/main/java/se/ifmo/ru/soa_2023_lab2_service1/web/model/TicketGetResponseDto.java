package se.ifmo.ru.soa_2023_lab2_service1.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.ifmo.ru.soa_2023_lab2_service1.service.model.Color;
import se.ifmo.ru.soa_2023_lab2_service1.service.model.Location;
import se.ifmo.ru.soa_2023_lab2_service1.service.model.TicketType;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlType
@XmlRootElement(name = "Ticket")
public class TicketGetResponseDto {
    private Integer id;
    private String name;
    private TicketCoordinatesGetResponseDto coordinates;
    private java.time.LocalDate creationDate;
    private Float price;
    private String type;
    private TicketPersonGetResponseDto person;


    @Data
    @XmlType
    @XmlRootElement(name = "Coordinates")
    public static class TicketCoordinatesGetResponseDto {
        private Integer x;
        private int y;
    }
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @XmlType
    @XmlRootElement(name = "Person")
    public static class TicketPersonGetResponseDto {
        private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
        private Long weight; //Поле может быть null, Значение поля должно быть больше 0
        private String hairColor; //Поле не может быть null
        private TicketPersonLocationGetResponseDto location; //Поле не может быть null
    }

    @Data
    @XmlType
    @XmlRootElement(name = "Person")
    public static class TicketPersonLocationGetResponseDto {
        private Float x; //Поле не может быть null
        private long y;
        private double z;
    }


}
