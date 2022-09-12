package org.example.model;

public class ASTNode {
    private final Token token;
    private final ASTNode leftASTNode;
    private final ASTNode rightASTNode;

    public ASTNode(Token token, ASTNode leftASTNode, ASTNode rightASTNode) {
        this.token = token;
        this.leftASTNode = leftASTNode;
        this.rightASTNode = rightASTNode;
    }

    public Token getToken() {
        return token;
    }

    public ASTNode getLeftASTNode() {
        return leftASTNode;
    }

    public ASTNode getRightASTNode() {
        return rightASTNode;
    }

    @Override
    public String toString() {
        return "\nNode{" +
                "token=" + token +
                ", leftNode=" + leftASTNode +
                ", rightNode=" + rightASTNode +
                "}";
    }
}