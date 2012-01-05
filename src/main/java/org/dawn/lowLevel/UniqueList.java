package org.dawn.lowLevel;

import java.util.*;

/**
 * A list designed so as not to allow duplicates.
 * 
 */
public class UniqueList<E> extends ArrayList<E> {
	
	/**
	 * Append the specified elemnt to the end of the list, first checking if it is a duplicate
	 * 
	 * @param e The element to add
	 * @return true if the list was modified as a result of this operation.
	 */
	public boolean add(E e){
		if(contains(e)) return false; // do not allow duplicates
		else return super.add(e);
		
	}
	
	
	public void add(int index, E element){
		if(contains(element)) return; // do not allow duplicates
		else super.add(index, element);
	}
	
	/**
	 * 
	 * @return true if the list is modified in any way by this method call
	 */
	public boolean addAll(Collection<? extends E> c){
		int initialSize = size(); // use size as a test for modification.
		for(E e : c){
			add(e);
		}
		return (size() == initialSize);
	}
	
	public boolean addAll(int index, Collection<? extends E> c){
		int initialSize = size(); // use size as a test for modification.
		int position = index;
		for(E e : c){
			add(position++, e);
		}
		return (size() == initialSize);
	}
	
	/**
	 * @return null if nothing changed, otherwise return the element initially at index
	 */
	public E set(int index, E element) throws IndexOutOfBoundsException {
		if(contains(element)) return null; // No duplicates allowed
		else return super.set(index, element);
	}
	
}
