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

import org.kevinferrare.meijiChokoSolver.solver.Board;

/**
 * Based on meiji milk choko puzzle dataset
 * 
 * @author Kévin Ferrare
 * 
 */
public class MeijiMilk8x8ChokoPuzzle extends MeijiMilkChokoPuzzle {

	public MeijiMilk8x8ChokoPuzzle() {
		this.setId(PuzzleId.MILK_8x8_WITH_RANDOM_BLOCKS);
		this.setPieces(createPieces());
		this.setBoard(new Board(8, 8));
		completeBoardWithRandom();//board is bigger than the total number of blocks, fill with random data
	}
}
