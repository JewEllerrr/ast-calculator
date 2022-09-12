package services;

import model.Token;
import model.TokenType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class TokenService {
    private final String delimiter = "+-/*()";

    public ArrayList<Token> tokenize(String expression) {

        StringTokenizer elements = new StringTokenizer(expression.replaceAll("\\s", ""), delimiter, true);
        ArrayList<Token> tokens = new ArrayList<>();

        while (elements.hasMoreTokens()) {
            String element = elements.nextToken();
            if ("(".equals(element)) {
                tokens.add(new Token(element, TokenType.LPAREN));
            }
            else if (")".equals(element)) {
                tokens.add(new Token(element, TokenType.RPAREN));
            }
            else if ("+".equals(element)) {
                tokens.add(new Token(element, TokenType.ADD, TokenType.ADD.getPrecedence()));
            }
            else if ("-".equals(element)) {
                tokens.add(new Token(element, TokenType.SUB, TokenType.SUB.getPrecedence()));
            }
            else if ("/".equals(element)) {
                tokens.add(new Token(element, TokenType.DIV, TokenType.DIV.getPrecedence()));
            }
            else if ("*".equals(element)) {
                tokens.add(new Token(element, TokenType.MUL, TokenType.MUL.getPrecedence()));
            }
            else if (isNumeric(element)) {
                tokens.add(new Token(element, TokenType.LITERAL));
            }
            else if (!Character.isDigit(element.charAt(0))) {
                tokens.add(new Token(element, element, TokenType.VARIABLE));
            }
            else {
                return new ArrayList<>();
            }
        }

        return tokens;
    }

    public boolean isNumeric(String string) {
        if ((string == null) || string.equals("") || string.startsWith("-")) {
            return false;
        }
        try {
            int value = Integer.parseInt(string);
            short s = (short) value;
            Short.toUnsignedInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }

    }

    public String convertTokensToString(ArrayList<Token> tokens) {
        return tokens.stream().map(Token::getValue)
                .collect(Collectors.joining(" "));
    }

    public void setUpVariables(ArrayList<Token> tokens, HashSet<Token> uniqueTokens) {
        tokens.stream().filter(token -> token.getType().equals(TokenType.VARIABLE))
                .forEach(token -> {
                    String value = uniqueTokens.stream()
                            .filter(uniqueToken -> uniqueToken.getDeclaration() != null
                                    && uniqueToken.getDeclaration().equals(token.getDeclaration()))
                            .findAny().get().getValue();
                    token.setValue(value);
                    token.setType(TokenType.LITERAL);
                    token.setPrecedence(TokenType.LITERAL.getPrecedence());
                });
    }
}
