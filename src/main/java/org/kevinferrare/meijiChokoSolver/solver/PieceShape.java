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

import java.util.ArrayList;
import java.util.List;

import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Coordinate;
import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Matrix;


/**
 * Represents a possible shape for a piece.
 * The shape is stored internally as a list of coordinates which can be accessed using the getOccupied method.
 * 
 * @author Kévin Ferrare
 * 
 */
public class PieceShape {
	private int firstOccupiedBlockIndex;
	private int occupiedBlockSize;

	private Coordinate occupied[];//it would be stupid to store the matrix as it, so store the coordinates of its occupied blocks
	private int color;
	private int sizeX;
	private int sizeY;

	public PieceShape(Matrix blocks, int color) {
		this.color = color;
		this.setBlocks(blocks);
	}

	/**
	 * Set this piece's shape from a block matrix where 0 = nothing and 1 occupied<br />
	 * Precalculates values depending on the shape
	 * 
	 * @param blocks
	 *            the blocks from which to generate the shape
	 */
	public void setBlocks(Matrix blocks) {
		this.sizeX = blocks.getSizeX();
		this.sizeY = blocks.getSizeY();
		//finds the first line's first block
		for (this.firstOccupiedBlockIndex = 0; this.firstOccupiedBlockIndex < blocks.getSizeX() && blocks.get(this.firstOccupiedBlockIndex, 0) == 0; this.firstOccupiedBlockIndex++)
			;
		//then finds the size of the consecutive occupied space on the first line
		for (this.occupiedBlockSize = this.firstOccupiedBlockIndex; this.occupiedBlockSize < blocks.getSizeX() && blocks.get(this.occupiedBlockSize, 0) != 0; this.occupiedBlockSize++)
			;
		this.occupiedBlockSize -= this.firstOccupiedBlockIndex;

		if (this.firstOccupiedBlockIndex >= blocks.getSizeX() || this.occupiedBlockSize < 0) {
			throw new RuntimeException("error : in PieceShape\n" + blocks.toString());
		}

		//fills a vector with all the block's positions
		List<Coordinate> occupied = new ArrayList<Coordinate>();
		for (int x = 0; x < blocks.getSizeX(); x++) {
			for (int y = 0; y < blocks.getSizeY(); y++) {
				if (blocks.get(x, y) != 0) {
					occupied.add(new Coordinate(x, y));
				}
			}
		}
		//copy the result into an array which are way faster than any of the Java data structures
		int i = 0;
		this.occupied = new Coordinate[occupied.size()];
		for (Coordinate coordinate : occupied) {
			this.occupied[i] = coordinate;
			i++;
		}
	}

	/**
	 * Gets the index of the first cell that is occupied on the first line of this shape
	 * 
	 * @return the index
	 */
	public int getFirstOccupiedBlockIndex() {
		return firstOccupiedBlockIndex;
	}

	/**
	 * Gets the size of the occupied blocks on the first free line (starting from firstOccupiedBlockIndex)
	 * 
	 * @return the size
	 */
	public int getOccupiedBlockSize() {
		return occupiedBlockSize;
	}

	/**
	 * Gets an array of all the blocks' coordinates
	 * 
	 * @return the coordinates of all the blocks occupied by this shape
	 */
	public Coordinate[] getOccupied() {
		return occupied;
	}

	public int getColor() {
		return color;
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		res.append("occupiedSpaceStartsFrom:");
		res.append(this.getFirstOccupiedBlockIndex());
		res.append('\n');
		res.append("occupiedBlockSize:");
		res.append(this.getOccupiedBlockSize());
		res.append('\n');
		return res.toString();
	}
}