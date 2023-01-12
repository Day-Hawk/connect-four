package fourwins.controller.check;

import fourwins.player.Token;

import java.util.HashSet;

public final class VectorLine extends HashSet<Vector> {
  private final Token token;
  public VectorLine(final Token token) {
    this.token = token;
  }

  public Token token() {
    return this.token;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
