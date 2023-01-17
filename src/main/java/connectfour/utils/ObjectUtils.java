package connectfour.utils;

/**
 * Class with static methods to make living with objects easy.
 */
public final class ObjectUtils {
  /**
   * Throw {@link NullPointerException} if object is null with given message.
   *
   * @param object  to check if it is null.
   * @param message to throw as error.
   * @param <TYPE>  generic type of object, needed for return parameter.
   * @return object if present and not null.
   * @throws NullPointerException if object is null.
   */
  public static <TYPE> TYPE throwIfNull(final TYPE object,
                                        final String message) {
    if (object != null) { //Returns the object if it is not null.
      return object; //Returns the object.
    }
    throw message == null ?
      new NullPointerException() : //Throws an empty error message if no message is specified.
      new NullPointerException(message); //Creates an error with the message.
  }
}
