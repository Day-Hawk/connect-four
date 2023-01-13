package fourwins.console;

import fourwins.game.Round;
import fourwins.game.Token;
import fourwins.utils.ObjectUtils;

/**
 * This class builds the pitch of a round. -> {@link Round#pitch()}.
 * <p>
 * Example:
 * Rows: 6
 * Columns: 6
 * Player has to make the move.
 *
 * <pre>
 * +---+---+---+---+---+---+---+
 * |   |   |   |   |   |   |   |
 * +---+---+---+---+---+---+---+
 * |   |   |   |   |   |   |   |
 * +---+---+---+---+---+---+---+
 * |   |   |   |   |   |   |   |          X - PLAYER
 * +---+---+---+---+---+---+---+
 * |   |   |   |   |   |   |   |          O - COM
 * +---+---+---+---+---+---+---+
 * |   |   |   |   |   |   |   |        » It's PLAYER's turn!
 * +---+---+---+---+---+---+---+
 * |   |   |   |   |   |   |   |
 * +---+---+---+---+---+---+---+
 * | 1 | 2 | 3 | 4 | 5 | 6 | 7 |
 * </pre>
 */
public class ConsolePitch {
  /**
   * Round to create pitch {@link String} for.
   * Used information: {@link Round#pitch()}, {@link Round#rows()} ()}, {@link Round#columns()}.
   */
  private final Round round;

  /**
   * Create a new {@link ConsolePitch} instance with information from a {@link Round}.
   *
   * @param round to create a pitch for.
   */
  public ConsolePitch(final Round round) {
    ObjectUtils.throwIfNull(round, "Round is null."); //Check if round is not null. If given object is null, throw error.
    this.round = round;
  }

  /**
   * Create pitch as {@link String}.
   * Example see {@link ConsolePitch}.
   *
   * @throws NullPointerException if round is null.
   */
  public String buildPitchString() {
    final int totalColumns = round.columns(); //Store value of total columns.
    int width = this.intLength(totalColumns); //Set the length of the number to display.
    width = width % 2 == 0 ? width + 1 : width; //If width is even make it odd. -> Centre char of token in field.

    final StringBuilder stringBuilder = new StringBuilder() //Create new string builder instance.
      .append(Messages.LINE) //Add line to builder.
      .append("\n"); //Goto next line for pitch.

    this.appendLine(stringBuilder, totalColumns, width) /*Start with top of pitch.*/
      .append("\n" /*Next line with content.*/);

    final int printLegend = this.round.rows() / 2; //Calculate row to print legend of tokens.

    for (int row = 0; row < this.round.pitch().length; row++) { //Loop trough every row to print info.
      stringBuilder.append("|"); //Start row
      for (int columInRow = 0; columInRow < round.pitch()[row].length; columInRow++) { // Loop trough every element of row.
        final char tokenCharacter = round.tokenAtOptional(row, columInRow) //Get present token of element.
          .orElse(Token.EMPTY /*Use empty token if there is no token present.*/)
          .symbol(); //Get char from token -> use as symbol.

        stringBuilder
          .append((" %" + width + "s " /*Manipulate whitespace.*/).formatted(tokenCharacter)) //Set symbol of token of the element.
          .append("|"); //End field with dash.
      }

      if (printLegend - 1 == row) { //Check if row is calculated for player legend info. (On top of COM info.)
        this.appendLegendComponent(stringBuilder, Token.PLAYER); //Append Token.PLAYER info behind pitch.
      }
      if (printLegend == row) { //Check if row is calculated for player legend info. (Center of legend.)
        this.appendLegendComponent(stringBuilder, Token.COM);  //Append Token.COM info behind pitch.
      }
      if (this.round.running() && printLegend + 1 == row) { //If game is running add the token on move. (Under the COM info.)
        this.appendLegendMove(stringBuilder, this.round.currentMove()); //Append info behind pitch.
      }

      stringBuilder.append("\n");
      this.appendLine(stringBuilder, totalColumns, width).append("\n"); //Create line to separate rows.
    }

    for (int index = 1; index <= this.round.columns(); index++) { //Loop through all columns to add number under pitch.
      stringBuilder.append(("| %" + width + "s " /*Define whitespace between numbers to display column.*/)
        .formatted(this.round.columnHasSpace(index - 1) /*Check if column is full.*/ ?
          Integer.toString(index) /*If free space -> use number.*/ : "X" /*If full -> 'block' with x.*/));
    }
    stringBuilder.append("|\n"); //End line of numbers.

    return stringBuilder
      .append("\n") //Goto next line.
      .append(Messages.LINE) //Print line to separate.
      .toString(); //Convert stringBuilder to string.
  }

  /**
   * Append a horizontal line to {@link StringBuilder}.
   *
   * @param stringBuilder to append line to.
   * @param columns       number of columns.
   * @param width         with of chars in colum.
   * @return instance of stringBuilder to chain method.
   */
  private StringBuilder appendLine(final StringBuilder stringBuilder,
                                   final int columns,
                                   final int width) {
    return stringBuilder
      .append(("+" + "-".repeat(width + 2) /*Amount of width with free space on each side.*/).repeat(columns))
      .append("+");
  }

  /**
   * Append char info for {@link Token} to {@link StringBuilder}.
   *
   * @param stringBuilder to append legend info.
   * @param token         type of {@link Token} to append info.
   * @return instance of stringBuilder to chain method.
   */
  private StringBuilder appendLegendComponent(final StringBuilder stringBuilder,
                                              final Token token) {
    return stringBuilder
      .append(" ".repeat(10) /*Some space between info and pitch.*/)
      .append("%s - %s".formatted(token.symbol(), token.name()) /*Token info.*/);
  }

  /**
   * Append info which {@link Token} has to make the move. Append info to {@link StringBuilder}.
   * When building the project utf-8 must be selected, because the message will be displayed incorrectly.
   *
   * @param stringBuilder to append info to.
   * @param token         to set as next move info.
   * @return instance of stringBuilder to chain method.
   */
  private StringBuilder appendLegendMove(final StringBuilder stringBuilder,
                                         final Token token) {
    return stringBuilder
      .append(" ".repeat(8) /*Some space between info and pitch.*/)
      .append("» It's %s's turn!".formatted(token.name()) /*Info active move.*/);
  }

  /**
   * Get char amount of integer.
   *
   * @param lengthInt to get char amount of.
   * @return amount of chars.
   * Example:
   * 1 -> 1, 0 -> 1, 10 -> 2, 50 -> 2, 100 -> 3.
   */
  private int intLength(final int lengthInt) {
    return Integer.toString(lengthInt).length();
  }
}
