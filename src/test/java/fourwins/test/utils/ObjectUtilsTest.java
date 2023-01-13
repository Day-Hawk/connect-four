package fourwins.test.utils;

import fourwins.game.controller.check.Vector;
import fourwins.utils.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link fourwins.utils.ObjectUtils}.
 */
public final class ObjectUtilsTest {
  /**
   * Check {@link fourwins.utils.ObjectUtils#throwIfNull(Object, String)} with a present object.
   */
  @Test
  public void testPresent() {
    Assertions.assertDoesNotThrow(() -> ObjectUtils.throwIfNull("Test-String", null)  /*Test with string*/);
    Assertions.assertDoesNotThrow(() ->
      ObjectUtils.throwIfNull(new Vector(-1, -1) /*Create vector for test*/, "Absent vector."));
  }

  /**
   * Check {@link fourwins.utils.ObjectUtils#throwIfNull(Object, String)} with an absent object (null).
   */
  @Test
  public void testAbsent() {
    Assertions.assertThrows(NullPointerException.class,
      () -> ObjectUtils.throwIfNull(null, null)); //Check if object is null without message.

    Assertions.assertThrows(NullPointerException.class, () ->
      ObjectUtils.throwIfNull(null, "Absent vector.")); //Check if object is null with message.
  }
}
