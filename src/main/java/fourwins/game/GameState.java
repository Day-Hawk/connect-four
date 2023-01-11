package fourwins.game;

/**
 * Expresses the state of a game.
 */
public enum GameState {
  PREPARING, //When the game is being created [default value].
  RUNNING, //When the game is on.
  END; //When the game is finished and a winner is determined.
}
