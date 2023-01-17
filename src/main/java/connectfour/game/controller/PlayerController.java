package connectfour.game.controller;

import connectfour.GameEngine;
import connectfour.console.Messages;
import connectfour.game.Round;
import connectfour.game.Token;

/**
 * Class which serves as a controller of the player.
 * This class asks for the field and processes the answer and returns a valid answer at the end.
 */
public final class PlayerController extends BaseController {
  /**
   * Create instance of {@link PlayerController}.
   *
   * @param round for which the controller is created.
   */
  public PlayerController(final Round round) {
    super(round);
  }

  /**
   * Waits for player input. When the player has made an input, it is processed, filtered and prepared.
   *
   * @return returns the selected position (column) in which the move is to be made.
   */
  @Override
  public int awaitColumn() {
    int column = -1; //Define new variable with value -1 -> Undefined.

    while (column < 0) { //Runs until a valid index is given.
      int maxInput = this.round().columns(); //Stores the maximal available column.
      System.out.printf(Messages.ASK_PLAYER_CONTROLLER_INPUT, maxInput); //Send question

      int inputIntegerIndex = GameEngine.instance().consoleInput() //Store input in variable.
        .awaitInteger(integer -> integer > 0 && integer <= maxInput /*Check if given integer is in range. 0 <= INPUT < maxInput*/,
          Messages.PLAYER_CONTROLLER_INPUT_RANGE::formatted /*Send player error message*/);

      if (!this.round().columnHasSpace(inputIntegerIndex - 1)) { //Check if input is a columns if free space.
        System.out.println(Messages.PLAYER_CONTROLLER_INPUT_FULL); //Send error message.
        continue; //Ask for next input.
      }
      column = inputIntegerIndex; //Set input as valid column.
    }

    System.out.printf(Messages.PLAYER_CONTROLLER_INPUT, (column)); //Info about given number.
    return column - 1; //Convert input to index and return to requester.
  }

  /**
   * Defines this controller to use {@link Token#PLAYER}.
   *
   * @return type: {@link Token#PLAYER}.
   */
  @Override
  public Token token() {
    return Token.PLAYER;
  }
}
