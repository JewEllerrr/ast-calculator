package org.example.model;

public enum TokenType {
    LPAREN(0),
    RPAREN(0),
    LITERAL(0),
    VARIABLE(0),
    ADD(1),
    SUB(1),
    MUL(2),
    DIV(2);

    private final int precedence;

    TokenType(int Precedence) {
        this.precedence = Precedence;
    }

    public int getPrecedence() {
        return this.precedence;
    }
}
