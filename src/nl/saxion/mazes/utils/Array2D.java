package nl.saxion.mazes.utils;

import java.lang.reflect.Array;

public class Array2D<T> {
	private final T[] data;
	private final int sizeX, sizeY;

	protected Array2D(Class<T> clazz, int sizeX, int sizeY) {
		this.data = (T[]) Array.newInstance(clazz, sizeX*sizeY);
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	protected T get(int x, int y) {
		if (x<0 || y<0 || x>=sizeX || y>=sizeY) {
			return null;
		}
		else {
			return data[y*sizeX+x];
		}
	}

	protected void set(int x, int y, T value) {
		if (x<0 || y<0 || x>=sizeX || y>=sizeY) {
			// Do nothing
		}
		else {
			data[y*sizeX+x] = value;
		}
	}

	protected int getSizeX() {
		return sizeX;
	}

	protected int getSizeY() {
		return sizeY;
	}
}
