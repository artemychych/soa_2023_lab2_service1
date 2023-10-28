package se.ifmo.ru.soa_2023_lab2_service1.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {
    private Integer x; //Поле не может быть null
    private int y;
}

