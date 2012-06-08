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

import java.util.ArrayList;
import java.util.List;

import org.kevinferrare.meijiChokoSolver.solver.Board;
import org.kevinferrare.meijiChokoSolver.solver.Piece;
import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Matrix;


/**
 * Meiji black choko puzzle dataset<br />
 * Challenge level : non suggar !
 * 明治ブラックチョコレートパズル
 * 
 * @author Kévin Ferrare
 *
 */
public class MeijiBlackChokoPuzzle extends Puzzle {

	public MeijiBlackChokoPuzzle() {
		this.setId(PuzzleId.BLACK);
		this.setPieces(createPieces());
		this.setBoard(new Board(6, 11));
	}

	protected static List<Piece> createPieces(){
		List<Piece> pieces=new ArrayList<Piece>();
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{1,1,1},
					{1,1,0},
					{1,0,0}
				}),
				1
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{1,1,1,1,1},
					{0,1,0,0,0}
				}),
				2
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{0,1,1,1},
					{1,1,1,0}
				}),
				3
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{1,1,0},
					{0,1,1},
					{1,1,0}
				}),
				4
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{0,1,1,1},
					{1,1,0,0},
					{0,1,0,0}
				}),
				5
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{0,1,1,1,1},
					{1,1,0,0,0}
				}),
				6
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{1,1,1},
					{1,0,0},
					{1,1,0}
				}),
				7
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{1,1,1,1,1},
					{1,0,0,0,0}
				}),
				8
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{0,1,1,1},
					{1,1,0,1}
				}),
				9
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{0,1,0,0},
					{1,1,1,1},
					{0,1,0,0}
				}),
				10
			)
		);
		pieces.add(
			new Piece(
				Matrix.createFromArray(new int[][]{
					{1,1,1,1},
					{1,0,1,0}
				}),
				11
			)
		);
		return pieces;
	}
}
