package org.kevinferrare.meijiChokoSolver.gui;

import java.awt.Component;

import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class TitledJPanel extends ColumnJPanel {
	public TitledJPanel(String title) {
		this(title, null);
	}

	public TitledJPanel(String title, Component content) {
		this.setBorder(new TitledBorder(title));
		if (content != null) {
			this.add(content);
		}
	}
}
