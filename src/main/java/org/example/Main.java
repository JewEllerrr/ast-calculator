package org.example;

import org.example.model.ASTNode;
import org.example.model.Token;
import org.example.model.TokenType;
import org.example.services.ShuntingYardParser;
import org.example.services.TokenService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Input an arithmetic expression\n>> ");
        String expression = userInput.nextLine();
        ShuntingYardParser shuntingYardParser = new ShuntingYardParser();
        TokenService tokenService = new TokenService();
        ArrayList<Token> tokens = tokenService.tokenize(expression);
        HashSet<Token> uniqueTokens = new HashSet<>(tokens);

        ASTNode astNode = null;
        try {
            astNode = shuntingYardParser.convertInfixNotationToAST(tokens);
        }
        catch (IllegalStateException e) {
            System.out.println("Unable to parse expression");
            userInput.close();
            System.exit(0);
        }
        loop:
        do {
            System.out.println("Expression: " + tokenService.convertTokensToString(tokens));
            System.out.print("0. set up variables\n1. calculate\n2. print \n3. exit\n>> ");
            switch (userInput.nextLine()) {
                case "0" -> {
                    uniqueTokens.stream().filter(token -> token.getType().equals(TokenType.VARIABLE))
                            .forEach(token -> {
                                String value;
                                do {
                                    System.out.print("Input a value for the variable (must be in range [0|65535])\n>> " + token.getValue() + " = ");
                                    value = userInput.nextLine();
                                }
                                while (!tokenService.isNumeric(value));
                                token.setValue(value);
                                token.setType(TokenType.LITERAL);
                                token.setPrecedence(TokenType.LITERAL.getPrecedence());
                            });
                    tokenService.setUpVariables(tokens, uniqueTokens);
                }
                case "1" -> {
                    try {
                        double result = shuntingYardParser.evaluateAST(astNode);
                        System.out.println("Result: " + result);
                    }
                    catch (IllegalArgumentException e) {
                        System.out.println("Expression can't be calculated, all variables must be initialized");
                    }
                }
                case "2" -> System.out.println("Binary tree:" + astNode);
                case "3" -> {
                    break loop;
                }
                default -> System.out.println("Unknown command");
            }
        }
        while (true);

        userInput.close();
    }

}
