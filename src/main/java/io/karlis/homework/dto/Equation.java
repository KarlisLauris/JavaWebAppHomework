package io.karlis.homework.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class Equation {
    @NonNull
    private String equation;
}
