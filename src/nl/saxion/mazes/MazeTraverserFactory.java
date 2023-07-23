package nl.saxion.mazes;

import java.util.function.Supplier;

@FunctionalInterface
public interface MazeTraverserFactory<D extends Direction<D>, M extends MazeTraverser<D>> extends Supplier<M> {
}
