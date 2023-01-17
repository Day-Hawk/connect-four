package connectfour.test.utils;

import connectfour.game.controller.check.Vector;
import connectfour.utils.ObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link connectfour.utils.ObjectUtils}.
 */
public final class ObjectUtilsTest {
  /**
   * Check {@link connectfour.utils.ObjectUtils#throwIfNull(Object, String)} with a present object.
   */
  @Test
  public void testPresent() {
    Assertions.assertDoesNotThrow(() -> ObjectUtils.throwIfNull("Test-String", null)  /*Test with string*/);
    Assertions.assertDoesNotThrow(() ->
      ObjectUtils.throwIfNull(new Vector(-1, -1) /*Create vector for test*/, "Absent vector."));
  }

  /**
   * Check {@link connectfour.utils.ObjectUtils#throwIfNull(Object, String)} with an absent object (null).
   */
  @Test
  public void testAbsent() {
    Assertions.assertThrows(NullPointerException.class,
      () -> ObjectUtils.throwIfNull(null, null)); //Check if object is null without message.

    Assertions.assertThrows(NullPointerException.class, () ->
      ObjectUtils.throwIfNull(null, "Absent vector.")); //Check if object is null with message.
  }
}
