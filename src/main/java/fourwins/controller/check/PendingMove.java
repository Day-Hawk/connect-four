package fourwins.controller.check;

import fourwins.game.Round;
import fourwins.game.exception.OutsideFieldException;
import fourwins.player.Token;
import fourwins.utils.ObjectUtils;

public class PendingMove {
  private final Round round;
  private final Token token;
  private final int row;
  private final int column;

  public PendingMove(final Round round,
                     final Token token,
                     int row,
                     int column) {
    ObjectUtils.throwIfNull(round, "Round is null."); //Checks the round is not null.
    ObjectUtils.throwIfNull(token, "Token is null."); //Checks the token is not null.

    this.round = round;
    this.token = token;
    this.row = row;
    this.column = column;
  }

  public boolean checkAll() {
    return this.vertical() || this.horizontal() || this.diagonalLeft() || this.diagonalRight();
  }

  public boolean vertical() {
    int value = 0;
    for (int i = this.round.rows(); i > 0; i--) {
      try {
        value = this.round.tokenAt(i - 1, this.column) == this.token ? value + 1 : 0;
        if (value >= 4) {
          return true;
        }
      } catch (final OutsideFieldException exception) {
        break;
      }
    }
    return false;
  }

  public boolean horizontal() {
    int value = 0;

    for (int i = 0; i < this.round.columns(); i++) {
      try {
        value = this.round.tokenAt(this.row, i) == this.token ? value + 1 : 0;
        if (value >= 4) {
          return true;
        }
      } catch (final OutsideFieldException exception) {
        break;
      }
    }
    return false;
  }

  public boolean diagonalLeft() {
    int value = 0;

    final Vector leftVector = new Vector(this.column - 5 /* x */, this.row + 5 /* y */);
    final Vector rightVector = new Vector(this.column + 6 /* x */, this.row - 6 /* y */);

    Vector currentCursor = leftVector.copy();

    while (!currentCursor.equals(rightVector)) {
      value = this.round.tokenAtOptional(currentCursor.y(), currentCursor.x()).filter(this.token::equals).isPresent() ? value + 1 : 0;
      if (value >= 4) {
        return true;
      }
      currentCursor = currentCursor.add(new Vector(1, -1));
    }
    return false;
  }

  public boolean diagonalRight() {
    int value = 0;

    final Vector leftVector = new Vector(this.column - 6 /* x */, this.row - 6 /* y */);
    final Vector rightVector = new Vector(this.column + 5 /* x */, this.row + 5 /* y */);

    Vector currentCursor = rightVector.copy();

    while (!currentCursor.equals(leftVector)) {
      value = this.round.tokenAtOptional(currentCursor.y(), currentCursor.x()).filter(this.token::equals).isPresent() ? value + 1 : 0;
      if (value >= 4) {
        return true;
      }
      currentCursor = currentCursor.add(new Vector(-1, -1));
    }
    return false;
  }
}
