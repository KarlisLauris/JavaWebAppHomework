package io.karlis.homework.dto;

import lombok.Data;

@Data
public class CountryResult {
    private String[] countries;
    private String currentLocation;
}
