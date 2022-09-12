package model;

import java.util.Objects;

public class Token {
    private String declaration;
    private String value;
    private TokenType type;
    private int precedence;

    public Token(String value, TokenType tokenType, int precedence) {
        this.value = value;
        this.type = tokenType;
        this.precedence = precedence;
    }

    public Token(String value, TokenType tokenType) {
        this.value = value;
        this.type = tokenType;
        this.precedence = TokenType.LITERAL.getPrecedence();
    }

    public Token(String declaration, String value, TokenType tokenType) {
        this.declaration = declaration;
        this.value = value;
        this.type = tokenType;
        this.precedence = TokenType.LITERAL.getPrecedence();
    }

    public int comparePrecedence(Token o) {
        return Integer.compare(precedence, o.precedence);
    }

    public String getDeclaration() {
        return declaration;
    }

    public String getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }

    @Override
    public String toString() {
        return "Token{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Token t)) {
            return false;
        }

        return Objects.equals(this.declaration, t.getDeclaration()) &&
                Objects.equals(this.value, t.getValue())
                && Objects.equals(this.type, t.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type, precedence);
    }
}
