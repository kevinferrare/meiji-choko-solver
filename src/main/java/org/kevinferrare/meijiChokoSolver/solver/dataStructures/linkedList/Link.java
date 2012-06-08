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
package org.kevinferrare.meijiChokoSolver.solver.dataStructures.linkedList;

/**
 * A link of the RelinkableLinkedList
 * 
 * @author Kévin Ferrare
 * 
 * @param <Type>
 */
public class Link<Type> {
	private Link<Type> next;
	private Link<Type> previous;
	private Type content;

	/**
	 * Get the data stored in this link
	 * @return the data stored in this link
	 */
	public Type getContent() {
		return content;
	}

	/**
	 * Set the data stored in this link
	 * @param content the data to store in this link
	 */
	public void setContent(Type content) {
		this.content = content;
	}

	/**
	 * Insert this link between the two specified elements
	 * 
	 * @param previous
	 * @param next
	 */
	protected void link(Link<Type> previous, Link<Type> next) {
		this.previous = previous;
		this.next = next;
		if (this.previous != null) {
			this.previous.next = this;
		}
		if (this.next != null) {
			this.next.previous = this;
		}
	}

	/**
	 * Detach this link from the list, only call this from RelinkableLinkedList
	 */
	protected void unlink() {
		if (this.previous != null) {
			this.previous.next = this.next;
		}
		if (this.next != null) {
			this.next.previous = this.previous;
		}
	}

	/**
	 * Reattach this link in the list, only call this from RelinkableLinkedList
	 */
	protected void relink() {
		this.link(this.getPrevious(), this.getNext());
	}

	/**
	 * Get the next link in the list
	 * @return next link or null if end of list reached
	 */
	public Link<Type> getNext() {
		return next;
	}

	/**
	 * Get the previous link in the list
	 * @return the previous link in the list, will not return null even when reaching the head of the list
	 */
	protected Link<Type> getPrevious() {
		return previous;
	}
}
