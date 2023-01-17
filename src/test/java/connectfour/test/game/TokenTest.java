package connectfour.test.game;

import connectfour.game.Round;
import connectfour.game.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Token}.
 */
public final class TokenTest {
  /**
   * Test {@link Token#flipToken()} method implementation.
   */
  @Test
  public void testFlip() {
    /*
     * Test equal.
     */
    Assertions.assertEquals(Token.PLAYER.flipToken(), Token.COM);
    Assertions.assertEquals(Token.COM.flipToken(), Token.PLAYER);
    Assertions.assertEquals(Token.EMPTY.flipToken(), Token.EMPTY);

    /*
     * Test not equal.
     */
    Assertions.assertNotEquals(Token.EMPTY.flipToken(), Token.PLAYER);
    Assertions.assertNotEquals(Token.EMPTY.flipToken(), Token.COM);
  }

  /**
   * Test {@link Token#injectMove(Round)}.
   */
  @Test
  public void testInjectMove() {
    final Round round = new Round(4, 4); //Generate round to inject.

    Assertions.assertDoesNotThrow(() -> Token.PLAYER.injectMove(round)); //Check if player character is injectable.
    Assertions.assertDoesNotThrow(() -> Token.COM.injectMove(round)); //Check if com character is injectable.

    Assertions.assertThrows(NullPointerException.class, () -> Token.EMPTY.injectMove(round)); //Should throw error -> no class defined.
  }
}
