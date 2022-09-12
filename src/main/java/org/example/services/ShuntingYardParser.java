package org.example.services;

import org.example.model.ASTNode;
import org.example.model.Token;
import org.example.model.TokenType;

import java.util.ArrayList;
import java.util.Stack;

public class ShuntingYardParser {

    public ASTNode convertInfixNotationToAST(ArrayList<Token> tokens) throws IllegalStateException {
        if (tokens.isEmpty()) throw new IllegalStateException();
        Stack<Token> operatorStack = new Stack<>();
        Stack<ASTNode> operandStack = new Stack<>();
        loop:
        for (Token t : tokens) {
            Token popped;
            switch (t.getType()) {
                case LPAREN:
                    operatorStack.push(t);
                    break;
                case RPAREN:
                    while (!operatorStack.isEmpty()) {
                        popped = operatorStack.pop();
                        if (popped.getType().equals(TokenType.LPAREN)) {
                            continue loop;
                        }
                        addNode(operandStack, popped);
                    }
                    throw new IllegalStateException();
                default:
                    if (!t.getType().equals(TokenType.LITERAL) && !t.getType().equals(TokenType.VARIABLE)) {
                        Token o2;
                        while (!operatorStack.isEmpty() && (o2 = operatorStack.peek()) != null) {
                            if ((t.comparePrecedence(o2) == 0) || t.comparePrecedence(o2) < 0) {
                                operatorStack.pop();
                                addNode(operandStack, o2);
                            }
                            else break;
                        }
                        operatorStack.push(t);
                    }
                    else operandStack.push(new ASTNode(t, null, null));
                    break;
            }
        }
        while (!operatorStack.isEmpty() && !operatorStack.peek().getType().equals(TokenType.LPAREN)) {
            addNode(operandStack, operatorStack.pop());
        }
        return operandStack.pop();
    }

    private void addNode(Stack<ASTNode> stack, Token operator) {
        ASTNode rightASTNode = stack.pop();
        ASTNode leftASTNode = stack.pop();
        stack.push(new ASTNode(operator, leftASTNode, rightASTNode));
    }

    public double evaluateAST(ASTNode tree) throws IllegalArgumentException {
        return switch (tree.getToken().getType()) {
            case VARIABLE -> throw new IllegalArgumentException();
            case MUL -> evaluateAST(tree.getLeftASTNode()) * evaluateAST(tree.getRightASTNode());
            case DIV -> evaluateAST(tree.getLeftASTNode()) / evaluateAST(tree.getRightASTNode());
            case ADD -> evaluateAST(tree.getLeftASTNode()) + evaluateAST(tree.getRightASTNode());
            case SUB -> evaluateAST(tree.getLeftASTNode()) - evaluateAST(tree.getRightASTNode());
            default -> Double.parseDouble(tree.getToken().getValue());
        };
    }

}
