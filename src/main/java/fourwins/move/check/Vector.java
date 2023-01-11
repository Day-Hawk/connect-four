package fourwins.move.check;

/**
 * This record contains a position or a direction of a move. (Two dimensional)
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
   * new x <- this.x + given.x
   * new y <- this.y + given.y
   *
   * @param vector which is to be added.
   * @return new vector instance with added values.
   * @throws NullPointerException If the given vector is null.
   */
  public Vector add(final Vector vector) throws NullPointerException {
    if (vector == null) { //Check if the given vector is null, if so throw error.
      throw new NullPointerException("Given vector is null!");
    }
    return new Vector(this.x + vector.x(), this.y + vector.y()); //Create new instance with the values described above.
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
}
