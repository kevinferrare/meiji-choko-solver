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
package org.kevinferrare.meijiChokoSolver.puzzle;

/**
 * List of puzzles ids available for creation (hard coded into the program)
 * 
 * @author Kévin Ferrare
 * 
 */
public enum PuzzleId {
	WHITE, MILK, MILK_5x12, MILK_4x15, MILK_3x20, MILK_8x8_WITH_RANDOM_BLOCKS, BLACK, BLACK_8x9_WITH_RANDOM_BLOCKS;

	/**
	 * Returns a <code>PuzzleId</code> from a name, or null if cannot match
	 * 
	 * @param name
	 *            the name of the puzzle
	 * @return the corresponding PuzzleId or null if not found
	 */
	public static PuzzleId getFromName(String name) {
		try {
			return PuzzleId.valueOf(name);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	/**
	 * Returns a String describing the list of valid PuzzleIds names separated by a comma
	 * @return a String describing the list of valid PuzzleIds names separated by a comma
	 */
	public static String getValidNames() {
		String names = "";
		PuzzleId[] values = PuzzleId.values();
		for (int i = 0; i < values.length; i++) {
			names += values[i].toString();
			if (i + 1 < values.length) {
				names += ", ";
			}
		}
		return names;
	}
}
