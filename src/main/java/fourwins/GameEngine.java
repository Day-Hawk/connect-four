package fourwins;

import fourwins.console.ConsoleInput;
import fourwins.console.Messages;
import fourwins.game.Round;

/**
 *
 */
public class GameEngine {
  private static GameEngine instance;

  public static GameEngine instance() {
    return instance;
  }

  private final ConsoleInput consoleInput;
  private Round round;

  protected GameEngine() {
    instance = this;
    this.consoleInput = new ConsoleInput(); //Create new consoleInput instance.
  }

  public void init() {
    System.out.println(Messages.LOGO); //Print logo.
    System.out.println(Messages.WELCOME); //Print welcome message.
    System.out.print(Messages.ASK_DESCRIPTION); //Print question for description.
    if (this.consoleInput.awaitBoolean(null /*null means that both values are used.*/)) { //Wait for user input.
      this.explain();
      return;
    }
    System.out.println(Messages.INIT_WARNING); //Print warning.
    this.fieldSize();
  }

  /**
   * Gives the player the description of the game.
   */
  public void explain() {
    System.out.println(Messages.DESCRIPTION); //Print description.
    System.out.print(Messages.ASK_DESCRIPTION_UNDERSTOOD); //Print question, player understood.
    if (this.consoleInput.awaitBoolean(null /*null means that both values are used.*/)) {
      this.fieldSize(); //If player types 'true'.
    } else {
      this.explain(); //If player types 'false'.
    }
  }

  /**
   * Requests the playing field size from the player.
   */
  public void fieldSize() {
    this.round = null; //Deletes the current round.
    System.out.print("Should the standard field size be used ? (yes/no): ");
    if (!this.consoleInput.awaitBoolean(null)) {
      this.customField();
      return;
    }
    this.prepare(6, 7);
  }

  public void customField() {
    System.out.print("Enter the number of rows desired (int >= 4): ");
    final int rows = this.consoleInput.awaitInteger(integer -> {
      return integer >= 4;
    }, "The number of rows must be greater than 4. [input %d]"::formatted);

    System.out.print("Enter the number of columns desired (int >= 4): ");
    final int columns = this.consoleInput.awaitInteger(integer -> {
      return integer >= 4;
    }, "The number of columns must be greater than 4. [input %d]"::formatted);

    this.prepare(rows, columns);
  }

  public void prepare(final int rows,
                      final int columns) {
    this.round = new Round(rows, columns);
    System.out.printf("Round created with %d rows and %d columns.\n", rows, columns);
    System.out.print("Are you ready? (yes): ");

    if (this.consoleInput.awaitBoolean(aBoolean -> aBoolean)) {
      this.round.init();
    }
  }

  public void reset() {
    System.out.print(Messages.ASK_RESET); //Ask for reset.
    if (this.consoleInput.awaitBoolean(aBoolean -> aBoolean)) {
      System.out.println("\n".repeat(5));
      this.fieldSize();
    }
  }

  public ConsoleInput consoleInput() {
    return this.consoleInput;
  }
}
