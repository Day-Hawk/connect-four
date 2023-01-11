package fourwins;

import fourwins.game.Round;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 */
public class Controller {

  private static Controller instance;

  public static Controller instance() {
    return instance;
  }

  public static void main(String[] args) {
    new Controller().init();
  }

  private final Scanner scanner;
  private Round round;

  protected Controller() {
    this.scanner = new Scanner(System.in);
    instance = this;
  }

  public void init() {
    System.out.println("""
       .----------------.  .----------------.  .----------------.  .----------------.  .-----------------. .----------------.
      | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
      | |   _    _     | || |              | || | _____  _____ | || |     _____    | || | ____  _____  | || |    _______   | |
      | |  | |  | |    | || |              | || ||_   _||_   _|| || |    |_   _|   | || ||_   \\|_   _| | || |   /  ___  |  | |
      | |  | |__| |_   | || |    ______    | || |  | | /\\ | |  | || |      | |     | || |  |   \\ | |   | || |  |  (__ \\_|  | |
      | |  |____   _|  | || |   |______|   | || |  | |/  \\| |  | || |      | |     | || |  | |\\ \\| |   | || |   '.___`-.   | |
      | |      _| |_   | || |              | || |  |   /\\   |  | || |     _| |_    | || | _| |_\\   |_  | || |  |`\\____) |  | |
      | |     |_____|  | || |              | || |  |__/  \\__|  | || |    |_____|   | || ||_____|\\____| | || |  |_______.'  | |
      | |              | || |              | || |              | || |              | || |              | || |              | |
      | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
       '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'
       """);
    System.out.println("Welcome to four wins.");
    System.out.print("Is an explanation of the game required. (yes/no): ");
    if (this.awaitBoolean(null)) {
      this.explain();
      return;
    }
    System.out.println("Ok, the game is now in setup. If an explanation is still needed, it must be restarted.");
    this.setUp();
  }

  public void explain() {
    System.out.println("\n Fabio stinkt. \n");

    System.out.print("Game understood? (yes): ");
    if (this.awaitBoolean(null)) {
      this.setUp();
    } else {
      this.explain();
    }
  }

  public void reset() {
    System.out.print("Reset? (yes): ");
    if (this.awaitBoolean(aBoolean -> aBoolean)) {
      System.out.println("\n".repeat(5));
      this.setUp();
    }
  }

  public void setUp() {
    this.round = null;
    System.out.print("Should the standard field size be used ? (yes/no): ");
    if (!this.awaitBoolean(null)) {
      this.setUpField();
      return;
    }
    this.prepare(6, 7);
  }

  public void setUpField() {
    System.out.print("Enter the number of rows desired (int >= 4): ");
    final int rows = this.awaitInteger(integer -> {
      return integer >= 4;
    }, "The number of rows must be greater than 4. [input %d]"::formatted);

    System.out.print("Enter the number of columns desired (int >= 4): ");
    final int columns = this.awaitInteger(integer -> {
      return integer >= 4;
    }, "The number of columns must be greater than 4. [input %d]"::formatted);

    this.prepare(rows, columns);
  }

  public void prepare(final int rows,
                      final int columns) {
    this.round = new Round(rows, columns);
    System.out.printf("Round created with %d rows and %d columns.\n", rows, columns);
    System.out.print("Are you ready? (yes): ");

    if (this.awaitBoolean(aBoolean -> aBoolean)) {
      this.round.init();
    }

  }

  public <T> T await(final Predicate<String> testInput,
                     final Function<String, T> mapFunction,
                     final Predicate<T> testValue,
                     final Function<T, String> message) {
    while (true) {
      final String textInput = this.scanner.next();

      if (testInput != null && !testInput.test(textInput)) {
        System.out.println("Invalid int input.");
        continue;
      }

      if (mapFunction == null) {
        System.out.println("No mapping function present, returns plain text.");
        continue;
      }

      final T mappedObject = mapFunction.apply(textInput);

      if (mappedObject == null) {
        continue;
      }

      if (testValue != null && !testValue.test(mappedObject)) {
        System.out.println(message == null ? "Invalid int input." : message.apply(mappedObject));
        continue;
      }

      return mappedObject;
    }
  }

  public boolean awaitBoolean(final Predicate<Boolean> booleanPredicate) {
    final Map<String, Boolean> valueMap = new HashMap<>() {
      {
        this.put("yes", true);
        this.put("y", true);
        this.put("true", true);
        this.put("no", false);
        this.put("n", false);
        this.put("false", false);
      }
    };
    return this.await(s -> valueMap.containsKey(s.toLowerCase()),
      valueMap::get,
      booleanPredicate == null ? aBoolean -> true : booleanPredicate,
      null);
  }

  /**
   * @param integerPredicate
   * @param message
   * @return
   */
  public Integer awaitInteger(final Predicate<Integer> integerPredicate,
                              final Function<Integer, String> message) {
    return this.await(s -> {
      try {
        Integer.parseInt(s);
        return true;
      } catch (final NumberFormatException ignore) {
      }
      return false;
    }, Integer::parseInt, integerPredicate, message);
  }
}
