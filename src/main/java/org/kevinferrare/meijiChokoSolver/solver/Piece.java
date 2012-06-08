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

import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Matrix;


/**
 * A Piece, can have different orientations (shapes)
 * for example a bar "I" will have two orientations : "I" and "-"
 * @author Kévin Ferrare
 * 
 */
public class Piece {
	private List<PieceShape> pieceStates = new ArrayList<PieceShape>();

	/**
	 * Constructs the piece with the given initial shape and the given color
	 * @param initialShape
	 * @param color
	 */
	public Piece(Matrix initialShape, int color) {
		//tries to turn and flip the matrix in all the possible configurations to get all the different possibilities for this piece
		List<Matrix> shapes = Matrix.getAllArrangements(initialShape);
		for (Matrix shape : shapes) {
			PieceShape state = new PieceShape(shape, color);
			pieceStates.add(state);
		}
	}

	/**
	 * Returns a list of all the possible shapes for this piece
	 * @return a list of all the possible shapes for this piece
	 */
	public List<PieceShape> getPieceShapes() {
		return pieceStates;
	}
}
