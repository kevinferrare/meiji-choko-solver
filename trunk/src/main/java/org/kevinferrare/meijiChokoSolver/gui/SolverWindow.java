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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.kevinferrare.meijiChokoSolver.puzzle.Puzzle;

/**
 * Solver window which contains the <code>PuzzleChooserJPanel</code>
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
public class SolverWindow extends JFrame implements WindowListener {
	public SolverWindow(Puzzle puzzle) {
		this.add(new PuzzleChooserJPanel(puzzle));
		try {
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (java.security.AccessControlException e) {//applets don't support that
		}
		this.setTitle("Meiji chocolate puzzle solver");
		this.pack();//initial window size
		this.setVisible(true);
		this.setResizable(false);
		this.addWindowListener(this);
	}

	//window listener implementation below to stop the solver before closing 
	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		e.getWindow().removeAll();//will remove the components, triggering the solver to stop (a bit dirty)
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
