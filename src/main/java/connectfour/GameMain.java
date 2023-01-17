package connectfour;

/**
 * Class which contains the main method of java.
 */
public final class GameMain {
  /**
   * Entry point.
   *
   * @param args arguments from jar launcher. Ignored by this program.
   */
  public static void main(String[] args) {
    new GameEngine() //Create new GameEngine instance.
      .init(); //Initialize this instance.
  }

  /**
   * Block constructor for other classes.
   */
  private GameMain() {
    /*Nothing to see here.*/
  }
}
