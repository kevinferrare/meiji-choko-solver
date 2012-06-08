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

/**
 * Objects that are capable of receiving signals from the solver
 * 
 * @author Kévin Ferrare
 * 
 */
public interface SolverEventListener {
	/**
	 * Tells whether the solver should notify the listener each time the board is changed
	 * @return true if so false else
	 */
	public boolean shouldGetNotifyOfChangesInBoard(Solver solver);

	/**
	 * Called when the board content is changed
	 */
	public void changeInBoard(Solver solver);

	/**
	 * Called when a solution is found
	 */
	public void solutionFound(Solver solver);

	/**
	 * Called when all the solutions are found
	 */
	public void allSolutionsFound(Solver solver);
}
