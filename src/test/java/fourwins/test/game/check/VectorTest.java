package fourwins.test.game.check;

import fourwins.game.controller.check.Vector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Vector}.
 */
public final class VectorTest {
  /**
   * Test constructor and getter.
   */
  @Test
  public void createVector() {
    final Vector vector = new Vector(1, 2); //Create new vector instance.

    Assertions.assertEquals(vector.x(), 1); //Check if vector stored the right x.
    Assertions.assertEquals(vector.y(), 2); //Check if vector stored the right y.
  }

  /**
   * Test {@link Vector#add(Vector)}.
   */
  @Test
  public void addVector() {
    final Vector baseVector = new Vector(1, -2); //Create new vector instance as base.
    final Vector vectorToAdd = new Vector(5, 5); //Create vector to add on base.

    final Vector sumVector = baseVector.add(vectorToAdd); //Created vector trough add method.

    Assertions.assertNotEquals(sumVector.x(), baseVector.x()); //check if x value changed of baseVector.
    Assertions.assertNotEquals(sumVector.y(), baseVector.y()); //check if y value changed of baseVector.

    Assertions.assertEquals(sumVector.x(), baseVector.x() + vectorToAdd.x()); //Check if add implementation calculates the right value.
    Assertions.assertEquals(sumVector.y(), baseVector.y() + vectorToAdd.y()); //Check if add implementation calculates the right value.
  }
}
