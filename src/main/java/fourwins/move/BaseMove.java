package fourwins.move;

import fourwins.game.Round;
import fourwins.player.Token;

public abstract class BaseMove {
  private final Round round;

  public BaseMove(Round round) {
    this.round = round;
  }

  public Round round() {
    return this.round;
  }

  public abstract int awaitColumn();

  public abstract Token token();
}
