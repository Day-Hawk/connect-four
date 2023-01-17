package connectfour.game;

import connectfour.game.controller.BaseController;
import connectfour.game.controller.ComController;
import connectfour.game.controller.PlayerController;
import connectfour.utils.ObjectUtils;

/**
 * Distinguishes the tokens that are placed on the board.
 */
public enum Token {
  /**
   * Player sitting between the screen and the chair to defeat the COM.
   */
  PLAYER('X', PlayerController.class),

  /**
   * Computer that must be beaten.
   */
  COM('O', ComController.class),

  /**
   * If no token is present at field.
   */
  EMPTY(' ', null);

  /**
   * Char to use if token displayed.
   * These can then be obtained through the method {@link Token#symbol()}.
   */
  private final char symbol;
  /**
   * The class is needed to build the controller. For this variable, there is no getter.
   */
  private final Class<? extends BaseController> moveClass;

  /**
   * Fill enum member with content.
   *
   * @param symbol    to use for token if displayed.
   * @param moveClass specifies the associated controller.
   */
  Token(char symbol, Class<? extends BaseController> moveClass) {
    this.symbol = symbol;
    this.moveClass = moveClass;
  }

  /**
   * Get the symbol to display token on a pitch for example.
   *
   * @return get display char of token.
   */
  public char symbol() {
    return symbol;
  }

  /**
   * Create a character controller from the locally given class.
   * For the build {@link Token#moveClass} is used.
   *
   * @param round  passes the round for which the controller is to be created.
   * @param <TYPE> generic type of the controller class  (Inherited from {@link BaseController}).
   * @return created {@link BaseController} instance.
   * @throws NullPointerException If there is no class or an error occurs during the building process.
   */
  @SuppressWarnings("unchecked")
  public <TYPE extends BaseController> TYPE injectMove(final Round round) throws NullPointerException {
    ObjectUtils.throwIfNull(this.moveClass, "There is no class that can be built."); //Check if a class is set for the instance.

    try {
      return (TYPE /*Format the created instance to the specified type.*/) this.moveClass
        .getConstructor(Round.class) //Get the constructor of the specified class, which contains only the round as a passing value.
        .newInstance(round); //Build a new instance of the class with the round specified.
    } catch (final Exception exception /*Collected errors: method not found, invoke and cast.*/) {
      throw new NullPointerException("An error occurred while building the class.");
    }
  }

  /**
   * Simple method to rotate the token.
   *
   * @return new token (other side of the previous token).
   */
  public Token flipToken() {
    return switch (this /*instance type of the enum*/) { //Go through all possible token types.
      case PLAYER -> COM; //Player turns into computer.
      case COM -> PLAYER; //Computer turns into player.
      case EMPTY -> EMPTY; //Empty token remains empty.
    };
  }
}
