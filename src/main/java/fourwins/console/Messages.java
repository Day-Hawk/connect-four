package fourwins.console;

/**
 * This interface holds all message from game.
 */
public interface Messages {
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
  String WELCOME = "Welcome to four wins.";

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

     Fabio stinkt.

    """;

  /**
   * Variable holds, if the player understood the description.
   */
  String ASK_DESCRIPTION_UNDERSTOOD = "Game understood? (yes): ";

  /**
   * Variable holds the question, if game should be reset.
   */
  String ASK_RESET = "Reset? (yes): ";
}