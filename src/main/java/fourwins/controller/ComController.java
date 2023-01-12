package fourwins.controller;

import fourwins.controller.check.Vector;
import fourwins.controller.check.VectorLine;
import fourwins.controller.check.VectorPair;
import fourwins.game.Round;
import fourwins.game.exception.OutsideFieldException;
import fourwins.player.Token;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ComController extends BaseController {

  public ComController(final Round round) {
    super(round);
  }

  @Override
  public int awaitColumn() {
    try {
      Thread.sleep(500L); //Wait before returning, otherwise it feels like you're just playing against yourself.
    } catch (final InterruptedException exception /*Ignore error as it is irrelevant.*/) {
    }

    return this.round()
      .comLines()
      .stream()
      .filter(vectors -> vectors.token() != Token.EMPTY)
      .sorted((o1, o2) -> Integer.compare(this.calculateSortPosition(o2), this.calculateSortPosition(o1)))
      .map(this::f)
      .filter(vectorPair -> !vectorPair.extraPoints().isEmpty())
      .findFirst()
      .map(vectorPair -> {
        this.round().comLines().remove(vectorPair.vectors());
        return vectorPair.extraPoints().get(ThreadLocalRandom.current().nextInt(vectorPair.extraPoints().size()));
      })
      .map(Vector::x)
      .orElseGet(this::randomColumn);
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

  public int calculateSortPosition(final VectorLine vectorLine) {
    if (vectorLine.size() >= 3) {
      return vectorLine.token() == Token.PLAYER ? 90 : 100;
    }
    if (vectorLine.size() == 2) {
      return vectorLine.token() == Token.PLAYER ? 10 : 20;
    }
    return 1;
  }

  public VectorPair f(final VectorLine vectors) {
    final List<Vector> extraPoints = new ArrayList<>();
    final Vector[] vectorsArray = vectors.toArray(new Vector[0]);

    final Vector from = vectorsArray[0];
    final Vector to = vectorsArray[vectorsArray.length - 1];

    Vector directionVector = from.subtract(to);
    directionVector = new Vector(directionVector.x() != 0 ? directionVector.x() / Math.abs(directionVector.x()) : 0,
      directionVector.y() != 0 ? directionVector.y() / Math.abs(directionVector.y()) : 0);
    Vector invertedDirectionVector = new Vector(directionVector.x() * -1, directionVector.y() * -1);

    Vector left = from.add(directionVector);
    Vector right = to.add(invertedDirectionVector);

    if (this.checkIfFieldIsValid(left)) {
      extraPoints.add(left);
    }
    if (this.checkIfFieldIsValid(right)) {
      extraPoints.add(right);
    }
    return new VectorPair(vectors, extraPoints);
  }

  public boolean checkIfFieldIsValid(final Vector vector) {
    try {
      return this.round().tokenAtOptional(vector.y(), vector.x())
        .filter(token -> token == Token.EMPTY)
        .isPresent() && this.round().columnNext(vector.x()) == vector.y();
    } catch (final OutsideFieldException ignore) {
    }
    return false;
  }

  public int randomColumn() {
    return round().availableColumns().toArray(new Integer[0])
      [ThreadLocalRandom.current().nextInt(round().availableColumns().size())];
  }
}
