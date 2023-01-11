package fourwins.game;

import fourwins.Controller;
import fourwins.move.check.PendingMove;
import fourwins.console.ConsoleText;
import fourwins.game.exception.OutsideFieldException;
import fourwins.player.Token;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The round which holds the information about a game.
 */
public final class Round {
  /**
   * The playing field, which is occupied by the tokens.
   */
  private final Token[][] pitch;
  /**
   * This variable describes who is currently on the move.
   */
  private Token currentMove;
  public int totalMoves;
  /**
   *
   */
  private GameState gameState;
  private GameResult gameResult;

  /**
   * Creates a new round instance.
   *
   * @param rows    number of lines that holds the playing field.
   * @param columns number of columns holding the pitch.
   * @throws IllegalArgumentException if rows or columns is less than 4.
   */
  public Round(final int rows,
               final int columns) throws IllegalArgumentException {
    this.pitch = new Token[this.checkSizeValue(rows)][this.checkSizeValue(columns)]; //Create a two-dimensional array.
    this.currentMove = Token.EMPTY;
    this.totalMoves = 0;
    this.gameState = GameState.PREPARING;
    this.gameResult = GameResult.DRAW;

    for (int i = 0; i < this.rows() /*Max amount of rows in field.*/; i++) {
      Arrays.fill(this.pitch[i] /* Row */, Token.EMPTY); //Fill every row array with EMPTY tokens.
    }
  }

  public void init() {
    System.out.println("Initialisiere runde");
    this.currentMove = ThreadLocalRandom.current().nextDouble(.99)  /*Generate random to see who starts.*/ < 0.5 ?
      Token.PLAYER : //Random number between 0...0.99 is smaller than 0.5 player is first.
      Token.COM; //Otherwise COM.

    System.out.format("%s startet. \n", this.currentMove.name());

    new ConsoleText().printPitch(this);
    this.gameState = GameState.RUNNING;

    while (this.gameState == GameState.RUNNING) {
      if (this.totalMoves >= this.rows() * this.columns()) {
        this.end(null);
        return;
      }
      int value = this.currentMove.injectMove(this).awaitColumn();

      final PendingMove pendingMove = this.a(this.currentMove, value);

      if (pendingMove.checkAll()) {
        this.end(this.currentMove);
      }

      this.currentMove = this.currentMove.flipToken();
      new ConsoleText().printPitch(this);
      this.totalMoves++;
    }

  }

  /**
   * Get the current pitch.
   *
   * @return current instance of the field.
   */
  public Token[][] pitch() {
    return this.pitch;
  }

  /**
   * Indicates the number of rows of the board.
   *
   * @return number of rows as number.
   */
  public int rows() {
    return this.pitch.length;
  }

  /**
   * Returns the number of columns. Since all rows have the same number of columns,
   * the choice of row does not matter. Thus, the first line (index 0) is used.
   *
   * @return number of columns.
   */
  public int columns() {
    return this.pitch[0].length;
  }

  public Token currentMove() {
    return this.currentMove;
  }

  /**
   * Get the token that is on a certain field.
   *
   * @param rowIndex
   * @param columnIndex
   * @return
   */
  public Token tokenAt(final int rowIndex,
                       final int columnIndex) throws OutsideFieldException {
    final int maxRowIndex = this.rows() - 1; //Store rows to calculate once.
    final int maxColumIndex = this.columns() - 1; //Store columns to calculate once.

    if (rowIndex < 0 || rowIndex > maxRowIndex) { //Check if given row index is in bounds. 0 <= rowIndex < maxRowIndex. Throw error if outside bounds.
      throw new OutsideFieldException("Illegal row. Max row index is %d. [given: %d]".formatted(maxRowIndex, rowIndex));
    }
    if (columnIndex < 0 || columnIndex > maxColumIndex) { //Check if given colum index is in bounds. 0 <= columnIndex < maxColumIndex. Throw error if outside bounds.
      throw new OutsideFieldException("Illegal colum. Max colum index is %d. [given: %d]".formatted(maxColumIndex, columnIndex));
    }
    return this.pitch[rowIndex][columnIndex]; //Get token of field.
  }

  /**
   * @param rowIndex
   * @param columnIndex
   * @return
   */
  public Optional<Token> tokenAtOptional(final int rowIndex,
                                         final int columnIndex) {
    try {
      return Optional.ofNullable(this.tokenAt(rowIndex, columnIndex));
    } catch (final OutsideFieldException ignore /*Exception could be ignored, response will be null.*/) {
    }
    return Optional.empty();
  }

  public PendingMove a(final Token token,
                       final int columnIndex) {
    int rowIndex = -1; //Stores the selected row -> row also used as topArray.
    Token[] topArray = null; //This variable will hold the row on top of the column.
    for (int i = this.rows(); i > 0; i--) { //Reverse loop through the rows of the pitch. (-> From bottom to top)
      if (this.pitch[i - 1][columnIndex] != Token.EMPTY) { //Check if field at row with the given column is unused.
        continue; //Go to next row.
      }
      topArray = this.pitch[(rowIndex = i - 1)]; //Use row as topArray, because there is now value.
      break; //Because topArray found.
    }

    //Todo: Custom exception.
    if (topArray == null) { //Throw error if no topArray found -> overflow.
      throw new NullPointerException("Column overflow.");
    }

    topArray[columnIndex] = token;
    return new PendingMove(this, token, rowIndex, columnIndex);
  }

  public boolean b(final int columnIndex) {
    for (int i = this.rows(); i > 0; i--) { //Reverse loop through the rows of the pitch. (-> From bottom to top)
      if (this.pitch[i - 1][columnIndex] != Token.EMPTY) { //Check if field at row with the given column is unused.
        continue; //Go to next row.
      }
      return true;
    }
    return false;
  }

  public Set<Integer> availableColumns() {
    final Set<Integer> availableColumns = new HashSet<>();

    for (int i = 0; i < this.columns(); i++) {
      if (this.b(i)) {
        availableColumns.add(i);
      }
    }

    return availableColumns;
  }

  public void end(Token token) {
    this.gameState = GameState.END;
    this.gameResult = token == null ? GameResult.DRAW : token == Token.PLAYER ? GameResult.WON : GameResult.LOST;

    System.out.println(this.gameResult.message());

    Controller.instance().reset();
  }

  /**
   * Checks whether the number of fields is in one direction of the field 4 or above.
   *
   * @param size value to be checked.
   * @return Returns the size value so that this method can be used directly.
   * @throws IllegalArgumentException if the number is less than 4.
   */
  private int checkSizeValue(final int size) throws IllegalArgumentException {
    if (size < 4) { //If the value is less than four, it is not 4 or greater. Then an error is thrown.
      throw new IllegalArgumentException("Illegal size for field. Must be higher than 4. [given: %d]".formatted(size));
    }
    return size;
  }
}
