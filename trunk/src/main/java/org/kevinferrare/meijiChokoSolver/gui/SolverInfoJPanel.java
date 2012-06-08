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
package org.kevinferrare.meijiChokoSolver.gui;

import java.awt.Dimension;
import java.text.NumberFormat;

import javax.swing.JLabel;

import org.kevinferrare.meijiChokoSolver.solver.Solver;

/**
 * Displays information about the current state of the solver.<br />
 * Call update() to refresh it.
 * 
 * @author Kévin Ferrare
 *
 */
@SuppressWarnings("serial")
public class SolverInfoJPanel extends ColumnJPanel {
	private static NumberFormat numberFormat = NumberFormat.getNumberInstance();
	static {
		numberFormat.setGroupingUsed(false);
		numberFormat.setMaximumFractionDigits(3);
		numberFormat.setMinimumFractionDigits(0);
	}

	private Solver solver;
	private JLabel solutionFoundJLabel = new JLabel();
	private JLabel placementsAttemptedJLabel = new JLabel();
	private JLabel placementsSuccessfulJLabel = new JLabel();
	private JLabel elapsedComputationTimeJLabel = new JLabel();

	public SolverInfoJPanel(Solver solver) {
		this.solver = solver;
		this.add(solutionFoundJLabel);
		this.add(placementsAttemptedJLabel);
		this.add(placementsSuccessfulJLabel);
		this.add(elapsedComputationTimeJLabel);
		update();
		Dimension size=this.getPreferredSize();
		size.width+=35;//leave space for the numbers to grow
		this.setPreferredSize(size);
	}

	/**
	 * Updates the displayed information
	 */
	public void update() {
		solutionFoundJLabel.setText("Solutions found : " + numberFormat.format(solver.getNbSolutions()));
		placementsAttemptedJLabel.setText("Placements attempted : " + numberFormat.format(solver.getPlacementAttempts()));
		placementsSuccessfulJLabel.setText("Placements successful : " + numberFormat.format(solver.getPlacementSuccessful()));
		elapsedComputationTimeJLabel.setText("Time spent computing : " + numberFormat.format(solver.getElapsedTimeMiliSeconds() / 1000d) + "s");
	}
}