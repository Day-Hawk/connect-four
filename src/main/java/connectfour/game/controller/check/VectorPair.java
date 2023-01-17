package connectfour.game.controller.check;

import java.util.List;

/**
 * This class links a {@link VectorLine} and additional {@link Vector} objects calculated from the list.
 *
 * @param vectors          on which the predictionPoints are based.
 * @param predictionPoints calculated possibilities for the move.
 */
public record VectorPair(VectorLine vectors,
                         List<Vector> predictionPoints) {
}
