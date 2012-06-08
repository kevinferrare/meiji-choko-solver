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
 * A simple double linked list in which you can reinsert detached Links,
 * increases performances over the default java implementation
 * 
 * @author Kévin Ferrare
 * 
 * @param <Type>
 */
public class RelinkableLinkedList<Type> {
	private Link<Type> head = new Link<Type>();
	private Link<Type> top;//last link of the list
	private int size = 0;

	public RelinkableLinkedList() {
		head = new Link<Type>();//empty link node for the head container
		top = head;//empty list, last link = head
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Add an object to the end of the list
	 * @param content object to add
	 */
	public void add(Type content) {
		Link<Type> newLink = new Link<Type>();
		newLink.setContent(content);
		newLink.link(top, null);
		top = newLink;
		size++;
	}

	/**
	 * Get the first element of the list
	 * @return the first element of the list
	 */
	public Link<Type> getFirst() {
		return this.head.getNext();
	}

	/**
	 * Detach the given link from the list in O(1)
	 * 
	 * @param link
	 *            the link to detach
	 */
	public void unlink(Link<Type> link) {
		link.unlink();
		size--;
	}

	/**
	 * Reattach the given link to the list in O(1)
	 * 
	 * @param link
	 *            the link to reattach
	 */
	public void relink(Link<Type> link) {
		link.relink();
		size++;
	}
}
