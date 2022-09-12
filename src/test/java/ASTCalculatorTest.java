import model.ASTNode;
import model.Token;
import model.TokenType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import services.ShuntingYardParser;
import services.TokenService;

import java.util.ArrayList;
import java.util.HashSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

public class ASTCalculatorTest {
    ShuntingYardParser shuntingYardParser;
    TokenService tokenService;

    @BeforeTest
    public void beforeTest() {
        shuntingYardParser = new ShuntingYardParser();
        tokenService = new TokenService();
    }

    @Test
    public void shouldConvertInfixNotationToAST() {

        // given
        String expression = "14 + 2 * 3 - 6 / 2";

        // when
        ArrayList<Token> tokens = tokenService.tokenize(expression);
        ASTNode astNode = shuntingYardParser.convertInfixNotationToAST(tokens);
        double result = shuntingYardParser.evaluateAST(astNode);

        // then
        double expected = 17;
        assertEquals(result, expected);

    }

    @Test
    public void shouldReplaceValuesAndCalculate() {

        // given
        String expression = "14 + x * 3 - y / x";
        HashSet<Token> uniqueTokens = new HashSet<>();
        uniqueTokens.add(new Token("y", "2", TokenType.LITERAL));
        uniqueTokens.add(new Token("x", "4", TokenType.LITERAL));

        // when
        ArrayList<Token> tokens = tokenService.tokenize(expression);
        tokenService.setUpVariables(tokens, uniqueTokens);
        ASTNode astNode = shuntingYardParser.convertInfixNotationToAST(tokens);
        double result = shuntingYardParser.evaluateAST(astNode);

        // then
        double expected = 25.5;
        assertEquals(result, expected);

    }

    @Test
    public void willThrowIllegalStateException() {

        // given
        String expression = "14 + 2 * 3 - 6 / 2)";

        // when
        ArrayList<Token> tokens = tokenService.tokenize(expression);

        // then
        assertThrows(IllegalStateException.class, () -> shuntingYardParser.convertInfixNotationToAST(tokens));

    }

    @Test
    public void willThrowIllegalArgumentException() {

        // given
        String expression = "14 + 2 * 3 - 6 / x";

        // when
        ArrayList<Token> tokens = tokenService.tokenize(expression);
        ASTNode astNode = shuntingYardParser.convertInfixNotationToAST(tokens);

        // then
        assertThrows(IllegalArgumentException.class, () -> shuntingYardParser.evaluateAST(astNode));

    }
}

