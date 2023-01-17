package connectfour.game.controller.check;

import connectfour.utils.ObjectUtils;

import java.util.Objects;

/**
 * This record contains a position or a direction of a move (Two dimensional).
 * This class does not contain all operations that can be applied to a vector.
 * Only the required functions are implemented here.
 *
 * @param x coordinate in x direction.
 * @param y coordinate in y direction.
 */
public record Vector(int x,
                     int y) {
  /**
   * Add any {@link Vector} to this instance. When adding, a new {@link Vector} will be created.
   * The values of this instance remain untouched.
   * <p>
   * Simple description of the new {@link Vector}:
   * new x &lt;- this.x + given.x
   * new y &lt;- this.y + given.y
   *
   * @param vector which is to be added.
   * @return new vector instance with added values.
   * @throws NullPointerException If the given vector is null.
   */
  public Vector add(final Vector vector) throws NullPointerException {
    ObjectUtils.throwIfNull(vector, "Add vector is null."); //Checks the vector is not null.
    return new Vector(this.x + vector.x(), this.y + vector.y()); //Create new instance with the values described above.
  }

  /**
   * Subtract any {@link Vector} to this instance. When subtracting, a new {@link Vector} will be created.
   * The values of this instance remain untouched.
   * <p>
   * Simple description of the new {@link Vector}:
   * new x &lt;- this.x - given.x
   * new y &lt;- this.y - given.y
   *
   * @param vector which is to be subtracted.
   * @return new vector instance with subtracted values.
   * @throws NullPointerException If the given vector is null.
   */
  public Vector subtract(final Vector vector) throws NullPointerException {
    ObjectUtils.throwIfNull(vector, "Subtract vector is null."); //Checks the vector is not null.
    return new Vector(this.x - vector.x(), this.y - vector.y()); //Create new instance with the values described above.
  }

  /**
   * Clone this Vector instance.
   * The values are transferred to a new vector class.
   *
   * @return create new instance of {@link Vector} with the same values.
   */
  public Vector copy() {
    return new Vector(this.x, this.y); //Create instance.
  }

  /**
   * Overwrites the hashCode of the class. Creates a common hashCode of the {@link Vector#x} and {@link Vector#y} value.
   * <p>
   * So this class can be used error free in a {@link java.util.Set} for example.
   *
   * @return new generated hashCode. -> {@link Objects#hash(Object...)}.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y); //-> This method calls Arrays.hashCodes(int[]); Used to reduce code length.
  }
}
