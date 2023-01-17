package connectfour.game.exception;

/**
 * This error is triggered when a field is selected that is outside the playing field.
 * Inherits from {@link RoundException}.
 */
public class OutsideFieldException extends RoundException {
  /**
   * Create new instance of an error with a message.
   *
   * @param message Message which serves as an error message.
   */
  public OutsideFieldException(final String message) {
    super(message); //Passes the value to the inherited class.
  }
}
