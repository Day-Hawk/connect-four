package fourwins.console;

import fourwins.game.Round;
import fourwins.player.Token;
import fourwins.utils.ObjectUtils;

public class ConsoleText {

  /**
   * @param round for which the field is to be printed.
   * @throws NullPointerException if round is null.
   */
  public void printPitch(final Round round) throws NullPointerException {
    ObjectUtils.throwIfNull(round, "Round is null."); //Check if round is not null. If given object is null, throw error.

    final int totalColumns = round.columns(); //Store value of total columns.
    final StringBuilder string = new StringBuilder("\n".repeat(3))
      .append(Messages.LINE)
      .append(this.horizontalLine(totalColumns))
      .append("\n");

    final int printLegend = round.rows() / 2;

    for (int row = 0; row < round.pitch().length; row++) {
      string.append("|");
      for (int columInRow = 0; columInRow < round.pitch()[row].length; columInRow++) {
        final char tokenCharacter = round.tokenAtOptional(row, columInRow)
          .orElse(Token.EMPTY /*Use empty token if there is no token present.*/)
          .symbol();

        string.append(" %s |".formatted(tokenCharacter));
      }

      if (printLegend - 1 == row) {
        this.appendLegendComponent(string, Token.PLAYER);
      }
      if (printLegend == row) {
        this.appendLegendComponent(string, Token.COM);
      }
      if (printLegend + 1 == row) {
        this.appendLegendMove(string, round.currentMove());
      }

      string.append("\n").append(this.horizontalLine(totalColumns)).append("\n");
    }

    for (int i = 1; i <= round.columns(); i++) {
      string.append("  %s ".formatted(round.columnHasSpace(i - 1) ? Integer.toString(i) : "X"));
    }

    System.out.println(string);
  }

  /**
   * Create a line for the table with the number of columns.
   *
   * @param columns number of columns.
   * @return created line as string.
   */
  private String horizontalLine(final int columns) {
    return "+---" /*Base string to duplicate.*/
      .repeat(columns) + "+";
  }

  private void appendLegendComponent(final StringBuilder stringBuilder,
                                     final Token token) {
    stringBuilder.append(" ".repeat(10)).append("%s - %s".formatted(token.symbol(), token.name()));
  }

  private void appendLegendMove(final StringBuilder stringBuilder,
                                final Token token) {
    stringBuilder.append(" ".repeat(5)).append("It's %s's turn!".formatted(token.name()));
  }
}
