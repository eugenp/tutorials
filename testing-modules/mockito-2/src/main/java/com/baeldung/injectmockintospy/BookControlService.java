package com.baeldung.injectmockintospy;

import java.time.ZonedDateTime;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class BookControlService {

    private StatisticService statisticService;
    private RepairService repairService;

    public void returnBook(Book book) {
        book.setReturnDate(null);
        statisticService.calculateAdded();
        if(repairService.shouldRepair(book)){
            log.info("Book should be repaired");
        }
    }

    public void giveBook(Book book) {
        book.setReturnDate(ZonedDateTime.now().plusDays(14L));
        statisticService.calculateRemoved();
    }

    public BookControlService(StatisticService statisticService, RepairService repairService) {
        this.statisticService = statisticService;
        this.repairService = repairService;
    }

}
