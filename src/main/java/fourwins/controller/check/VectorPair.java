package fourwins.controller.check;

import java.util.List;

public record VectorPair(VectorLine vectors,
                         List<Vector> extraPoints) {
}
