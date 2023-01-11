package fourwins.player;

import fourwins.game.Round;
import fourwins.move.BaseMove;
import fourwins.move.ComMove;
import fourwins.move.PlayerMove;

import java.lang.reflect.InvocationTargetException;

/**
 * Distinguishes the tokens that are placed on the board.
 */
public enum Token {
  PLAYER('X', PlayerMove.class), //Player sitting between the screen and the chair.
  COM('O', ComMove.class), //Computer that must be beaten.
  EMPTY(' ', null); //If no token is present at field.

  /**
   * Char to use if token displayed.
   */
  private final char symbol;

  private final Class<? extends BaseMove> moveClas;

  /**
   * Fill enum member with content.
   *
   * @param symbol   to use for token if displayed.
   * @param moveClas
   */
  Token(char symbol, Class<? extends BaseMove> moveClas) {
    this.symbol = symbol;
    this.moveClas = moveClas;
  }

  /**
   * Get the symbol to display token on a pitch for example.
   *
   * @return get display char of token.
   */
  public char symbol() {
    return symbol;
  }

  public <T extends BaseMove> T injectMove(final Round round) {
    try {
      return (T) this.moveClas.getConstructor(Round.class).newInstance(round);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @return
   */
  public Token flipToken() {
    return switch (this) {
      case PLAYER -> COM;
      case COM -> PLAYER;
      case EMPTY -> EMPTY;
    };
  }
}
