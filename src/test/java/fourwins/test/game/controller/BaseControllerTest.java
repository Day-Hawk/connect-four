package fourwins.test.game.controller;

import fourwins.game.Round;
import fourwins.game.Token;
import fourwins.game.controller.BaseController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * {@link fourwins.game.controller.BaseController}
 */
public final class BaseControllerTest {
  @Test
  public void testNullRoundController() {
    Assertions.assertThrows(NullPointerException.class, () -> new TestController(null, -1, Token.EMPTY)); //Check if throws error with null round.
  }

  @Test
  public void testController() {
    final Round round = new Round(5, 5); //Generate round.

    final TestController playerController = new TestController(round, 3, Token.PLAYER); //Create player controller.
    Assertions.assertEquals(playerController.awaitColumn(), 3);
    Assertions.assertEquals(playerController.token(), Token.PLAYER);

    final TestController comController = new TestController(round, 2, Token.COM); //Create com controller.
    Assertions.assertEquals(comController.awaitColumn(), 2);
    Assertions.assertEquals(comController.token(), Token.COM);
  }

  /**
   * Test controller instance.
   */
  private static final class TestController extends BaseController {
    /**
     * Value to return at {@link TestController#awaitColumn()}.
     */
    private final int value;
    /**
     * Value to return at {@link TestController#token()}.
     */
    private final Token token;

    public TestController(Round round,
                          int value,
                          Token token) {
      super(round);
      this.value = value;
      this.token = token;
    }

    @Override
    public int awaitColumn() {
      return this.value;
    }

    @Override
    public Token token() {
      return this.token;
    }
  }
}
