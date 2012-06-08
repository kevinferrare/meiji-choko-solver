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
package org.kevinferrare.meijiChokoSolver.run;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kevinferrare.meijiChokoSolver.puzzle.Puzzle;
import org.kevinferrare.meijiChokoSolver.solver.Solver;

/**
 * Runs the solver in batch mode (will find all the solutions)
 * 
 * @author Kévin Ferrare
 * 
 */
public class BatchSolverStarter implements SolverStarter {
	private static Log log = LogFactory.getLog(BatchSolverStarter.class);

	@Override
	public void start(Puzzle puzzle) {
		log.debug("Starting in batch mode");
		Solver solver = new Solver(puzzle);
		solver.setSolverEventListener(new PrintStreamOutputSolverEventListener(System.out));
		solver.run();
	}
}