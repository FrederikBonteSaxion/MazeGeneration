package nl.saxion.mazes;

import java.util.HashMap;
import java.util.Iterator;

public class TraverserList<D extends Direction<D>, M extends MazeTraverser<D>> implements Iterable<M> {
	private final HashMap<Character<D>, M> characters;
	private final MazeTraverserFactory<D,M> supplier;

	public TraverserList(MazeTraverserFactory<D,M> supplier) {
		this.characters = new HashMap<>();
		this.supplier = supplier;
	}

	public void register(Character<D> character) {
		M traverser = this.supplier.get();
		this.characters.put(character, traverser);
		character.setMazeTraverser(traverser);
	}

	@Override
	public Iterator<M> iterator() {
		return characters.values().iterator();
	}
}
