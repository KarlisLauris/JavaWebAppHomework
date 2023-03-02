package io.karlis.homework.controllers;

import io.karlis.homework.dto.HighLow;
import io.karlis.homework.dto.EquationResult;
import io.karlis.homework.dto.Equation;
import io.karlis.homework.services.CalculatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/short")
    public ResponseEntity<EquationResult> calculateShort(@RequestBody Equation equation) {
        String input = sanitizeString(equation.getEquation());
        return ResponseEntity.ok(calculatorService.calculateShort(input));
    }

    @PostMapping("/long")
    public ResponseEntity<EquationResult> calculateLong(@RequestBody Equation equation) {
        String input = sanitizeString(equation.getEquation());
        return ResponseEntity.ok(calculatorService.calculateLong(input));
    }

    @PostMapping("/high-low")
    public ResponseEntity<Map<String, Double>> findHighLow(@RequestBody HighLow equation) {
        String[] input = sanitizeArray(equation.getNumbers());
        return ResponseEntity.ok(calculatorService.findHighLow(input));
    }
    private String[] sanitizeArray(String[] input) {
        if (input == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression cannot be null");
        }

        if (input.length < 2) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression must contain at least 2 numbers");
        }

        for (String s : input) {
            if (s.matches("[^0-9]") && !s.equals("-")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression can only contain numbers");
            }
        }
        return input;
    }
    private String sanitizeString(String input) {
        if (input == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression cannot be null");
        }

        if (input.matches("[^0-9+\\-*/]")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression can only contain numbers and operators");
        }

        if (input.matches(".*[+\\-*/]$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression cannot end with an operator");
        }

        if (input.matches("[+\\-*/]{2,}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Consecutive operators are not allowed");
        }

        input = input.replaceAll("\\s", "");

        if (input.matches("^[*/].*")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression cannot start with an operator");
        } else if (input.startsWith("+")) {
            input = input.substring(1);
        }
        return input;
    }
}
