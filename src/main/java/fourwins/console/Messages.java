package fourwins.console;

/**
 * This interface holds all message from game.
 */
public interface Messages {

  String LINE = "===========================================================================================================================";

  /**
   * Variable holds the logo message.
   */
  String LOGO = """
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
    """;

  /**
   * Variable holds the welcome message.
   */
  String WELCOME = """

    Welcome to four wins.
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

     Each player has 21 tiles of the same color. When a player drops a tile into a column,
     it occupies the lowest free space in the column. The winner is the first player to line up four or
     more of his pieces horizontally, vertically or diagonally.

     (-> translated Source: https://de.wikipedia.org/wiki/Vier_gewinnt)

    """;

  /**
   * Variable holds, if the player understood the description.
   */
  String ASK_DESCRIPTION_UNDERSTOOD = "Game understood? (yes): ";

  /**
   * Variable holds the question, if game should be reset.
   */
  String ASK_RESET = "Reset? (yes): ";


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