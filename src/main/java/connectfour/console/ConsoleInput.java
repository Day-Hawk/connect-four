package connectfour.console;

import connectfour.GameEngine;
import connectfour.utils.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Class for managing console input. Filter string input, map strings to other types.
 * It is also possible to check mapped objects and create a message if the given object is not valid for the program.
 */
public final class ConsoleInput {
  /**
   * Scanner instance to use for class methods.
   */
  private final Scanner scanner;

  /**
   * Create a new input class. A scanner is created in the constructor.
   */
  public ConsoleInput() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Wait for a console input from the user.
   *
   * @param testInput   check if the input value has a certain value ({@link Predicate}).
   *                    Example: A string can be checked if it can be converted to an integer.
   *                    Never should be null!
   * @param mapFunction changes the type of the input.
   *                    Example: string(12) s -> Integer.parseInt(s) -> 12.
   *                    Never should be null!
   * @param testValue   Check the result of the mapFunction ({@link Predicate}).
   * @param message     Outputs the message if the testValue fails.
   * @param <TYPE>      generic type of the expected object.
   * @return the modified return value.
   * @throws NullPointerException if testInput or mapFunction is empty.
   */
  public <TYPE> TYPE await(final Predicate<String> testInput,
                           final Function<String, TYPE> mapFunction,
                           final Predicate<TYPE> testValue,
                           final Function<TYPE, String> message) throws NullPointerException {
    ObjectUtils.throwIfNull(testInput, "No test function given."); //Check if message is checkable. (-> predicate present)
    ObjectUtils.throwIfNull(mapFunction, "No mapping function given."); //Check if message is mappable. (-> function present)

    while (true) { //Runs until a result is returned.
      String textInput = null;

      try {
        textInput = this.scanner.next(); //textInput is the entered value of the user.
      } catch (Exception exception) {
        textInput = "";
        System.out.println();
        System.out.println("An error has occurred with the scanner.");
        System.out.println("If you started the application as a container check if the tags '-i'(Keep STDIN open even if not attached) and '-t'(Allocate a pseudo-TTY) are present.");
        System.out.println();
        System.exit(0);
      }

      if (!testInput.test(textInput) /*Tests the input if true valid.*/) {
        System.out.println("Invalid int input.");
        continue; //Wait for next input, goto start of while.
      }

      final TYPE mappedObject = mapFunction.apply(textInput); //mappedObject holds the result of the mapFunction with the textInput as input value.

      if (mappedObject == null) { //Check if mappedObject is present. Otherwise, throw exception.
        continue; //Wait for next input, goto start of while.
      }

      if (testValue != null /*Checks if a test exists for the mappedObject.*/ &&
        !testValue.test(mappedObject) /*Tests the mappedObject if true valid.*/) {
        System.out.println(message == null ? "Invalid int input." : message.apply(mappedObject));
        continue; //Wait for next input, goto start of while.
      }

      return mappedObject; //Return the response.
    }
  }

  /**
   * Wait for a boolean input.
   * <p>
   * [yes, y, true] -> true
   * <p>
   * [no, n, false] -> false
   *
   * @param booleanPredicate checks if the entered boolean is valid.
   * @param message          output message if booleanPredicate check fails.
   * @return entered boolean value.
   */
  public boolean awaitBoolean(final Predicate<Boolean> booleanPredicate,
                              final Function<Boolean, String> message) {
    final Map<String, Boolean> valueMap = new HashMap<>() {  //Create new map with input to boolean. (Dirty way).
      { //Use scope in map to insert values.
        this.put("yes", true);
        this.put("y", true);
        this.put("true", true);

        this.put("no", false);
        this.put("n", false);
        this.put("false", false);
      }
    };
    return this.await(s -> valueMap.containsKey(s.toLowerCase()) /*Checks if the input is in the valueMap.*/,
      s -> valueMap.get(s.toLowerCase()) /*Returns the value of the valueMap input.*/,
      booleanPredicate,
      message);
  }

  /**
   * Wait for integer input.
   *
   * @param integerPredicate checks if the entered integer is valid.
   * @param message          output message if integerPredicate check fails.
   * @return entered integer
   */
  public Integer awaitInteger(final Predicate<Integer> integerPredicate,
                              final Function<Integer, String> message) {
    return this.await(s -> {
        try {
          Integer.parseInt(s); //Check if input is integer.
          return true; //Return, true if input can be converted to integer.
        } catch (final NumberFormatException ignore /*If parseInt fails -> handle below.*/) {
        }
        return false;
      }, Integer::parseInt /*Convert to integer*/,
      integerPredicate /*Check if integer.*/,
      message /*If check fails message.*/);
  }
}