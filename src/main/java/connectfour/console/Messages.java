package connectfour.console;

/**
 * This interface holds all message from game.
 */
public interface Messages {
  /**
   * line.
   */
  String LINE = "===========================================================================================================================";

  /**
   * Variable holds the logo message.
   */
  String LOGO = """
       _____                            _              ______              \s
      / ____|                          | |            |  ____|             \s
     | |     ___  _ __  _ __   ___  ___| |_   ______  | |__ ___  _   _ _ __\s
     | |    / _ \\| '_ \\| '_ \\ / _ \\/ __| __| |______| |  __/ _ \\| | | | '__|
     | |___| (_) | | | | | | |  __/ (__| |_           | | | (_) | |_| | |  \s
      \\_____\\___/|_| |_|_| |_|\\___|\\___|\\__|          |_|  \\___/ \\__,_|_|  \s
    """;

  /**
   * Variable holds the welcome message.
   */
  String WELCOME = """

    Welcome to connect four.
    Draw the command window at least as large that the '=' strokes are displayed in one line.
    """;

  /**
   * Variable holds the question, if a explanation is required.
   */
  String ASK_DESCRIPTION = "Is an explanation of the game required. (yes/no): ";

  /**
   * Variable holds the info for explanation.
   */
  String INIT_WARNING = "Ok, the game is now in setup. If an explanation is still needed, it must be restarted.";

  /**
   * Variable holds game description.
   */
  String DESCRIPTION = """

     Connect Four (also known as Connect 4, Four Up, Plot Four, Find Four, Captain's Mistress,
     Four in a Row, Drop Four, and Gravitrips in the Soviet Union) is a two-player connection board game,
     in which the players choose a color and then take turns dropping colored tokens into a seven-column,
     six-row vertically suspended grid. The pieces fall straight down, occupying the lowest available space within the column.
     The objective of the game is to be the first to form a horizontal, vertical, or diagonal line of four of one's own tokens.
     Connect Four is a solved game. The first player can always win by playing the right moves.

     (-> source: https://en.wikipedia.org/wiki/Connect_Four)

    """;

  /**
   * Variable holds, if the player understood the description.
   */
  String ASK_DESCRIPTION_UNDERSTOOD = "Game understood? (yes): ";

  /**
   * Variable holds the question, if game should be reset.
   */
  String ASK_RESET = "Reset? (yes/no): ";


  /**
   * Ask if player is ready.
   */
  String ASK_READY = "Are you ready? (yes): ";

  /**
   * Ask if custom field should be used.
   */
  String ASK_FIELD_SIZE = "Should the standard field size be used ? (yes/no): ";

  /**
   * Ask for custom rows amount.
   */
  String ASK_FIELD_SIZE_ROW = "Enter the number of rows desired (int >= 4): ";


  /**
   * Error message if given row is outside range.
   */
  String FIELD_SIZE_ROW_ERROR = "The number of rows must be greater than 4. [input %d]";

  /**
   * Ask for custom columns amount.
   */
  String ASK_FIELD_SIZE_COLUMN = "Enter the number of columns desired (int >= 4): ";


  /**
   * Error message if given row is outside range.
   */
  String FIELD_SIZE_COLUMN_ERROR = "The number of columns must be greater than 4. [input %d]";


  /**
   * Variable holds test create message for round.
   */
  String ROUND_CREATE = "Round is created, this process may take some time.";

  /**
   * Ask player for a number to input.
   */
  String ASK_PLAYER_CONTROLLER_INPUT = "Enter a number between 1 and %d: ";

  /**
   * Error if number is out of range
   */
  String PLAYER_CONTROLLER_INPUT_RANGE = "The entered number is outside the range. [input: %d]";

  /**
   * Error if column is already full.
   */
  String PLAYER_CONTROLLER_INPUT_FULL = "Choose another column, this one is full.";

  /**
   * Message if input was successful.
   */
  String PLAYER_CONTROLLER_INPUT = "You have entered %d. \n";
}