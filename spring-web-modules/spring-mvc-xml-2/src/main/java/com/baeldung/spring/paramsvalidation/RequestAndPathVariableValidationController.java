package com.baeldung.spring.paramsvalidation;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Controller
@RequestMapping("/public/api/1")
@Validated
public class RequestAndPathVariableValidationController {

    @GetMapping("/name-for-day")
    public String getNameOfDayByNumberRequestParam(@RequestParam @Min(1) @Max(7) Integer dayOfWeek) {
        return dayOfWeek + "";
    }

    @GetMapping("/name-for-day/{dayOfWeek}")
    public String getNameOfDayByPathVariable(@PathVariable("dayOfWeek") @Min(1) @Max(7) Integer dayOfWeek) {
        return dayOfWeek + "";
    }

    @GetMapping("/valid-name")
    public void validStringRequestParam(@RequestParam @NotBlank @Size(max = 10) @Pattern(regexp = "^[A-Z][a-zA-Z0-9]+$") String name) {

    }

}
