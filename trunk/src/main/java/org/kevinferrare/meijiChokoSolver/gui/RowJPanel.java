package org.kevinferrare.meijiChokoSolver.gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RowJPanel extends JPanel {
	private GridBagConstraints gridBagConstraints = new GridBagConstraints();
	public RowJPanel(){
		this.setLayout(new GridBagLayout());
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy=0;
		gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		gridBagConstraints.weightx = 1;
	}

	@Override
	public Component add(Component comp){
		super.add(comp, gridBagConstraints);
		return comp;
	}
}
