package fourwins.game;

import fourwins.GameEngine;
import fourwins.console.Messages;
import fourwins.game.controller.check.MoveChecker;
import fourwins.console.ConsolePitch;
import fourwins.game.controller.check.VectorLine;
import fourwins.game.exception.OutsideFieldException;
import fourwins.utils.ObjectUtils;

import java.util.*;

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
   * These can then be obtained through the method {@link Round#currentMove()}.
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
  /**
   * Stores all available lines of pitch for COM player.
   * These can then be obtained through the method {@link Round#comLines()}.
   */
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
    this.comLines = new HashSet<>(); //Create new HashSet instance.

    for (int i = 0; i < this.rows() /*Max amount of rows in field.*/; i++) {
      Arrays.fill(this.pitch[i] /* Row */, Token.EMPTY); //Fill every row array with EMPTY tokens.
    }
  }

  /**
   * Initialize and manage the game.
   */
  public void init() {
    System.out.println(Messages.ROUND_CREATE); //Print round create message.
    this.currentMove = GameEngine.instance().random().nextDouble()  /*Generate random to see who starts.*/ < 0.5 ?
      Token.PLAYER : //Random number between 0...0.99 is smaller than 0.5 player is first.
      Token.COM; //Otherwise COM.

    this.gameState = GameState.RUNNING; //Changes the status of preparing to running.
    System.out.println(new ConsolePitch(this).buildPitchString()); //Prints an empty pitch into the console.

    while (this.gameState == GameState.RUNNING) { //Round runs as long as the game is set to running.
      if (this.totalMoves >= this.rows() * this.columns()/* Comparison value are the maximum moves -> number of fields.*/) {
        this.end(null /*null means draw*/); //Finishes the game as draw.
        return; //Return because game is over -> no more moves to make. [Pitch is full.]
      }

      final int columnIndex = this.currentMove.injectMove(this) //Create a controller from the active turn.
        .awaitColumn(); //This method interrupts the thread until a column is specified.
      final MoveChecker moveChecker = this.move(this.currentMove, columnIndex); //Perform the move. More info about the class.

      if (moveChecker.checkAll()) { //The move is checked.
        System.out.println(new ConsolePitch(this).buildPitchString()); //Print the new field in the console.
        this.end(this.currentMove); //If the check is true, the game is over and the current player/bot has won.
        return; //Return because game is over.
      }

      this.currentMove = this.currentMove.flipToken(); //Transfers the active token to the other participant.
      System.out.println(new ConsolePitch(this).buildPitchString()); //Print the new field in the console.

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

  /**
   * Get the current {@link Token} of variable {@link Round#currentMove}.
   *
   * @return reference stored in variable {@link Round#currentMove}.
   */
  public Token currentMove() {
    return this.currentMove;
  }

  /**
   * Get all {@link VectorLine} stored for COM to predict next moves.
   *
   * @return reference of {@link Round#comLines}.
   */
  public Set<VectorLine> comLines() {
    return this.comLines;
  }

  /**
   * Checks if the given index is a valid row in the game field.
   * <p>
   * -> 0 &lt;= rowIndex &lt; maxRowIndex(max amount of rows - 1)
   *
   * @param rowIndex which must be checked.
   * @return rowIndex so this method can be used chained.
   * @throws OutsideFieldException if given rowIndex is out of rang. (No present on pitch.)
   */
  public int validateRowIndex(final int rowIndex) throws OutsideFieldException {
    final int maxRowIndex = this.rows() - 1; //Store rows to calculate once.
    if (rowIndex < 0 || rowIndex > maxRowIndex) { //Check if given row index is in bounds. 0 <= rowIndex < maxRowIndex. Throw error if outside bounds.
      throw new OutsideFieldException("Illegal row. Max row index is %d. [given: %d]".formatted(maxRowIndex, rowIndex));
    }
    return rowIndex;
  }

  /**
   * Checks if the given index is a valid column in the game field.
   * <p>
   * -> 0 &lt;= rowIndex &lt; maxRowIndex(max amount of rows - 1)
   *
   * @param columnIndex which must be checked.
   * @return rowIndex so this method can be used chained
   * @throws OutsideFieldException if given rowIndex is out of rang. (No present on pitch.)
   */
  public int validateColumnIndex(final int columnIndex) throws OutsideFieldException {
    final int maxColumIndex = this.columns() - 1; //Store columns to calculate once.
    if (columnIndex < 0 || columnIndex > maxColumIndex) { //Check if given colum index is in bounds. 0 <= columnIndex < maxColumIndex. Throw error if outside bounds.
      throw new OutsideFieldException("Illegal colum. Max colum index is %d. [given: %d]".formatted(maxColumIndex, columnIndex));
    }
    return columnIndex;
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

  /**
   * Get the {@link Token} type of field.
   *
   * @param rowIndex    of the row in which to search.
   * @param columnIndex of the column in which to search.
   * @return the value in the field. (token is null: illegal state, token is {@link Token#EMPTY} -> no owner.)
   * @throws OutsideFieldException if one or both values are outside the field.
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

  /**
   * Create a MoveChecker instance from parameters.
   * The token is entered into the field, but the move is not checked directly.
   *
   * @param token       to be entered in the field.
   * @param columnIndex column into which the token is inserted.
   * @return created {@link MoveChecker} instance.
   * @throws NullPointerException if token is null.
   */
  public MoveChecker move(final Token token,
                          final int columnIndex) throws NullPointerException {
    ObjectUtils.throwIfNull(token, "Given token is null."); //Checks if token is not null.

    int rowIndex = -1; //Stores the selected row -> row also used as topArray.
    Token[] topArray = null; //This variable will hold the row on top of the column.
    for (int i = this.rows() - 1; i >= 0; i--) { //Reverse loop through the rows of the pitch. (-> From bottom to top)
      if (this.pitch[i][columnIndex] != Token.EMPTY) { //Check if field at row with the given column is unused.
        continue; //Go to next row.
      }
      topArray = this.pitch[(rowIndex = i)]; //Use row as topArray, because there is now value.
      break; //Because topArray found.
    }

    ObjectUtils.throwIfNull(topArray, "Column overflow."); //Throw error if no topArray found -> overflow.

    topArray[columnIndex] = token; //Set token on field.
    return new MoveChecker(this, token, rowIndex, columnIndex);
  }

  /**
   * Checks whether a column still has a free space to make a move.
   *
   * @param columnIndex to be checked.
   * @return true, if column has at least 1 free space.
   */
  public boolean columnHasSpace(final int columnIndex) {
    try {
      this.validateColumnIndex(columnIndex);
      for (int i = this.rows(); i > 0; i--) { //Reverse loop through the rows of the pitch. (-> From bottom to top)
        if (this.pitch[i - 1][columnIndex] != Token.EMPTY) { //Check if field at row with the given column is unused.
          continue; //Go to next row.
        }
        return true; //Token is EMPTY -> true.
      }
    } catch (final OutsideFieldException ignore /*Exception could be ignored -> if thrown return value is false.*/) {
    }
    return false;
  }

  /**
   * The next free space in the column is displayed.
   * Similar to {@link Round#columnHasSpace(int)}, but this method returns the column.
   *
   * @param columnIndex to be checked.
   * @return index of row with free field.
   * @throws OutsideFieldException if columnIndex is outside the field.
   */
  public int columnNext(final int columnIndex) throws OutsideFieldException {
    this.validateColumnIndex(columnIndex);
    for (int row = this.rows() - 1; row >= 0; row--) { //Reverse loop through the rows of the pitch. (-> From bottom to top)
      if (this.pitch[row][columnIndex] != Token.EMPTY) { //Check if field at row with the given column is unused.
        continue; //Go to next row.
      }
      return row; //Row with free field at columnIndex.
    }
    return -1;
  }

  /**
   * A {@link Set} with all free columns.
   * Function checks for each column: {@link Round#columnHasSpace(int)}
   * -> if this is true, the column is included in the list.
   *
   * @return list with free columns. If {@link Set} is empty -> game is over.
   */
  public Set<Integer> availableColumns() {
    final Set<Integer> availableColumns = new HashSet<>(); //Create new set instance.
    for (int column = 0; column < this.columns(); column++) { //Loop trough every column of pitch.
      if (this.columnHasSpace(column)) { //Check if column has free space. If true ->
        availableColumns.add(column); //Add column int to set.
      }
    }
    return availableColumns; //Return set.
  }

  /**
   * Get if the game is running or not.
   *
   * @return true, if {@link Round#gameState} is set to {@link GameState#RUNNING}.
   */
  public boolean running() {
    return this.gameState == GameState.RUNNING;
  }

  /**
   * End this round.
   *
   * @param token could be null.
   */
  private void end(final Token token) {
    this.gameState = GameState.END; //Set state as end.
    this.gameResult = token == null ? GameResult.DRAW /*If given token is null.*/ :
      token == Token.PLAYER /*Runs if token is present*/ ? GameResult.WON /*Token is player*/ :
        GameResult.LOST /*Token is com*/;

    System.out.println(this.gameResult.message()); //Print fancy screen of GameResult.
    System.out.println(Messages.LINE);
    GameEngine.instance().reset(); //Go to reset -> start next game.
  }
}