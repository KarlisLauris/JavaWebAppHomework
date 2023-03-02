package io.karlis.homework.controllers;

import io.karlis.homework.dto.CountryResult;
import io.karlis.homework.services.CountryService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/countries")
public class CountryController {
    final CountryService countryService;

public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping()
    @SneakyThrows
    public CountryResult getSortedCountries() {
        return countryService.getSortedCountries();
    }
}

