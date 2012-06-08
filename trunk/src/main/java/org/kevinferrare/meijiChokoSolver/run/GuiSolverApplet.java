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

import java.applet.Applet;

import org.kevinferrare.meijiChokoSolver.puzzle.AvailablePuzzleCreator;
import org.kevinferrare.meijiChokoSolver.puzzle.Puzzle;
import org.kevinferrare.meijiChokoSolver.puzzle.PuzzleId;


/**
 * Runs the solver as a browser applet
 * @author Kévin Ferrare
 *
 */
@SuppressWarnings("serial")
public class GuiSolverApplet extends Applet {
	public void init() {
		Puzzle puzzle = AvailablePuzzleCreator.createPuzzle(PuzzleId.MILK);		
		(new GuiSolverStarter()).start(puzzle);
	}
}
