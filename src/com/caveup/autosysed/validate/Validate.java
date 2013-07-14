package com.caveup.autosysed.validate;

public interface Validate<E> {

	/**
	 * Validate a given element
	 * 
	 * @param element
	 * @return
	 */
	boolean validate(E element);
}
