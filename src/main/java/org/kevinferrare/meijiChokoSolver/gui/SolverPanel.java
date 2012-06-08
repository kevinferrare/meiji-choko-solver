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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kevinferrare.meijiChokoSolver.gui.SolverGUIOperatingModeJComboBox.SolverGUIOperatingMode;
import org.kevinferrare.meijiChokoSolver.solver.Solver;
import org.kevinferrare.meijiChokoSolver.solver.SolverEventListener;

/**
 * Starts and stops the solver when appropriate and displays it's state depending on
 * how the user chooses it with the <code>SolverGUIOperatingModeJComboBox</code>
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
class SolverPanel extends RowJPanel implements ActionListener, SolverEventListener {
	private static Log log = LogFactory.getLog(SolverPanel.class);
	private Solver solver;
	private MatrixCanvas canvas;
	private JButton nextButton = new JButton();
	private SolverInfoJPanel solverInfoJPanel;
	private SolverGUIOperatingModeJComboBox solverGUIOperatingModeJComboBox = new SolverGUIOperatingModeJComboBox(SolverGUIOperatingMode.PAUSE_AFTER_EACH_SOLUTION);
	private boolean pauseBetweenSolutions;
	private int timeBetweenSolutions;
	private boolean pauseBetweenPlacements;
	private int timeBetweenPlacements;
	private Timer refreshSolverInfosTimer = new Timer(100, this);//will refresh the computation state panel 10 times a second
	private Future<?> solverFuture;//to wait for the solver thread completion

	public SolverPanel(Solver solver) {
		this.solver = solver;
		//register the component to the solver so that the interface gets noticed when something happens
		this.solver.setSolverEventListener(this);

		//layout
		canvas = new MatrixCanvas();
		canvas.setMatrix(solver.getBoard());
		this.add(new TitledJPanel("Board", canvas));
		this.add(createSideBar());

		solverGUIOperatingModeChanged();
		solverInfoJPanel.update();
	}

	/**
	 * Creates the components on the right
	 * @return
	 */
	private JPanel createSideBar() {
		ColumnJPanel panel = new ColumnJPanel();

		panel.add(new TitledJPanel("Current solver controls", createControls()), new Insets(0,3,0,0));

		solverInfoJPanel = new SolverInfoJPanel(solver);
		panel.add(new TitledJPanel("Current solver information", solverInfoJPanel), new Insets(0,3,0,0));

		return panel;
	}

	/**
	 * Creates the controls inside the right bar
	 * @return
	 */
	private JPanel createControls() {
		ColumnJPanel panel = new ColumnJPanel();

		solverGUIOperatingModeJComboBox.addActionListener(this);
		solverGUIOperatingModeJComboBox.setActionCommand("solverGUIOperatingModeChanged");
		panel.add(solverGUIOperatingModeJComboBox);

		nextButton.addActionListener(this);
		nextButton.setActionCommand("next");
		panel.add(nextButton);

		JButton restartButton = new JButton("Restart");
		restartButton.setActionCommand("restartSolver");
		restartButton.addActionListener(this);
		panel.add(restartButton);

		JButton changePaletteButton = new JButton("Change colors");
		changePaletteButton.setActionCommand("changeColors");
		changePaletteButton.addActionListener(this);
		panel.add(changePaletteButton);

		return panel;
	}

	/**
	 * Called when the component is shown
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		startSolver();
	}

	/**
	 * Called when the component is removed
	 */
	@Override
	public void removeNotify() {
		super.removeNotify();
		stopSolver();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if ("solverGUIOperatingModeChanged".equals(event.getActionCommand())) {
			solverGUIOperatingModeChanged();
		} else if ("next".equals(event.getActionCommand())) {
			continueSolver();
		} else if ("restartSolver".equals(event.getActionCommand())) {
			stopSolver();
			startSolver();
		} else if ("changeColors".equals(event.getActionCommand())) {
			this.canvas.setRandomPalette();
		} else {
			solverInfoJPanel.update();//refresh by timer
		}
	}

	private void startSolver() {
		log.debug("Requesting start");
		solverFuture = Executors.newSingleThreadExecutor().submit(solver);
		refreshSolverInfosTimer.start();
		log.debug("Successfully started");
	}

	private void stopSolver() {
		log.debug("Requesting stop");
		refreshSolverInfosTimer.stop();
		solver.abort();
		continueSolver();//unlock the solver if it was locked waiting for user input
		try {
			solverFuture.get();//wait for the solver to end (should not take long since it's in aborted state)
		} catch (Exception e) {
		}
		solver.reset();//resets the solver for the next run
		log.debug("Successfully stopped");
	}

	private void solverGUIOperatingModeChanged() {
		SolverGUIOperatingMode mode = solverGUIOperatingModeJComboBox.getSelectedSolverGUIOperatingMode();
		//reset
		pauseBetweenSolutions = false;
		timeBetweenSolutions = 0;
		pauseBetweenPlacements = false;
		timeBetweenPlacements = 0;
		switch (mode) {
			case CONTINUOUS:
				break;
			case DELAY_AFTER_EACH_SOLUTION:
				timeBetweenSolutions = 500;
				break;
			case PAUSE_AFTER_EACH_SOLUTION:
				pauseBetweenSolutions = true;
				break;
			case DELAY_AFTER_EACH_PLACEMENT:
				timeBetweenPlacements = 20;
				break;
			case PAUSE_AFTER_EACH_PLACEMENT:
				pauseBetweenPlacements = true;
				break;
		}
		if (!pauseBetweenSolutions && !pauseBetweenPlacements) {
			nextButton.setVisible(false);
			continueSolver();
		}
	}

	/**
	 * Pause the current thread for the given delay
	 * 
	 * @param millis
	 *            the delay in milliseconds
	 */
	protected void pauseThread(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Pauses the current thread until the user press the continue button
	 */
	protected synchronized void pauseThreadUntilUserAction() {
		try {
			//enable the continue button
			invokeLater(new Runnable() {
				public void run() {
					updateGuiForWaitForUserAction();
				}
			});
			log.debug("Waiting for user click");
			wait();
			log.debug("The user clicked");
			//disable the continue button
			invokeLater(new Runnable() {
				public void run() {
					updateGuiForComputing();
				}
			});
		} catch (Exception e) {
		}
	}

	/**
	 * Allow the threads waiting for user input to continue
	 */
	protected synchronized void continueSolver() {
		notifyAll();
	}

	@Override
	public boolean shouldGetNotifyOfChangesInBoard(Solver solver) {
		return timeBetweenPlacements > 0 || pauseBetweenPlacements;
	}

	@Override
	public void changeInBoard(Solver solver) {
		canvas.repaint();
		solverInfoJPanel.update();
		if (pauseBetweenPlacements) {
			pauseThreadUntilUserAction();
		} else if (timeBetweenPlacements > 0) {
			pauseThread(timeBetweenPlacements);
		}
	}

	@Override
	public void solutionFound(Solver solver) {
		canvas.repaint();
		solverInfoJPanel.update();
		if (pauseBetweenSolutions) {
			pauseThreadUntilUserAction();
		} else if (timeBetweenSolutions > 0) {
			pauseThread(timeBetweenSolutions);
		}
	}

	@Override
	public void allSolutionsFound(Solver solver) {
		//let AWT finish processing the previous switchGuiToComputingMode before re-enabling the button
		refreshSolverInfosTimer.stop();
		invokeLater(new Runnable() {
			public void run() {
				updateGuiForAllSolutionsFound();
			}
		});
	}

	protected void updateGuiForWaitForUserAction() {
		nextButton.requestFocusInWindow();
		nextButton.setVisible(true);
		nextButton.setEnabled(true);
		nextButton.setText("Next");
	}

	protected void updateGuiForComputing() {
		nextButton.setEnabled(false);
		nextButton.setText("Computing...");
	}

	protected void updateGuiForAllSolutionsFound() {
		nextButton.setVisible(true);
		nextButton.setEnabled(false);
		nextButton.setText("No more solutions !");
	}

	private void invokeLater(Runnable runnable) {
		try {
			SwingUtilities.invokeLater(runnable);
		} catch (Exception e) {
		}
	}
}
