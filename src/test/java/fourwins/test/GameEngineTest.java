package fourwins.test;

import fourwins.GameEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test {@link fourwins.GameEngine}.
 */
public class GameEngineTest {

  /**
   * Test if {@link GameEngine#random()} instance, is present.
   */
  @Test
  public void testRandomPresent() {
    Assertions.assertNotNull(new GameEngine().random()); //Check if random instance is present.
  }
}
