package com.hitotech.hexagonal.controller;

import com.hitotech.hexagonal.service.DocumentHexagonalService;
import com.hitotech.hexagonal.service.HexagonalService;
import com.hitotech.hexagonal.service.RelationalHexagonalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HexagonalController {

    private DocumentHexagonalService documentHexagonalService;

    private RelationalHexagonalService relationalHexagonalService;

    public HexagonalController(DocumentHexagonalService documentHexagonalService,
                               RelationalHexagonalService relationalHexagonalService) {
        this.documentHexagonalService = documentHexagonalService;
        this.relationalHexagonalService = relationalHexagonalService;
    }

    @GetMapping(value = "/document/{id}")
    public String getMessageInDocument(@PathVariable Long id) {
        System.out.println(id);
        return this.getMessage(documentHexagonalService, id);
    }

    @GetMapping(value = "/relational/{id}")
    public String getMessageInRelational(@PathVariable Long id) {
        return this.getMessage(relationalHexagonalService, id);
    }

    public String getMessage(HexagonalService hexagonalService, Long id) {
        System.out.println(id);
        return  hexagonalService.getMessage(id);
    }

}
