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

/**
 * A combo box with a list of operating mode the user can choose
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
public class SolverGUIOperatingModeJComboBox extends JComboBox {
	enum SolverGUIOperatingMode {
		CONTINUOUS("Do not stop"), DELAY_AFTER_EACH_SOLUTION("Delay after each solution"), PAUSE_AFTER_EACH_SOLUTION("Pause after each solution"), DELAY_AFTER_EACH_PLACEMENT("Delay after each placement"), PAUSE_AFTER_EACH_PLACEMENT("Pause after each placement");
		private String title;

		private SolverGUIOperatingMode(String title) {
			this.title = title;
		}

		public String toString() {
			return title;
		}
	}

	public SolverGUIOperatingModeJComboBox(SolverGUIOperatingMode defaultSolverGUIOperatingMode) {
		super(SolverGUIOperatingMode.values());
		this.setSelectedItem(defaultSolverGUIOperatingMode);
	}

	public SolverGUIOperatingMode getSelectedSolverGUIOperatingMode() {
		return (SolverGUIOperatingMode) this.getSelectedItem();
	}
}