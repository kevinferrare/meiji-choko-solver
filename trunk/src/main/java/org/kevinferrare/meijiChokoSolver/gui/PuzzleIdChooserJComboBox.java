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

import javax.swing.JComboBox;

import org.kevinferrare.meijiChokoSolver.puzzle.PuzzleId;

/**
 * A combo box with a list of puzzles the user can choose
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
public class PuzzleIdChooserJComboBox extends JComboBox {
	public PuzzleIdChooserJComboBox() {
		super(PuzzleId.values());
	}

	public PuzzleId getSelectedPuzzleId() {
		return (PuzzleId) this.getSelectedItem();
	}
}