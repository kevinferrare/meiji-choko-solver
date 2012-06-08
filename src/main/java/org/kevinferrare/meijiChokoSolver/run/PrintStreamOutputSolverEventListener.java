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

import java.io.PrintStream;
import java.text.NumberFormat;

import org.kevinferrare.meijiChokoSolver.solver.Solver;
import org.kevinferrare.meijiChokoSolver.solver.SolverEventListener;

/**
 * Gets notified of the solver's events and prints the results to a given <code>PrintStream</code>
 * 
 * @author Kévin Ferrare
 * 
 */
public class PrintStreamOutputSolverEventListener implements SolverEventListener {
	private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
	static {
		numberFormat.setGroupingUsed(false);
		numberFormat.setMaximumFractionDigits(3);
		numberFormat.setMinimumFractionDigits(0);
	}
	private PrintStream printStream;

	public PrintStreamOutputSolverEventListener(PrintStream printStream) {
		this.printStream=printStream;
	}

	@Override
	public boolean shouldGetNotifyOfChangesInBoard(Solver solver) {
		return false;
	}

	@Override
	public void changeInBoard(Solver solver) {
	}

	@Override
	public void solutionFound(Solver solver) {
		double[] recursiveCallsCount=solver.getRecursiveCallsCount();
		String recursiveCallsStr = "[";
		for (int i=0;i<recursiveCallsCount.length;i++) {
			recursiveCallsStr += numberFormat.format(recursiveCallsCount[i]);
			if(i+1<recursiveCallsCount.length) {
				recursiveCallsStr += ", ";
			}
		}
		recursiveCallsStr += "]";
		printStream.println("Solution no " + numberFormat.format(solver.getNbSolutions()) + " found");
		printStream.println("Placements attempted : " + numberFormat.format(solver.getPlacementAttempts()));
		printStream.println("Placements successful : " + numberFormat.format(solver.getPlacementSuccessful()));
		printStream.println("Time spent computing : " + numberFormat.format(solver.getElapsedTimeMiliSeconds() / 1000d) + "s");
		printStream.println("Recursive calls : "+recursiveCallsStr);
		printStream.println(solver.getBoard());
	}

	@Override
	public void allSolutionsFound(Solver solver) {
		printStream.println("All solutions found !!!");
		printStream.println("Time spent computing : "+(solver.getElapsedTimeMiliSeconds()/1000d)+"s");
	}
}
