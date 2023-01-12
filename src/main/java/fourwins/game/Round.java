package fourwins.game;

import fourwins.GameEngine;
import fourwins.console.Messages;
import fourwins.controller.check.MoveChecker;
import fourwins.console.ConsoleText;
import fourwins.controller.check.VectorLine;
import fourwins.game.exception.OutsideFieldException;
import fourwins.player.Token;

import java.util.*;
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
   * This variable describes who is currently on the move. More info: {@link Token}.
   * Default: {@link Token#EMPTY}.
   */
  private Token currentMove;
  /**
   * The number of moves made by both sides combined.
   */
  public int totalMoves;
  /**
   * Current state of the game. More info: {@link GameState}.
   * Default: {@link GameState#PREPARING}.
   */
  private GameState gameState;
  /**
   * Current result of the game. More info: {@link GameResult}.
   * Default: {@link GameResult#DRAW}.
   */
  private GameResult gameResult;

  private final Set<VectorLine> comLines;

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
    this.currentMove = Token.EMPTY; //Sets the default value to the variable.
    this.totalMoves = 0; //Sets the start value.
    this.gameState = GameState.PREPARING; //Sets the default value to the variable.
    this.gameResult = GameResult.DRAW; //Sets the default value to the variable.
    this.comLines = new HashSet<>();

    for (int i = 0; i < this.rows() /*Max amount of rows in field.*/; i++) {
      Arrays.fill(this.pitch[i] /* Row */, Token.EMPTY); //Fill every row array with EMPTY tokens.
    }
  }

  /**
   * Initialize the round.
   */
  public void init() {
    System.out.println(Messages.ROUND_CREATE); //Print round create message.
    this.currentMove = ThreadLocalRandom.current().nextDouble(.99)  /*Generate random to see who starts.*/ < 0.5 ?
      Token.PLAYER : //Random number between 0...0.99 is smaller than 0.5 player is first.
      Token.COM; //Otherwise COM.

    new ConsoleText().printPitch(this); //Prints an empty pitch into the console.
    this.gameState = GameState.RUNNING; //Changes the status of preparing to running.

    while (this.gameState == GameState.RUNNING) { //Round runs as long as the game is set to running.
      if (this.totalMoves >= this.rows() * this.columns()/* Comparison value are the maximum moves -> number of fields.*/) {
        this.end(null /*null means draw*/); //Finishes the game as draw.
        return;
      }

      final int columnIndex = this.currentMove.injectMove(this) //Create a controller from the active turn.
        .awaitColumn(); //This method interrupts the thread until a column is specified.
      final MoveChecker pendingMove = this.a(this.currentMove, columnIndex); //Perform the move. More info about the class.

      if (pendingMove.checkAll(vectors -> System.out.println(vectors))) { //The move is checked.
        new ConsoleText().printPitch(this); //Print the new field in the console.
        this.end(this.currentMove); //If the check is true, the game is over and the current player/bot has won.
        return;
      }

      this.currentMove = this.currentMove.flipToken(); //Transfers the active token to the other participant.
      new ConsoleText().printPitch(this); //Print the new field in the console.

      this.totalMoves++; //Increment moves.
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

  public Set<VectorLine> comLines() {
    return this.comLines;
  }

  /**
   * @param rowIndex
   * @throws OutsideFieldException
   */
  public int validateRowIndex(final int rowIndex) throws OutsideFieldException {
    final int maxRowIndex = this.rows() - 1; //Store rows to calculate once.
    if (rowIndex < 0 || rowIndex > maxRowIndex) { //Check if given row index is in bounds. 0 <= rowIndex < maxRowIndex. Throw error if outside bounds.
      throw new OutsideFieldException("Illegal row. Max row index is %d. [given: %d]".formatted(maxRowIndex, rowIndex));
    }
    return rowIndex;
  }

  /**
   * @param columnIndex
   * @return
   * @throws OutsideFieldException
   */
  public int validateColumnIndex(final int columnIndex) throws OutsideFieldException {
    final int maxColumIndex = this.columns() - 1; //Store columns to calculate once.
    if (columnIndex < 0 || columnIndex > maxColumIndex) { //Check if given colum index is in bounds. 0 <= columnIndex < maxColumIndex. Throw error if outside bounds.
      throw new OutsideFieldException("Illegal colum. Max colum index is %d. [given: %d]".formatted(maxColumIndex, columnIndex));
    }
    return columnIndex;
  }

  /**
   * Get the {@link Token} type of field.
   *
   * @param rowIndex    of the row in which to search.
   * @param columnIndex of the column in which to search.
   * @return the value in the field. (token is null: illegal state, token is {@link Token#EMPTY} -> no owner.)
   * @throws OutsideFieldException If one or both values are outside the field.
   */
  public Token tokenAt(final int rowIndex,
                       final int columnIndex) throws OutsideFieldException {
    return this.pitch[this.validateRowIndex(rowIndex)]/*Gets array in which the fields are*/
      [this.validateColumnIndex(columnIndex)]; //Get token of field.
  }

  /**
   * This method behaves similarly to {@link Round#tokenAt(int, int)}.
   * This method works with {@link Optional}.
   * If a field is not available, an error is generated at {@link Round#tokenAt(int, int)}.
   * This method returns an {@link Optional#empty()} instead (Stands for null).
   *
   * @param rowIndex    of the row in which to search.
   * @param columnIndex of the column in which to search.
   * @return values of the field if available in {@link Optional}.
   */
  public Optional<Token> tokenAtOptional(final int rowIndex,
                                         final int columnIndex) {
    try {
      return Optional.ofNullable(this.tokenAt(rowIndex, columnIndex)); //Wrap the value of the field in the optional.
    } catch (final OutsideFieldException ignore /*Exception could be ignored, response will be null.*/) {
    }
    return Optional.empty(); //Return an empty optional, since the field does not exist.
  }

  public MoveChecker a(final Token token,
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
    return new MoveChecker(this, token, rowIndex, columnIndex);
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

  public int c(final int columnIndex) throws OutsideFieldException {
    this.validateColumnIndex(columnIndex);
    for (int i = this.rows() - 1; i >= 0; i--) { //Reverse loop through the rows of the pitch. (-> From bottom to top)
      if (this.pitch[i][columnIndex] != Token.EMPTY) { //Check if field at row with the given column is unused.
        continue; //Go to next row.
      }
      return i;
    }
    return -1;
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

    GameEngine.instance().reset();
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
