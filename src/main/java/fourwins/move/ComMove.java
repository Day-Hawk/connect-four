package fourwins.move;

import fourwins.game.Round;
import fourwins.player.Token;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ComMove extends BaseMove {

  public ComMove(final Round round) {
    super(round);
  }

  @Override
  public int awaitColumn() {
    final Set<Integer> availableColumns = round().availableColumns();

    try {
      Thread.sleep(600L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    return availableColumns.toArray(new Integer[0])[ThreadLocalRandom.current().nextInt(availableColumns.size())];
  }

  @Override
  public Token token() {
    return Token.COM;
  }
}
