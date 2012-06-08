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

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.kevinferrare.meijiChokoSolver.puzzle.AvailablePuzzleCreator;
import org.kevinferrare.meijiChokoSolver.puzzle.PuzzleId;
import org.kevinferrare.meijiChokoSolver.puzzle.Puzzle;

/**
 * Launches a solver from command line arguments (either in a gui or batch mode)
 * 
 * @author Kévin Ferrare
 * 
 */
public class SolverApplication {
	private static Log log = LogFactory.getLog(SolverApplication.class);

	public static void main(String[] args) throws Exception {
		Options options = new Options();
		options.addOption(null, "help", false, "Print help");
		options.addOption(null, "puzzle", true, "Name of the puzzle to start with, choose one from (" + PuzzleId. getValidNames() + "), 'milk' if not specified");
		options.addOption(null, "output_type", true, "the way the solutions are output, the default is 'gui' but there is also a command line mode ('batch')");
		CommandLineParser parser = new PosixParser();
		CommandLine commandLine = parser.parse(options, args);
		if (commandLine.hasOption("help")) {
			HelpFormatter helpFormatter = new HelpFormatter();
			helpFormatter.printHelp("Available options :", options);
			return;
		}
		Puzzle puzzle = createPuzzleFromArguments(commandLine);
		if (puzzle == null) {
			return;
		}
		createStarterFromArguments(commandLine).start(puzzle);
	}

	/**
	 * Creates a puzzle from the given command line arguments
	 * 
	 * @param args
	 *            the command line arguments
	 * @return a puzzle object or null if couldn't create
	 */
	public static Puzzle createPuzzleFromArguments(CommandLine commandLine) {
		PuzzleId puzzleId = null;
		if (commandLine.hasOption("puzzle")) {
			String puzzleName = commandLine.getOptionValue("puzzle");
			puzzleId = PuzzleId.getFromName(puzzleName);
			if (puzzleId == null) {//invalid name specified
				log.error("Puzzle name '" + puzzleName + "' is not valid, please choose between " + PuzzleId.getValidNames());
				return null;
			}
		} else {
			puzzleId = PuzzleId.MILK;//default
			log.info("No puzzle name supplied, using default '" + puzzleId.name() + "'");
		}
		return AvailablePuzzleCreator.createPuzzle(puzzleId);
	}

	/**
	 * Creates a starter from the given command line arguments
	 * 
	 * @param args
	 *            the command line arguments
	 * @return a starter
	 */
	public static SolverStarter createStarterFromArguments(CommandLine commandLine) {
		String starterType = "gui";
		if (commandLine.hasOption("output_type")) {
			starterType = commandLine.getOptionValue("output_type");
		}
		if ("gui".equals(starterType)) {
			return new GuiSolverStarter();
		}
		return new BatchSolverStarter();
	}
}
