package connectfour.test.game;

import connectfour.game.Round;
import connectfour.game.exception.OutsideFieldException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test {@link connectfour.game.Round}.
 */
public class RoundTest {

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Assertions.assertDoesNotThrow(() -> new Round(6, 7)); //Check success build.

    Assertions.assertThrows(IllegalArgumentException.class, () -> new Round(-1, 7)); //Test with one negative number.
    Assertions.assertThrows(IllegalArgumentException.class, () -> new Round(-1, -1)); //Test with two negative numbers.

    Assertions.assertThrows(IllegalArgumentException.class, () -> new Round(3, 3)); //Check size.
  }

  /**
   * Check {@link Round#tokenAt(int, int)}.
   */
  @Test
  public void checkTokenAt() {
    final Round round = new Round(5,5);

    Assertions.assertThrows(OutsideFieldException.class, () -> round.tokenAt(5,5)); //Outside field.
    Assertions.assertThrows(OutsideFieldException.class, () -> round.tokenAt(-1,-1)); //Outside field.

    Assertions.assertDoesNotThrow(() -> round.tokenAt(1,1));
  }

  /**
   * Test {@link Round#validateRowIndex(int)}, {@link Round#validateColumnIndex(int)}.
   */
  @Test
  public void testChecks() {
    final Round round = new Round(5,5);

    Assertions.assertThrows(OutsideFieldException.class, () -> round.validateRowIndex(5)); //Check if index is in pitch.
    Assertions.assertThrows(OutsideFieldException.class, () -> round.validateColumnIndex(5));

    Assertions.assertDoesNotThrow(() -> round.validateRowIndex(3)); //Check if index is in pitch.
    Assertions.assertDoesNotThrow(() -> round.validateColumnIndex(3));
  }
}
