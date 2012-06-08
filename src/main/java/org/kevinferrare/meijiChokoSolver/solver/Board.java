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
package org.kevinferrare.meijiChokoSolver.solver;

import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Coordinate;
import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Matrix;

/**
 * The Board is where the pieces are placed by the solver.
 * 
 * @author Kévin Ferrare
 * 
 */
public class Board extends Matrix {
	/**
	 * Constructs a board of size sizeX, sizeY
	 * 
	 * @param sizeX
	 *            the number of columns of the board
	 * @param sizeY
	 *            the number of rows of the board
	 */
	public Board(int sizeX, int sizeY) {
		super(sizeX, sizeY);
	}

	/**
	 * Searches the first available cell on a given line (lineNo)
	 * from the given index on that line (linePos)
	 * 
	 * @param lineNo
	 *            the line number
	 * @param linePos
	 *            the position on that line from which to search
	 * @return the index of the available cell or -1 if not found
	 */
	public int findLineFirstAvailableCell(int lineNo, int linePos) {
		for (int x = linePos; x < this.getSizeX(); x++) {
			if (this.get(x, lineNo) == 0) {
				return x;
			}
		}
		return -1;
	}

	/**
	 * Tests whether the given piece shape can be placed at the given position
	 * 
	 * @param shape
	 *            the shape to be placed
	 * @param posX
	 *            the abscissa of the piece
	 * @param posY
	 *            the ordinate of the piece
	 * @return true if the piece can be placed, false else
	 */
	public boolean canPlacePieceShape(PieceShape shape, int posX, int posY) {
		if (posX + shape.getSizeX() > this.getSizeX() || posY + shape.getSizeY() > this.getSizeY()) {
			return false;
		}
		for (Coordinate coord : shape.getOccupied()) {
			if (this.get(posX + coord.getX(), posY + coord.getY()) != 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Actually places a piece shape on the board at the given position
	 * gives the board the shape's color value
	 * 
	 * @param shape
	 *            the shape to be placed
	 * @param posX
	 *            the abscissa of the piece
	 * @param posY
	 *            the ordinate of the piece
	 */
	public void placePieceShape(PieceShape shape, int posX, int posY) {
		for (Coordinate coord : shape.getOccupied()) {
			this.set(posX + coord.getX(), posY + coord.getY(), shape.getColor());
		}
	}

	/**
	 * Removes a piece shape from the board at the given position
	 * 
	 * @param shape
	 *            the shape to be removed
	 * @param posX
	 *            the abscissa of the piece
	 * @param posY
	 *            the ordinate of the piece
	 */
	public void removePieceShape(PieceShape shape, int posX, int posY) {
		for (Coordinate coord : shape.getOccupied()) {
			this.set(posX + coord.getX(), posY + coord.getY(), 0);
		}
	}
}
