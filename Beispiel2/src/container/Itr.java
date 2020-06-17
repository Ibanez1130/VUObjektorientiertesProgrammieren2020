package container;

import java.util.Iterator;

public class Itr<E> implements Iterator<E> {
	private IContainerElement<E> next;

	public Itr(IContainerElement<E> firstElement) {
		this.next = firstElement;
	}
	
	public E next () {
		if (this.next == null) return null; // If we get to this, we know we are at the end of the list.
		E next = this.next.getData();
		if (this.next.hasNextElement()) {
			this.next = this.next.getNextElement(); // Set the current next Element to the next element, to iterate through the list.
		} else {
			this.next = null; // If there is no next element, set the current one as the end of the list.
		}
		return next;
	}

	public boolean hasNext () {
		if (this.next != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void remove () throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not supported by the iterator!");
	}
}
