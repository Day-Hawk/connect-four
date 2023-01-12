package fourwins.controller.check;

import fourwins.game.Round;
import fourwins.game.exception.OutsideFieldException;
import fourwins.player.Token;
import fourwins.utils.ObjectUtils;

import java.util.function.Consumer;

/**
 * This class checks a move that has been made.
 * <p>
 * This class also extends the list of the {@link Round#comLines()} for the COM player.
 */
public class MoveChecker {
  /**
   * Round to check in.
   */
  private final Round round;
  /**
   * {@link Token} which initiated this check.
   */
  private final Token token;
  /**
   * Row that changed in pith of {@link Round#pitch()}.
   */
  private final int row;
  /**
   * Column that changed in pith of {@link Round#pitch()}.
   */
  private final int column;

  /**
   * Create a check instance.
   * <p>
   * The values of row and column can also be seen as coordinates. (Also as {@link Vector}).
   *
   * @param round  from which the playing field is to be tested.
   * @param token  type of the game move. {@link Token#COM} or {@link Token#PLAYER}.
   * @param row    in which the field has changed.
   * @param column in which the field has changed.
   */
  public MoveChecker(final Round round,
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

  public boolean checkAll(Consumer<VectorLine> fourLineConsumer) {
    return this.vertical(fourLineConsumer) ||
      this.horizontal(fourLineConsumer) ||
      this.diagonalLeft(fourLineConsumer) ||
      this.diagonalRight(fourLineConsumer);
  }

  /**
   * Checks if the line is met vertically in the column.
   *
   * @return true if there is a row of four in the column.
   */
  public boolean vertical(Consumer<VectorLine> fourLineConsumer) {
    boolean returnValue = false; //Value that is modified.
    final VectorLine verticalLines = new VectorLine(this.token); //Create a vector line that stores chains.

    for (int i = this.round.rows() - 1; i >= 0; i--) { //From maximum index to minimum. The bottom row in a column is the last value of the row array.
      try {
        if (this.round.tokenAt(i, this.column) == this.token) { //Checks which value is on the field.
          verticalLines.add(new Vector(this.column /*x*/, i /*y*/)); //Adds the vector to the line.
          if (verticalLines.size() >= 4) { //If the number is less than four, the whole process is repeated.
            System.out.println("Vertial 4");
            returnValue = true; //4 is reached!
            break;
          }
        } else {
          this.utilizeLines(verticalLines); //Writes the existing lines to memory. (For the COM).
        }
      } catch (final OutsideFieldException exception) {
        System.out.println("broke");
        break; //Position is outside the playing field.
      }
    }
    this.utilizeLines(verticalLines); //Writes the existing lines to memory. (For the COM).
    return returnValue; //Returns the value. If true the token has won.
  }

  public boolean horizontal(Consumer<VectorLine> fourLineConsumer) {
    boolean returnValue = false; //Value that is modified.
    final VectorLine verticalLines = new VectorLine(this.token); //Create a vector line that stores chains.

    for (int i = 0; i < this.round.columns(); i++) {
      try {
        if (this.round.tokenAt(this.row, i) == this.token) {
          verticalLines.add(new Vector(i /*x*/, this.row /*y*/)); //Adds the vector to the line.

          if (verticalLines.size() >= 4) { //If the number is less than four, the whole process is repeated.
            System.out.println("Horzional 4");
            returnValue = true; //4 is reached!
            break; //-> no storage needed. Game over.
          }
        } else {
          this.utilizeLines(verticalLines); //Writes the existing lines to memory. (For the COM).
        }
      } catch (final OutsideFieldException exception) {
        System.out.println("broke");
        break;
      }
    }
    this.utilizeLines(verticalLines); //Writes the existing lines to memory. (For the COM).
    return returnValue;
  }

  public boolean diagonalLeft(Consumer<VectorLine> fourLineConsumer) {
    boolean returnValue = false; //Value that is modified.
    final VectorLine verticalLines = new VectorLine(this.token); //Create a vector line that stores chains.

    final Vector leftVector = new Vector(this.column - 5 /* x */, this.row + 5 /* y */);
    final Vector rightVector = new Vector(this.column + 6 /* x */, this.row - 6 /* y */);

    Vector currentCursor = leftVector.copy();

    while (!currentCursor.equals(rightVector)) {
      if (this.round.tokenAtOptional(currentCursor.y(), currentCursor.x())
        .filter(this.token::equals)
        .isPresent()) {
        verticalLines.add(currentCursor); //Adds the vector to the line.

        if (verticalLines.size() >= 4) { //If the number is less than four, the whole process is repeated.
          System.out.println("Diagonal L 4");
          returnValue = true; //4 is reached! -> No need to write lines to memory. Game won.
          break;
        }
      } else {
        this.utilizeLines(verticalLines); //Store the line as it was interrupted. (For the COM).
      }
      currentCursor = currentCursor.add(new Vector(1, -1));
    }
    this.utilizeLines(verticalLines); //Save the line because the check is finished. (For the COM).
    return returnValue;
  }

  public boolean diagonalRight(Consumer<VectorLine> fourLineConsumer) {
    boolean returnValue = false; //Value that is modified.
    final VectorLine verticalLines = new VectorLine(this.token); //Create a vector line that stores chains.

    final Vector leftVector = new Vector(this.column - 6 /* x */, this.row - 6 /* y */);
    final Vector rightVector = new Vector(this.column + 5 /* x */, this.row + 5 /* y */);

    Vector currentCursor = rightVector.copy();

    while (!currentCursor.equals(leftVector)) {
      if (this.round.tokenAtOptional(currentCursor.y(), currentCursor.x())
        .filter(this.token::equals)
        .isPresent()) {
        verticalLines.add(currentCursor); //Adds the vector to the line.

        if (verticalLines.size() >= 4) { //If the number is less than four, the whole process is repeated.
          System.out.println("Diagonal R 4");
          returnValue = true; //4 is reached! -> No need to write lines to memory. Game won.
          break;
        }
      } else {
        this.utilizeLines(verticalLines); //Store the line as it was interrupted. (For the COM).
      }
      currentCursor = currentCursor.add(new Vector(-1, -1));
    }
    this.utilizeLines(verticalLines); //Save the line because the check is finished. (For the COM).
    return returnValue;
  }

  /**
   * @param vectors
   */
  public void utilizeLines(final VectorLine vectors) {
    ObjectUtils.throwIfNull(vectors, "VectorLine object is null."); //Throw error if vectors is null.

    if (vectors.size() > 1) { //Lines are only valid from two values. (Two points are needed to draw a line).
      final VectorLine vectorLine = new VectorLine(this.token); //Create new instance of line to use for COM.
      vectorLine.addAll(vectors); //Add alle vectors to new created one.
      this.round.comLines().add(vectorLine); //Add created line to lines.
    }
    vectors.clear(); //Clear given vector to avoid handling double data.
  }
}