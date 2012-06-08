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
package org.kevinferrare.meijiChokoSolver.puzzle;

import java.util.List;

import org.kevinferrare.meijiChokoSolver.solver.Board;
import org.kevinferrare.meijiChokoSolver.solver.Piece;

/**
 * Holds the information describing a puzzle
 * 
 * @author Kévin Ferrare
 * 
 */
public class Puzzle {
	private PuzzleId id;
	private List<Piece> pieces;
	private Board board;

	public Puzzle() {
	}

	public Puzzle(List<Piece> pieces, Board board) {
		super();
		this.pieces = pieces;
		this.board = board;
	}

	/**
	 * Complete the board with randomly placed blocks
	 */
	protected void completeBoardWithRandom() {
		int numberOfBlocksWhenFull = 0;
		for (Piece piece : this.getPieces()) {
			numberOfBlocksWhenFull += piece.getPieceShapes().get(0).getOccupied().length;
		}
		int numberOfRandomBlocks = this.getBoard().getSizeX() * this.getBoard().getSizeY() - numberOfBlocksWhenFull;
		for (int i = 0; i < numberOfRandomBlocks; i++) {
			int x, y;
			do {
				x = (int) (Math.random() * this.getBoard().getSizeX());
				y = (int) (Math.random() * this.getBoard().getSizeY());
			} while (this.getBoard().get(x, y) == -1);//already taken, try next
			this.getBoard().set(x, y, -1);
		}
	}

	/**
	 * Returns the associated <code>PuzzleId</code>, null if unknown
	 * 
	 * @return the associated <code>PuzzleId</code>, null if unknown
	 */
	public PuzzleId getId() {
		return id;
	}

	/**
	 * Associate a <code>PuzzleId</code> to this instance
	 * 
	 * @param id
	 *            the <code>PuzzleId</code> of this instance
	 */
	public void setId(PuzzleId id) {
		this.id = id;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	public void setPieces(List<Piece> pieces) {
		this.pieces = pieces;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
