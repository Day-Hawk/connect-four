package fourwins.game.controller.check;

import fourwins.game.Token;

import java.util.HashSet;

/**
 * Collection of several vectors in one line.
 * The function inherits from class {@link HashSet} which implements from {@link java.util.Set}.
 * Thus, its function will also be available.
 */
public final class VectorLine extends HashSet<Vector> {
  /**
   * Variable holds the type of the {@link Token}, which also applies to the elements of the collection.
   * These can then be obtained through the method {@link VectorLine#token()}.
   */
  private final Token token;

  /**
   * Construct a new Vector line instance.
   *
   * @param token which applies to the elements of the {@link java.util.Set}.
   */
  public VectorLine(final Token token) {
    this.token = token;
  }

  /**
   * Get the {@link Token} type of fields this list holds.
   *
   * @return reference of {@link VectorLine#token}.
   */
  public Token token() {
    return this.token;
  }
}