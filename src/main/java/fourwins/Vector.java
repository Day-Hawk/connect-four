package fourwins;

public record Vector(int x,
                     int y) {

  public Vector add(final Vector vector) {
    return new Vector(this.x + vector.x(), this.y + vector.y());
  }

  public Vector copy() {
    return new Vector(this.x, this.y);
  }

}
