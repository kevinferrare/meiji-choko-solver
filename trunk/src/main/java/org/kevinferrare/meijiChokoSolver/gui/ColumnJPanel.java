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

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Helper to create vertical panels that resize automatically respecting component sizes
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
public class ColumnJPanel extends JPanel {
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();

	public ColumnJPanel() {
		this.setLayout(new GridBagLayout());
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.anchor = GridBagConstraints.CENTER;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		gridBagConstraints.insets = new Insets(3, 3, 3, 3);

	}

	@Override
	public Component add(Component comp) {
		super.add(comp, gridBagConstraints);
		return comp;
	}

	/**
	 * Adds a component with the specified insets
	 * @param comp
	 * @param insets
	 * @return
	 */
	public Component add(Component comp, Insets insets) {
		Insets oldInsets = gridBagConstraints.insets;
		gridBagConstraints.insets = insets;
		super.add(comp, gridBagConstraints);
		gridBagConstraints.insets = oldInsets;
		return comp;
	}
}
