package connectfour;

import connectfour.console.ConsoleInput;
import connectfour.console.Messages;
import connectfour.game.Round;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Manages the setup of the rounds and the creation of the round.
 */
public class GameEngine {
  /**
   * Main instance of the game engine.
   * Static variable so that other classes can access through {@link GameEngine#instance()}.
   */
  private static GameEngine instance;

  /**
   * Give the main instance back. Stored in {@link GameEngine#instance}.
   *
   * @return reference of {@link GameEngine#instance}.
   */
  public static GameEngine instance() {
    return instance;
  }

  /**
   * This variable will hold the {@link ConsoleInput} instance, that will be used for the hole game and round.
   * These can then be obtained through the method {@link GameEngine#consoleInput()}.
   */
  private final ConsoleInput consoleInput;
  /**
   * Reference to the current round. Can also be null.
   */
  private Round round;

  /**
   * Construct game engine instance. And set as static instance.
   * Created instance will be stored in {@link GameEngine#instance}.
   * The static instance can be obtained with {@link GameEngine#instance()}.
   */
  public GameEngine() {
    instance = this; //<- Set as instance.
    this.consoleInput = new ConsoleInput(); //Create new consoleInput instance.
  }

  /**
   * Welcome player and ask for simple steps.
   * -> Ask if the player needs description. If so goto {@link GameEngine#explain()} else {@link GameEngine#explain()}.
   */
  public void init() {
    System.out.println(Messages.LINE);
    System.out.println(Messages.LOGO); //Print logo.
    System.out.println(Messages.LINE);
    System.out.println(Messages.WELCOME); //Print welcome message.
    System.out.print(Messages.ASK_DESCRIPTION); //Print question for description.
    if (this.consoleInput.awaitBoolean(null /*null means that both values are used.*/, null /*No wrong input.*/)) { //Wait for user input.
      this.explain(); //Goto explain branch.
      return; //Return, there is nothing to init anymore.
    }
    System.out.println(Messages.INIT_WARNING); //Print warning.
    this.fieldSize();
  }

  /**
   * Gives the player the description of the game.
   * If player does not understand the game, it will loop this method -> go to description on links.
   */
  public void explain() {
    System.out.println(Messages.DESCRIPTION); //Print description.
    System.out.print(Messages.ASK_DESCRIPTION_UNDERSTOOD); //Print question, player understood.
    if (this.consoleInput.awaitBoolean(null /*null means that both values are used.*/, null /*No wrong input.*/)) {
      this.fieldSize(); //If player types 'true'.
    } else {
      this.explain(); //If player types 'false'.
    }
  }

  /**
   * Requests the pitch size from the player.
   */
  public void fieldSize() {
    this.round = null; //Deletes the current round.
    System.out.print(Messages.ASK_FIELD_SIZE);
    if (!this.consoleInput.awaitBoolean(null /*null means that both values are used.*/, null /*No wrong input.*/)) {
      this.customField(); //Goto field setup.
      return; //Return, player will set up custom field.
    }
    this.prepare(6, 7); //Prepare round with default size.
  }

  /**
   * SetUp custom pitch.
   */
  public void customField() {
    System.out.print(Messages.ASK_FIELD_SIZE_ROW); //Ask for row size.
    final int rows = this.consoleInput.awaitInteger(integer -> integer >= 4 /*Checks if given integer is 4 or bigger.*/,
      Messages.FIELD_SIZE_ROW_ERROR::formatted /*If given integer is smaller than 4.*/); //Get row size from input.

    System.out.print(Messages.ASK_FIELD_SIZE_COLUMN); //Ask for column size.
    final int columns = this.consoleInput.awaitInteger(integer -> integer >= 4,  /*Checks if given integer is 4 or bigger.*/
      Messages.FIELD_SIZE_COLUMN_ERROR::formatted /*If given integer is smaller than 4.*/);

    this.prepare(rows, columns); //Prepare round with input values.
  }

  /**
   * Create game from parameters and wait until the player starts.
   *
   * @param rows    number of rows for pitch.
   * @param columns number ob columns for pitch.
   */
  public void prepare(final int rows,
                      final int columns) {
    this.round = new Round(rows, columns); //Create round instance.
    System.out.printf("Round created with %d rows and %d columns.\n", rows, columns);
    System.out.print(Messages.ASK_READY); //Ask if player is ready.

    if (this.consoleInput.awaitBoolean(aBoolean -> aBoolean /*Checks if player types 'true' Otherwise wait.*/,
      aBoolean -> Messages.ASK_READY /*Reprint ready question. If not true.*/)) {
      this.round.init(); //Start game.
    }
  }

  /**
   * Reset the current running game.
   */
  public void reset() {
    System.out.print(Messages.ASK_RESET); //Ask for reset.
    if (this.consoleInput.awaitBoolean(null, /* means that both values are used.*/null)) {
      System.out.println("\n".repeat(5));
      this.fieldSize();
    } else {
      Runtime.getRuntime().exit(0);
    }
  }

  /**
   * Get instance of local {@link ConsoleInput}.
   *
   * @return reference of {@link GameEngine#consoleInput}
   */
  public ConsoleInput consoleInput() {
    return this.consoleInput;
  }

  /**
   * Get random instance, to use for random operations in game.
   *
   * @return random instance of {@link ThreadLocalRandom#current()}.
   */
  public Random random() {
    return ThreadLocalRandom.current(); //Get random instance of ThreadLocalRandom.
  }
}
