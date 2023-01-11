package fourwins.controller;

import fourwins.game.Round;
import fourwins.player.Token;
import fourwins.utils.ObjectUtils;

/**
 * The controller that manages the inputs to the game field.
 */
public abstract class BaseController {
  /**
   * Round of the controller.
   * These can then be obtained through the method {@link BaseController#round()}.
   */
  private final Round round;

  /**
   * Create a new controller instance.
   *
   * @param round reference for the variable {@link BaseController#round}.
   */
  public BaseController(final Round round) {
    ObjectUtils.throwIfNull(round, "Round is null."); //Checks the round is not null.

    this.round = round;
  }

  /**
   * Returns the instance variable {@link BaseController#round}.
   *
   * @return for which the controller was created.
   */
  public Round round() {
    return this.round;
  }

  /**
   * Method to be inherited to adjust the logic. The player must make an input while the pc calculates a digit.
   *
   * @return returns the selected position (column) in which the move is to be made.
   */
  public abstract int awaitColumn();

  /**
   * Returns the type of {@link Token} set by this controller.
   *
   * @return token type.
   */
  public abstract Token token();
}