package fourwins.move;

import fourwins.Controller;
import fourwins.game.Round;
import fourwins.player.Token;

public class PlayerMove extends BaseMove {

  public PlayerMove(final Round round) {
    super(round);
  }

  @Override
  public int awaitColumn() {
    int column = -1;

    while (column < 0) {
      int maxInput = this.round().columns();
      System.out.printf("Gebe eine zahl zwischen 1 und %d ein: ", maxInput);

      int inputIntegerIndex = Controller.instance().awaitInteger(integer -> {
        return integer > 0 && integer <= maxInput;
      }, "The entered number is outside the range. [input: %d]"::formatted);

      if (!this.round().b(inputIntegerIndex - 1)) {
        System.out.println("Choose another column, this one is full.");
        continue;
      }

      column = inputIntegerIndex;
    }

    System.out.printf("Du hast %d eingegeben. \n", (column));
    return column - 1;
  }

  @Override
  public Token token() {
    return Token.PLAYER;
  }
}
