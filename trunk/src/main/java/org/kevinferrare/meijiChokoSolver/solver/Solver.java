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
package org.kevinferrare.meijiChokoSolver.solver;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kevinferrare.meijiChokoSolver.puzzle.Puzzle;
import org.kevinferrare.meijiChokoSolver.solver.dataStructures.linkedList.Link;
import org.kevinferrare.meijiChokoSolver.solver.dataStructures.linkedList.RelinkableLinkedList;

/**
 * Attempts to find all solutions for the given board.<br />
 * Can be run in it's own thread, to stop the thread call the <code>abort()</code> method.<br />
 * <br />
 * To solve a board, the program tries to put an available and fitting piece shape on the current line.<br />
 * If a fitting piece shape is found, the corresponding shape is removed from the list of available pieces and the solver
 * moves to the next available blank cell on the current line, or to the next line if the current
 * line is complete.<br />
 * When all the pieces have been placed, the solutionFound() method is called and the solver attempts continue the process until all solutions are found.
 * 
 * @author Kévin Ferrare
 * 
 */
public class Solver implements Runnable {
	private static Log log = LogFactory.getLog(Solver.class);
	private Board board;
	private RelinkableLinkedList<Piece> remainingPieces;
	private double recursiveCallsCount[];
	private int currentDepth = 0;
	private double placementAttempts;
	private double placementSuccessful;
	private int nbSolutions;
	private SolverEventListener solverEventListener;
	private Chronometer chronometer = new Chronometer();
	private boolean aborting = false;//when true the solver recursive function will do it's best to return as soon as possible

	/**
	 * Creates a solver for the given Puzzle
	 * 
	 * @param puzzle
	 *            the puzzle to solve
	 * @throws CloneNotSupportedException
	 */
	public Solver(Puzzle puzzle) {
		this(puzzle.getPieces(), puzzle.getBoard());
	}

	/**
	 * Creates a solver with the given size and the given pieces
	 * 
	 * @param pieces
	 *            the pieces to be tried
	 * @param board
	 *            the board on which to solve the puzzle
	 */
	public Solver(List<Piece> pieces, Board board) {
		this.recursiveCallsCount = new double[pieces.size() + 1];
		this.board = board;
		this.remainingPieces = new RelinkableLinkedList<Piece>();
		for (Piece piece : pieces) {
			this.remainingPieces.add(piece);
		}
	}

	/**
	 * Starts the solution search
	 */
	public void run() {
		log.debug("Starting to search for solutions");
		chronometer.start();
		fill(0, 0);
		chronometer.pause();
		if (!aborting) {
			log.debug("Found all solutions");
			//finished, tell our listener
			if (solverEventListener != null) {
				solverEventListener.allSolutionsFound(this);
			}
		} else {//finished but because abortion was requested
			log.debug("Aborted");
		}
	}

	/**
	 * Requests abortion for the currently running solution algorithm.
	 */
	public void abort() {
		log.debug("Requesting abortion");
		this.aborting = true;

	}

	/**
	 * Resets the solver for another run
	 */
	public void reset() {
		log.debug("Resetting values to default");
		for (int i = 0; i < recursiveCallsCount.length; i++) {
			recursiveCallsCount[i] = 0;
		}
		currentDepth = 0;
		placementAttempts = 0;
		placementSuccessful = 0;
		nbSolutions = 0;
		aborting = false;
	}

	/**
	 * Tries to put one piece on the given line from the given index,
	 * then calls itself to place the remaining pieces if any.<br />
	 * Calls solutionFound() when all the pieces have been placed.
	 * 
	 * @param lineNo
	 *            the line from which to place the piece
	 * @param linePos
	 *            the index from which to search for the piece
	 */
	protected void fill(int lineNo, int linePos) {
		recursiveCallsCount[currentDepth]++;//for statistics
		if (remainingPieces.isEmpty()) {
			//no more pieces to put, solution found !
			nbSolutions++;
			solutionFound();
			return;
		}
		//finds the first available position ; this is where we're going to try to put the pieces
		linePos = board.findLineFirstAvailableCell(lineNo, linePos);
		while (linePos == -1) {//line full, try next line
			linePos = 0;
			lineNo++;
			if (lineNo >= board.getSizeY()) {//top of the board reached
				return;
			}
			linePos = board.findLineFirstAvailableCell(lineNo, linePos);
		}
		Link<Piece> linkPiece = remainingPieces.getFirst();
		while (linkPiece != null) {
			remainingPieces.unlink(linkPiece);//remove the piece from the list
			Piece piece = linkPiece.getContent();
			List<PieceShape> shapes = piece.getPieceShapes();
			for (PieceShape shape : shapes) {//try all the available shapes for this piece
				placementAttempts++;
				//where should we put this shape ?
				int tryPosition = linePos - shape.getFirstOccupiedBlockIndex();
				if (tryPosition < 0) {
					continue;//the shape cannot be placed on the line without leaving an unfillable blank
				}
				if (board.canPlacePieceShape(shape, tryPosition, lineNo)) {
					//good the shape will fit
					placementSuccessful++;
					board.placePieceShape(shape, tryPosition, lineNo);
					changeInBoard();
					//continue the recursive iteration to place another block
					currentDepth++;
					fill(lineNo, tryPosition + shape.getOccupiedBlockSize());
					currentDepth--;
					//remove shape for next try
					board.removePieceShape(shape, tryPosition, lineNo);
					changeInBoard();
				}
			}
			//replace the piece in the list of available pieces
			remainingPieces.relink(linkPiece);
			linkPiece = linkPiece.getNext();
			if (aborting) {//abort after the pieces structure has been restored
				return;
			}
		}
		return;
	}

	protected void changeInBoard() {
		//tell our listener
		if (!aborting && solverEventListener != null && solverEventListener.shouldGetNotifyOfChangesInBoard(this)) {
			chronometer.pause();
			solverEventListener.changeInBoard(this);
			chronometer.resume();
		}
	}

	/**
	 * Function called when a solution is found
	 */
	protected void solutionFound() {
		//tell our listener
		if (!aborting && solverEventListener != null) {
			chronometer.pause();
			solverEventListener.solutionFound(this);
			chronometer.resume();
		}
	}

	/**
	 * Returns the object currently listening to this solver's events
	 * 
	 * @return the listener
	 */
	public SolverEventListener getSolverEventListener() {
		return solverEventListener;
	}

	/**
	 * Sets the listener that will listen to this solver's events
	 * 
	 * @param solverSignalReceiver
	 *            the listener
	 */
	public void setSolverEventListener(SolverEventListener solverSignalReceiver) {
		this.solverEventListener = solverSignalReceiver;
	}

	/**
	 * Gets the board being solved
	 * 
	 * @return the board being solved
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Gets an array containing the number of placements made for each level
	 * 
	 * @return
	 */
	public double[] getRecursiveCallsCount() {
		return recursiveCallsCount;
	}

	/**
	 * Gets the number of times the solver tried to place a shape
	 * 
	 * @return
	 */
	public double getPlacementAttempts() {
		return placementAttempts;
	}

	/**
	 * Gets the number of times the solver actually wrote a piece to the board
	 * 
	 * @return
	 */
	public double getPlacementSuccessful() {
		return placementSuccessful;
	}

	/**
	 * Gets the number of solution found
	 * 
	 * @return
	 */
	public int getNbSolutions() {
		return nbSolutions;
	}

	/**
	 * Gets the number of milliseconds spent computing the solution
	 * 
	 * @return
	 */
	public long getElapsedTimeMiliSeconds() {
		return chronometer.elapsedTimeMiliSeconds();
	}

}
