/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.spring.controller;

/**
 *
 * @author morrisonidiasirue
 */
import com.baeldung.spring.service.EquipmentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

public class EquipmentController {

    private EquipmentCategoryService equipmentCategoryService;

    @Autowired
    public EquipmentController(EquipmentCategoryService equipmentCategoryService) {
        this.equipmentCategoryService = equipmentCategoryService;
    }

}
