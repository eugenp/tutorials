package com.baeldung.springai.agentskills.anthropic;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MonthlySalesService {

    public List<MonthlySale> getMonthlySalesForYear(int year) {
        return List.of(
            new MonthlySale("Product A", year, Month.JANUARY, new BigDecimal("1200")),
            new MonthlySale("Product A", year, Month.FEBRUARY, new BigDecimal("1325")),
            new MonthlySale("Product A", year, Month.MARCH, new BigDecimal("1410")),
            new MonthlySale("Product A", year, Month.APRIL, new BigDecimal("1380")),
            new MonthlySale("Product A", year, Month.MAY, new BigDecimal("1495")),
            new MonthlySale("Product A", year, Month.JUNE, new BigDecimal("1560")),
            new MonthlySale("Product A", year, Month.JULY, new BigDecimal("1620")),
            new MonthlySale("Product A", year, Month.AUGUST, new BigDecimal("1585")),
            new MonthlySale("Product A", year, Month.SEPTEMBER, new BigDecimal("1660")),
            new MonthlySale("Product A", year, Month.OCTOBER, new BigDecimal("1715")),
            new MonthlySale("Product A", year, Month.NOVEMBER, new BigDecimal("1780")),
            new MonthlySale("Product A", year, Month.DECEMBER, new BigDecimal("1850")),
            new MonthlySale("Product B", year, Month.JANUARY, new BigDecimal("950")),
            new MonthlySale("Product B", year, Month.FEBRUARY, new BigDecimal("990")),
            new MonthlySale("Product B", year, Month.MARCH, new BigDecimal("1045")),
            new MonthlySale("Product B", year, Month.APRIL, new BigDecimal("1015")),
            new MonthlySale("Product B", year, Month.MAY, new BigDecimal("1090")),
            new MonthlySale("Product B", year, Month.JUNE, new BigDecimal("1135")),
            new MonthlySale("Product B", year, Month.JULY, new BigDecimal("1180")),
            new MonthlySale("Product B", year, Month.AUGUST, new BigDecimal("1160")),
            new MonthlySale("Product B", year, Month.SEPTEMBER, new BigDecimal("1215")),
            new MonthlySale("Product B", year, Month.OCTOBER, new BigDecimal("1270")),
            new MonthlySale("Product B", year, Month.NOVEMBER, new BigDecimal("1330")),
            new MonthlySale("Product B", year, Month.DECEMBER, new BigDecimal("1395"))
        );
    }

}
