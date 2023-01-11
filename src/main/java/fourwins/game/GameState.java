package fourwins.game;

/**
 * Expresses the state of a game.
 */
public enum GameState {
  /**
   * When the game is being created [default value].
   */
  PREPARING,

  /**
   * When the game is on.
   */
  RUNNING,

  /**
   * //When the game is finished and a winner is determined.
   */
  END;
}
