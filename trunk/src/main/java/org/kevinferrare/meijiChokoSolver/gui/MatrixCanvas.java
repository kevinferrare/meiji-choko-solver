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

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import org.kevinferrare.meijiChokoSolver.solver.dataStructures.Matrix;

/**
 * Draws a graphical representation of a numeric matrix.<br />
 * Zones with the same number will be shown contiguous with borders between cells where numbers are different
 * 
 * @author Kévin Ferrare
 * 
 */
@SuppressWarnings("serial")
public class MatrixCanvas extends Canvas {
	private Graphics2D imageGraphics;
	private BufferedImage image;
	private static Color defaultPalette[] = {
		new Color(0x00, 0x33, 0x00),
		new Color(0x00, 0x66, 0x99),
		new Color(0x00, 0xFF, 0xFF),
		new Color(0x33, 0x33, 0x99),
		new Color(0x33, 0x99, 0x00),
		new Color(0x33, 0x99, 0x99),
		new Color(0x66, 0x33, 0x00),
		new Color(0xCC, 0x00, 0x00),
		new Color(0xCC, 0x66, 0xFF),
		new Color(0xCC, 0x99, 0x99),
		new Color(0xFF, 0xCC, 0x00),
		new Color(0xFF, 0x99, 0x99),
		new Color(0xFF, 0x99, 0x00)
	};
	private Color palette[] = defaultPalette;
	private int cellSize;//size of a cell
	private int borderSize;//border width

	private Matrix matrix;//the matrix to represent

	public MatrixCanvas() {
		this(20, 2);
	}

	/**
	 * The given sizes are subject to resizing
	 * 
	 * @param cellSize
	 *            the initial size of a cell
	 * @param borderSize
	 *            the initial width of the border
	 */
	public MatrixCanvas(int cellSize, int borderSize) {
		this.cellSize = cellSize;
		this.borderSize = borderSize / 2;
	}

	@Override
	public void paint(Graphics graphics) {
		if (image != null) {
			graphics.drawImage(image, 0, 0, this);
		}
	}

	@Override
	public void repaint() {
		updateBuffer();
		super.repaint();
	}

	/**
	 * Draws the matrix contents to the internal graphic buffer
	 */
	public void updateBuffer() {
		int areaSizeX = matrix.getSizeX() * cellSize;
		int areaSizeY = matrix.getSizeY() * cellSize + borderSize;
		//create the buffer if it doesn't exists
		if (image == null) {
			image = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(areaSizeX, areaSizeY);
			if (image == null) {
				return;//not yet in a state to draw anything
			}
			imageGraphics = image.createGraphics();
		}
		imageGraphics.setColor(Color.BLACK);

		imageGraphics.fillRect(0, 0, areaSizeX, areaSizeY);
		int x, y;
		for (x = 0; x < matrix.getSizeX(); x++) {
			for (y = 0; y < matrix.getSizeY(); y++) {
				int shape = matrix.get(x, y);
				if (shape == 0) {
					continue;
				}
				imageGraphics.setColor(getShapeColor(shape));
				//draw cells in a way that leaves the background color visible if there is a need for a border
				int startX = x * cellSize + (matrix.isSameLeft(x, y) ? 0 : borderSize);
				int startY = (matrix.getSizeY() - 1 - y) * cellSize + (matrix.isSameBottom(x, y) ? 0 : borderSize);
				int sizeX = cellSize - (matrix.isSameLeft(x, y) ? 0 : borderSize) - (matrix.isSameRight(x, y) ? 0 : borderSize);
				int sizeY = cellSize - (matrix.isSameTop(x, y) ? 0 : borderSize) - (matrix.isSameBottom(x, y) ? 0 : borderSize);
				imageGraphics.fillRect(startX, startY, sizeX, sizeY);
				if (shape == -1) {//unmovable block
					imageGraphics.setColor(Color.BLACK);
					imageGraphics.drawLine(startX, startY, startX + sizeX, startY + sizeY);
					imageGraphics.drawLine(startX + +sizeX, startY, startX, startY + sizeY);
				}
			}
		}
	}

	/**
	 * Sets the given matrix as the content to draw
	 * 
	 * @param matrix
	 *            the matrix to be drawn
	 */
	public void setMatrix(Matrix matrix) {
		this.matrix = matrix;
		//adjust the size of the blocks so that the whole board fits in half of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height /= 2;
		screenSize.width /= 2;
		makeFitInside(screenSize);
		updateSize();
	}

	/**
	 * Adjusts the cellSize and borderSize parameters so that the canvas fits inside the specified maxSize
	 * 
	 * @param maxSize
	 *            the size the canvas should not exceed
	 */
	protected void makeFitInside(Dimension maxSize) {
		Dimension requiredSize = getRequiredSize();
		double resizeRatio;
		if (requiredSize.getHeight() > requiredSize.getWidth()) {//height is bigger
			resizeRatio = maxSize.getHeight() / requiredSize.getHeight();
		} else {//width is bigger
			resizeRatio = maxSize.getWidth() / requiredSize.getWidth();
		}
		cellSize = (int) (cellSize * resizeRatio);
		if (cellSize < 1) {
			cellSize = 1;
		}
		borderSize = (int) (borderSize * resizeRatio);
		if (borderSize < 1) {
			borderSize = 1;
		}
	}

	/**
	 * Sets the palette used to render the different numbers represented in the matrix.<br />
	 * If the palette has less colors that the different numbers in the matrix, some cells with different numbers will have the same color.
	 * 
	 * @param palette
	 */
	public void setPalette(Color[] palette) {
		this.palette = palette;
		updateBuffer();
		repaint();
	}

	/**
	 * Replace the current palette with the default palette
	 */
	public void resetPalette() {
		setPalette(defaultPalette);
	}

	/**
	 * Replace the current palette with a randomly generated palette
	 */
	public void setRandomPalette() {
		Color[] palette = new Color[this.palette.length];
		for (int i = 0; i < palette.length; i++) {
			int r = (int) (Math.random() * 255);
			int g = (int) (Math.random() * 255);
			int b = (int) (Math.random() * 255);
			palette[i] = new Color(r, g, b);
		}
		setPalette(palette);
	}

	/**
	 * Calculates the size required to display the component with the current cellSize and borderSize settings
	 * 
	 * @return
	 */
	protected Dimension getRequiredSize() {
		return new Dimension(matrix.getSizeX() * cellSize, matrix.getSizeY() * cellSize + borderSize);
	}

	/**
	 * Resizes the component to the required size
	 */
	protected void updateSize() {
		image = null;//resets the drawing buffer
		Dimension requiredSize = getRequiredSize();
		this.setSize(requiredSize);
		this.setMinimumSize(requiredSize);//keep the canvas at it's size when resizing the window
		this.setMaximumSize(requiredSize);
		updateBuffer();
	}

	/**
	 * Gets a color from the palette for the given shape number.
	 * Different shape numbers can give the same color,
	 * but a same shape number will always result in the same color
	 * 
	 * @param shape
	 *            the shape number
	 * @return the color of the shape
	 */
	protected Color getShapeColor(int shape) {
		if (shape == -1) {//unmovable block
			return Color.GRAY;
		}
		int idx = (shape - 1) % palette.length;
		return palette[idx];
	}
}
