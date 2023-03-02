package io.karlis.homework.services;

import io.karlis.homework.dto.EquationResult;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
@Service
public class CalculatorService {
    private static final int MAX_NUM_OPERATORS_SHORT = 1;
    private static final int MAX_NUM_OPERATORS_LONG = 4;

    public EquationResult calculateShort(String expression) {
        EquationResult answer = new EquationResult();
        answer.setEquation(expression);
        double result = calculate(expression, MAX_NUM_OPERATORS_SHORT);
        answer.setAnswer(result);
        return answer;
    }

    public EquationResult calculateLong(String expression) {
        EquationResult answer = new EquationResult();
        answer.setEquation(expression);
        double result = calculate(expression, MAX_NUM_OPERATORS_LONG);
        answer.setAnswer(result);
        return answer;
    }

    public Map<String, Double> findHighLow(String[] expression) {
        Map<String, Double> map = new HashMap<>();
        double[] values = Arrays.stream(expression)
                .mapToDouble(Double::parseDouble)
                .toArray();
        double min = Arrays.stream(values).min().orElseThrow();
        double max = Arrays.stream(values).max().orElseThrow();
        map.put("min", min);
        map.put("max", max);
        return map;
    }

    private static double calculate(String s, int maxNumOfOperators) {
        long numOp = s.chars().filter(c -> c == '+' || c == '-' || c == '*' || c == '/').count();
        if (numOp > maxNumOfOperators+1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expression cannot contain more than " + maxNumOfOperators + " operators");
        }
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringTokenizer st = new StringTokenizer(s, "+-*/", true);
        boolean expectOperand = true;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (expectOperand) {
                if (token.equals("-")) {
                    operands.push(-Double.parseDouble(st.nextToken()));
                } else if (token.matches("[0-9]+")) {
                    operands.push(Double.parseDouble(token));
                }
                expectOperand = false;
            } else {
                if (token.matches("[+\\-*/]")) {
                    while (!operators.isEmpty() && hasPriority(operators.peek(), token.charAt(0))) {
                        applyOperator(operands, operators.pop());
                    }
                    operators.push(token.charAt(0));
                    expectOperand = true;
                }
            }
        }
        while (!operators.isEmpty()) {
            applyOperator(operands, operators.pop());
        }
        return operands.pop();
    }

    private static boolean hasPriority(char op1, char op2) {
        return (op1 != '+' && op1 != '-') || (op2 != '*' && op2 != '/');
    }

    private static void applyOperator(Stack<Double> operands, char op) {
        double num2 = operands.pop();
        double num1 = operands.pop();
        switch (op) {
            case '+' -> operands.push(num1 + num2);
            case '-' -> operands.push(num1 - num2);
            case '*' -> operands.push(num1 * num2);
            case '/' -> operands.push(num1 / num2);
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid operator");
        }
    }
}
