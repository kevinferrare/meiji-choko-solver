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

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.kevinferrare.meijiChokoSolver.puzzle.AvailablePuzzleCreator;
import org.kevinferrare.meijiChokoSolver.puzzle.Puzzle;
import org.kevinferrare.meijiChokoSolver.solver.Solver;

/**
 * A panel that allows the user to choose from different puzzles and to display them (using a <code>SolverPanel</code>.
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
public class PuzzleChooserJPanel extends ColumnJPanel implements ActionListener {
	private JPanel solverPanelContainer = new JPanel();//the SolverPanel will be added and removed there
	private PuzzleIdChooserJComboBox puzzleChooserJComboBox;

	public PuzzleChooserJPanel(Puzzle puzzle) {
		puzzleChooserJComboBox = new PuzzleIdChooserJComboBox();
		if (puzzle != null) {
			puzzleChooserJComboBox.setSelectedItem(puzzle.getId());
		}
		puzzleChooserJComboBox.setActionCommand("choosePuzzleFromList");
		puzzleChooserJComboBox.addActionListener(this);
		this.add(new TitledJPanel("Choose a puzzle", puzzleChooserJComboBox));

		this.add(solverPanelContainer, new Insets(0,0,0,0));

		JButton infoButton = new JButton("About");
		infoButton.addActionListener(this);
		infoButton.setActionCommand("about");
		this.add(infoButton);

		solvePuzzle(puzzle);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if ("choosePuzzleFromList".equals(event.getActionCommand())) {
			solvePuzzle(AvailablePuzzleCreator.createPuzzle(puzzleChooserJComboBox.getSelectedPuzzleId()));
		} else if ("about".equals(event.getActionCommand())) {
			JOptionPane.showMessageDialog(null, "MEIJI CHOCOLATE PUZZLE solver【明治チョコレートパズル】\nCopyright 2008 and beyond, Kévin Ferrare.", "About", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void removeNotify() {
		super.removeNotify();
		//remove the eventually existing SolverPanel to allow the solver to stop
		solverPanelContainer.removeAll();
	}

	/**
	 * Stops the precedent started solver, creates a new solver and a new panel for the given puzzle and starts it
	 * 
	 * @param puzzle
	 *            the puzzle to solve
	 */
	protected void solvePuzzle(Puzzle puzzle) {
		if (solverPanelContainer.getComponentCount() != 0) {
			solverPanelContainer.removeAll();
		}
		solverPanelContainer.add(new SolverPanel(new Solver(puzzle)));
		//signal the eventual parent JFrame that the content may have changed and that it's time for a resize
		JFrame parent = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
		if (parent != null) {
			parent.pack();
		}
	}
}