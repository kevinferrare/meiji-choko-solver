/*
 * Meiji Choko Solver
 * Copyright 2008 and beyond, Kévin Ferrare.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 3 of the License, or 
 * (at your option) any later version. 
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package org.kevinferrare.meijiChokoSolver.solver.dataStructures;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a 2D Matrix, backed up by an array
 * 
 * @author Kévin Ferrare
 * 
 */
public class Matrix {
	private int sizeX;
	private int sizeY;
	private int array[];

	public Matrix(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		array = new int[sizeX * sizeY];
	}

	/**
	 * Returns the value stored at the given coordinates.<br />
	 * Does not check bounds.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return value stored at the given coordinates
	 */
	public final int get(int x, int y) {
		return array[x + y * sizeX];
	}

	/**
	 * Sets the given value at the given coordinates.<br />
	 * Does not check bounds.
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param value
	 *            the value to store
	 */
	public void set(int x, int y, int value) {
		array[x + y * sizeX] = value;
	}

	/**
	 * Gets the matrix x size
	 * 
	 * @return the matrix x size
	 */
	public final int getSizeX() {
		return sizeX;
	}

	/**
	 * Gets the matrix y size
	 * 
	 * @return the matrix y size
	 */
	public final int getSizeY() {
		return sizeY;
	}

	/**
	 * Tests whether the given coordinate is inside the matrix
	 * 
	 * @param x
	 * @param y
	 * @return true if inside, false else
	 */
	public boolean isInside(int x, int y) {
		if (x >= this.getSizeX() || y >= this.getSizeY() || x < 0 || y < 0) {
			return false;
		}
		return true;
	}

	/**
	 * Tests whether the values of the matrix pointed by the two coordinates are equal or not,
	 * if the coordinates are not inside the matrix, returns false
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return true if the cells contain the same value, false instead
	 */
	public boolean isSame(int x1, int y1, int x2, int y2) {
		if (!isInside(x1, y1) || !isInside(x2, y2)) {
			return false;
		}
		return this.get(x1, y1) == this.get(x2, y2);
	}

	public boolean isSameRight(int x, int y) {
		return isSame(x, y, x + 1, y);
	}

	public boolean isSameLeft(int x, int y) {
		return isSame(x, y, x - 1, y);
	}

	public boolean isSameTop(int x, int y) {
		return isSame(x, y, x, y - 1);
	}

	public boolean isSameBottom(int x, int y) {
		return isSame(x, y, x, y + 1);
	}

	/**
	 * Returns a new Matrix that correspond to the current Matrix, vertically reflected<br />
	 * Example :<br />
	 * 1,2,3<br />
	 * 4,5,6<br />
	 * would return<br />
	 * 4,5,6<br />
	 * 1,2,3
	 * 
	 * @return the matrix, vertically reflected
	 */
	public Matrix verticalReflexion() {
		Matrix targetMatrix = new Matrix(this.getSizeX(), this.getSizeY());
		for (int x = 0; x < this.getSizeX(); x++) {
			for (int y = 0; y < this.getSizeY(); y++) {
				targetMatrix.set(x, y, this.get(x, this.getSizeY() - y - 1));
				targetMatrix.set(x, this.getSizeY() - y - 1, this.get(x, y));
			}
		}
		return targetMatrix;
	}

	/**
	 * Returns a new Matrix that correspond to the current Matrix, rotated 90° to the left<br />
	 * Example :<br />
	 * 1,2,3<br />
	 * 4,5,6<br />
	 * would return<br />
	 * 3,6<br />
	 * 2,5<br />
	 * 1,4<br />
	 * 
	 * @return the matrix, turned to the left
	 */
	public Matrix rotateLeft() {
		Matrix targetMatrix = new Matrix(this.getSizeY(), this.getSizeX());
		for (int x = 0; x < this.getSizeX(); x++) {
			for (int y = 0; y < this.getSizeY(); y++) {
				int value = this.get(x, y);
				targetMatrix.set(this.getSizeY() - 1 - y, x, value);
			}
		}
		return targetMatrix;
	}

	/**
	 * Returns a list containing all the different matrixes that it's possible to get
	 * from the given matrix by rotating it and reflecting it.
	 * 
	 * @param matrix
	 *            the original matrix
	 * @return a list of Matrix containing this matrix + all the possible rotation / reflection combinations
	 */
	public static List<Matrix> getAllArrangements(Matrix matrix) {
		List<Matrix> matrixes = new ArrayList<Matrix>();
		rotateAndAddIfDifferent(matrixes, matrix);
		rotateAndAddIfDifferent(matrixes, matrix.verticalReflexion());
		return matrixes;
	}

	/**
	 * Adds the matrix and the 90° 180°, 270° rotated version to the list
	 * 
	 * @param matrixes
	 *            the matrix list
	 * @param matrix
	 *            the matrix to add and rotate
	 */
	private static void rotateAndAddIfDifferent(List<Matrix> matrixes, Matrix matrix) {
		Matrix result = matrix;
		addIfNotAlreadyThere(matrixes, result);
		for (int i = 0; i < 3; i++) {
			result = result.rotateLeft();
			addIfNotAlreadyThere(matrixes, result);
		}
	}

	/**
	 * Adds the matrix to the given list if it's not there
	 * 
	 * @param matrixes
	 *            the matrix list
	 * @param matrix
	 *            the matrix to add
	 */
	private static void addIfNotAlreadyThere(List<Matrix> matrixes, Matrix matrix) {
		if (!matrixes.contains(matrix)) {
			matrixes.add(matrix);
		}
	}

	/**
	 * Create a Matrix object from the given 2 dimensional array, must be at least 1x1
	 * 
	 * @param array
	 *            the array from which the data are taken
	 * @return the created Matrix
	 */
	public static Matrix createFromArray(int array[][]) {
		Matrix matrix = new Matrix(array.length, array[0].length);
		for (int x = 0; x < matrix.getSizeX(); x++) {
			for (int y = 0; y < matrix.getSizeY(); y++) {
				matrix.set(x, y, array[x][y]);
			}
		}
		return matrix;
	}

	@Override
	public boolean equals(Object that) {
		if (that == this) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (that instanceof Matrix) {
			Matrix thatM = (Matrix) that;
			if (thatM.getSizeX() != this.getSizeX() || thatM.getSizeY() != this.getSizeY()) {
				return false;
			}
			for (int y = 0; y < this.getSizeY(); y++) {
				for (int x = 0; x < this.getSizeX(); x++) {
					if (this.get(x, y) != thatM.get(x, y)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer(sizeX * sizeY);
		for (int y = 0; y < this.getSizeY(); y++) {
			for (int x = 0; x < this.getSizeX(); x++) {
				int i = this.get(x, y);
				if (i < 10) {
					res.append(i);
				} else {
					res.append((char) ('a' + (i - 10)));
				}
			}
			res.append('\n');
		}
		return res.toString();
	}
}
