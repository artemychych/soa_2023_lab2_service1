package se.ifmo.ru.soa_2023_lab2_service1.storage.repository.api;

import se.ifmo.ru.soa_2023_lab2_service1.service.model.TicketType;
import se.ifmo.ru.soa_2023_lab2_service1.storage.model.Filter;
import se.ifmo.ru.soa_2023_lab2_service1.storage.model.Page;
import se.ifmo.ru.soa_2023_lab2_service1.storage.model.Sort;
import se.ifmo.ru.soa_2023_lab2_service1.storage.model.TicketEntity;

import java.util.List;

public interface TicketRepository {
    TicketEntity findById(int id);

    TicketEntity save(TicketEntity entity);
    boolean deleteById(int id);
    Page<TicketEntity> getSortedAndFilteredPage(List<Sort> sortList, List<Filter> filters, Integer page, Integer size);

    TicketEntity getMinimumType();

    long countTicketByPrice(int price);

    List<TicketEntity> getTicketGreaterType(String ticketType);

}
