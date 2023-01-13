package fourwins.game.controller;

import fourwins.GameEngine;
import fourwins.game.controller.check.Vector;
import fourwins.game.controller.check.VectorLine;
import fourwins.game.controller.check.VectorPair;
import fourwins.game.Round;
import fourwins.game.exception.OutsideFieldException;
import fourwins.game.Token;

import java.util.*;

/**
 * This class is the controller of the computer.
 * It processes values of an active round or calculates a random value.
 */
public final class ComController extends BaseController {
  /**
   * Create instance of {@link ComController}.
   *
   * @param round for which the controller is created.
   */
  public ComController(final Round round) {
    super(round);
  }

  /**
   * Calculates the next move of the computer.
   * The process uses data from the specified {@link BaseController#round()}.
   *
   * @return column into which the next move is to be played.
   */
  @Override
  public int awaitColumn() {
    try {
      Thread.sleep(500L); //Wait before returning, otherwise it feels like you're just playing against yourself.
    } catch (final InterruptedException ignored /*Ignore error as it is irrelevant.*/) {
    }

    return this.round()
      .comLines()
      .stream() //Stream set of VectorLines
      .filter(vectors -> vectors.token() != Token.EMPTY /*Filter -> Empty lines should not be processed.*/)
      .sorted((vectors1, vectors2) ->
        Integer.compare( //Compare vectors2 to vectors1 with calculated priority.
          this.calculateSortPosition(vectors2), //Convert vectors2 to it's value.
          this.calculateSortPosition(vectors1))) //Convert vectors1 to it's value.
      .map(this::lineToPair /*Calculate possible positions for next move.*/)
      .filter(vectorPair -> !vectorPair.predictionPoints().isEmpty() /*Filter pairs that do not provide opportunities.*/)
      .findFirst() //Find the first value of the list and wrap it in optional.
      .map(vectorPair -> {
        this.round().comLines().remove(vectorPair.vectors()); //Delete the vector line from the list. (Expired)
        return vectorPair.predictionPoints().get(
          GameEngine.instance().random() //Call the random of the game engine.
            .nextInt(vectorPair.predictionPoints().size() /*The maximum value of randomness is the number of possibilities.*/));
      })
      .map(Vector::x /*Map the vector with its x to the column of the move.*/)
      .orElseGet(this::randomColumn /*If no value is present, or none has been processed, a random number is calculated.*/);
  }

  /**
   * Defines this controller to use {@link Token#COM}.
   *
   * @return type: {@link Token#COM}.
   */
  @Override
  public Token token() {
    return Token.COM;
  }

  /**
   * Sets the value of the possible moves.
   * <p>
   * If a line has 3 or more (to be on the safe side) entries, the game is almost over:
   * The COM player first looks to himself to see if he could finish the round (=> value: 100).
   * If this is not the case, he looks to see if he has to block the player so that he does not win (=> value: 90).
   * <p>
   * If there are two in a row, the COM player tries to complete his turn again (=> value: 20).
   * If he does not have two in a row, the player with two in a row tries to block (=> value: 10).
   * <p>
   * If none of the conditions are met, 1 is returned.
   *
   * @param vectorLine line to be processed for value.
   * @return value of the game move.
   */
  public int calculateSortPosition(final VectorLine vectorLine) {
    if (vectorLine.size() >= 3) { //most important step, the game could be over after the next move.
      return vectorLine.token() == Token.PLAYER /*Checks if they are player tokens.*/ ?
        90 /*When three players are token in a row.*/ :
        100 /*When three com are token in a row.*/;
    }
    if (vectorLine.size() == 2) { //When two tokens simd in a row.
      return vectorLine.token() == Token.PLAYER /*Checks if they are player tokens.*/ ?
        10 /*Two player tokens.*/ :
        20 /*Two com tokens.*/;
    }
    return 1; //standard value.
  }

  /**
   * Calculate possible points for the next move.
   *
   * @param vectors from which possible points are to be calculated.
   * @return created {@link VectorPair} that maintains the line and the possible points.
   */
  public VectorPair lineToPair(final VectorLine vectors) {
    final List<Vector> predictionVectors = new ArrayList<>(); //Create a new list that will hold the possible vectors.
    final Vector[] vectorsArray = vectors.toArray(new Vector[0]); //Convert vectors list to array.

    final Vector from = vectorsArray[0]; //Get first element of array.
    final Vector to = vectorsArray[vectorsArray.length - 1]; //Get the last value of the array -> length -1.

    Vector direction = from.subtract(to); //Calculate the vector between the two points.
    direction = new Vector(
      direction.x() != 0 ? direction.x() / Math.abs(direction.x()) : 0 /*Calculate the base.*/,
      direction.y() != 0 ? direction.y() / Math.abs(direction.y()) : 0 /*Calculate the base.*/);
    final Vector inverted = new Vector(direction.x() * -1, direction.y() * -1 /*Invert the direction vector.*/);

    final Vector left = from.add(direction); //Sum the base direction from the left vector.
    final Vector right = to.add(inverted); //Sum the inverted base direction from the right vector.

    if (this.checkIfFieldIsValid(left)) { //Check if the vector is a possible move.
      predictionVectors.add(left); //If true add it to the predictionVectors.
    }
    if (this.checkIfFieldIsValid(right)) { //Check if the vector is a possible move.
      predictionVectors.add(right); //If true add it to the predictionVectors.
    }
    return new VectorPair(vectors, predictionVectors); //create a couple with the line and the possible points.
  }

  /**
   * Check if the vector is assignable by the game move.
   * This checks whether the vector is located in the game field at all and whether
   * the next field in the row and column match the vector coordinates.
   *
   * @param vector which is to be tested.
   * @return true, if vector can be used for next move.
   */
  public boolean checkIfFieldIsValid(final Vector vector) {
    try {
      return this.round().tokenAtOptional(vector.y(), vector.x()) //Get token on vector position.
        .filter(token -> token == Token.EMPTY /*Filter if token is present and not empty -> Already owned.*/)
        .isPresent() && //true if -> token is not null and Token.EMPTY.
        this.round().columnNext(vector.x()) == vector.y(); //Check if highest free row of column is the vector position.
    } catch (final OutsideFieldException ignore /*Handled below.*/) {
    }
    return false; //Otherwise, return false.
  }

  /**
   * Get a random column from the possible free columns.
   *
   * @return index of the column.
   */
  public int randomColumn() {
    final Set<Integer> availableColumns = round().availableColumns(); //get all available columns.
    return availableColumns.toArray(new Integer[0]) //Convert set to an array.
      [GameEngine.instance().random().nextInt(availableColumns.size()) /*Determine random position of the array.*/];
  }
}