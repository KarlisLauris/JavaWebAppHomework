package io.karlis.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class CountryResult {
    private List<String> countries;
    private String currentLocation;
}
