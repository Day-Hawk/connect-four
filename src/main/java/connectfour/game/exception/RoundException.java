package connectfour.game.exception;

/**
 * Mother class of the error messages of the software.
 * Inherits from {@link Exception}.
 */
public class RoundException extends Exception {
  /**
   * Create new instance of an error with a message.
   *
   * @param message Message which serves as an error message.
   */
  public RoundException(final String message) {
    super(message); //Passes the value to the inherited class.
  }
}
