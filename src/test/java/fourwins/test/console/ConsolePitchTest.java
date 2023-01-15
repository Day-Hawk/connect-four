package fourwins.test.console;


import fourwins.console.ConsolePitch;
import fourwins.game.Round;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test {@link ConsolePitch#buildPitchString()}.
 */
public final class ConsolePitchTest {

  private static final String PITCH = """
    ===========================================================================================================================
    +---+---+---+---+---+
    |   |   |   |   |   |
    +---+---+---+---+---+
    |   |   |   |   |   |          X - PLAYER
    +---+---+---+---+---+
    |   |   |   |   |   |          O - COM
    +---+---+---+---+---+
    |   |   |   |   |   |
    +---+---+---+---+---+
    |   |   |   |   |   |
    +---+---+---+---+---+
    | 1 | 2 | 3 | 4 | 5 |

    ===========================================================================================================================""";

  /**
   * Test {@link ConsolePitch#buildPitchString()}.
   */
  @Test
  public void testString() {
    final Round round = new Round(5, 5); //Generate round.
    Assertions.assertEquals(new ConsolePitch(round).buildPitchString(), PITCH); //Compare result with defined field above.
  }
}
