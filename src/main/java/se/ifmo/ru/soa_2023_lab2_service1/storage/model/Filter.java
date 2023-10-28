package se.ifmo.ru.soa_2023_lab2_service1.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filter {
    private String fieldName;
    private FilteringOperation filteringOperation;
    private String fieldValue;
}
