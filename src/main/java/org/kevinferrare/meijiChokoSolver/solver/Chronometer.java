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

import java.util.Date;

/**
 * Tool to measure elapsed time.<br />
 * Start it using <code>start()</code><br />
 * You can pause it or resume it using the appropriate methods.<br />
 * 
 * @author Kévin Ferrare
 * 
 */
public class Chronometer {
	private Date startDate;
	private Long elapsedMiliseconds;

	public Chronometer() {
		start();
	}

	public void start() {
		startDate = new Date();
		elapsedMiliseconds = null;
	}

	public void pause() {
		elapsedMiliseconds = elapsedTimeMiliSeconds();
	}

	public void resume() {
		startDate = new Date();
		startDate.setTime(startDate.getTime() - elapsedMiliseconds);
		elapsedMiliseconds = null;
	}

	public double elapsedTimeSeconds() {
		return ((double) elapsedTimeMiliSeconds() / 1000);
	}

	public long elapsedTimeMiliSeconds() {
		if (elapsedMiliseconds == null) {//not paused
			return computeElapsedTimeMiliSeconds();
		} else {
			return elapsedMiliseconds;
		}
	}

	protected long computeElapsedTimeMiliSeconds() {
		Date now = new Date();
		return (now.getTime() - startDate.getTime());
	}

	public String toString() {
		return "Elapsed time : " + elapsedTimeSeconds() + " sec";
	}
}
