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

/**
 * Creates a solver from hard coded data set
 * 
 * @author Kévin Ferrare
 * 
 */
public class AvailablePuzzleCreator {
	/**
	 * Creates a puzzle from the supplied <code>PuzzleId</code>
	 * 
	 * @param puzzleId
	 *            the Id of the puzzle to create
	 * @return the solver initialized with the given puzzle
	 */
	public static Puzzle createPuzzle(PuzzleId puzzleId) {
		switch (puzzleId) {
			case WHITE:
				return new MeijiWhiteChokoPuzzle();
			case MILK:
				return new MeijiMilkChokoPuzzle();
			case MILK_3x20:
				return new MeijiMilk3x20ChokoPuzzle();
			case MILK_4x15:
				return new MeijiMilk4x15ChokoPuzzle();
			case MILK_5x12:
				return new MeijiMilk5x12ChokoPuzzle();
			case MILK_8x8_WITH_RANDOM_BLOCKS:
				return new MeijiMilk8x8ChokoPuzzle();
			case BLACK:
				return new MeijiBlackChokoPuzzle();
			case BLACK_8x9_WITH_RANDOM_BLOCKS:
				return new MeijiBlack8x9ChokoPuzzle();
		}
		return null;
	}
}
