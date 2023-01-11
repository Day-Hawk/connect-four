package fourwins;

/**
 * Class which contains the main method of java.
 */
public class GameMain {
  /**
   * @param args
   */
  public static void main(String[] args) {
    new GameEngine() //Create new GameEngine instance.
      .init(); //Initialize this instance.
  }
}
