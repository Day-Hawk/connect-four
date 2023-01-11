package fourwins.check;

import fourwins.Round;
import fourwins.Vector;
import fourwins.exception.OutsideFieldException;
import fourwins.player.Token;

public class PendingMove {
  private final Round round;
  private final Token token;
  private final int row;
  private final int column;

  public PendingMove(Round round,
                     Token token,
                     int row,
                     int column) {
    this.round = round;
    this.token = token;
    this.row = row;
    this.column = column;
  }

  public boolean checkAll() {
    return this.getTopVertical() || this.getHorizontal() || this.getDiagonalLeft() || this.getDiagonalRight();
  }

  public boolean getTopVertical() {
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

  public boolean getDiagonalLeft() {
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

  public boolean getDiagonalRight() {
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

  public boolean getHorizontal() {
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
}
