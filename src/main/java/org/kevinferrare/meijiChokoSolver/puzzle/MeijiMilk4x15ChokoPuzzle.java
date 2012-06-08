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
public class MeijiMilk4x15ChokoPuzzle extends MeijiMilkChokoPuzzle {

	public MeijiMilk4x15ChokoPuzzle() {
		this.setId(PuzzleId.MILK_4x15);
		this.setPieces(createPieces());
		this.setBoard(new Board(4, 15));
	}
}
