package container;

import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import util.searchable.ISearchableByFilter;
import util.searchable.ISearchFilter;

public class Container<E> implements Collection<E>, ISearchableByFilter<E> {
	private IContainerElement<E> firstElement;

	public Container() {
		this.firstElement = null;
	}

	@Override
	public boolean add (E e) throws NullPointerException {
		if (e == null) throw new NullPointerException("Parameter e is null!");
		IContainerElement<E> element = new ContainerElement<E>(e);
		if (this.contains(element)) return false;
		if (this.firstElement == null) {
			this.firstElement = element;
			return true;
		}
		IContainerElement<E> ce = this.firstElement;
		while (ce.hasNextElement()) {
			ce = ce.getNextElement();
		}
		ce.setNextElement(element);
		return true;
	}

	@Override
	public boolean addAll (Collection<? extends E> c) throws NullPointerException {
		if (c == null) throw new NullPointerException("Parameter c is null!");
		if (c.isEmpty()) return false;
		List<Boolean> success = new Vector<Boolean>();
		// TODO: Change loop to for!
		c.stream().forEach(e -> {
			try {
				success.add(this.add(e));
			} catch (NullPointerException npe) {
				success.add(false);
				System.out.println("One element could not be added to the collection: " + npe.getMessage());
			}
		});
		return !(success.contains(false));
	}
	
	@Override
	public void clear () {
		this.firstElement = null;
	}

	@Override
	public boolean contains (Object o) {
		if (o == null || this.firstElement == null) return false;
		IContainerElement<E> ce = this.firstElement;
		if (ce.getData().equals(o)) return true;
		while (ce.hasNextElement()) {
			ce = ce.getNextElement();
			if (ce.getData().equals(o)) return true;
		}
		return false;
	}
	
	@Override
	public boolean containsAll (Collection<?> c) {
		if (c == null) return false;
		if (c.isEmpty()) return false;
		for (Object o:c) {
			if (!this.contains(o)) return false;
		}
		return true;
	}
	
	public E get (int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > this.size()) throw new IndexOutOfBoundsException("The given index is out of bounds of the current collection!");
		if (this.firstElement == null) return null;
		IContainerElement<E> next = this.firstElement;
		for (int i = 0; i < index; i++) {
			if (!next.hasNextElement()) return null;
			next = next.getNextElement();
		}
		return next.getData();
	}
	
	@Override
	public boolean isEmpty () {
		return this.firstElement == null;
	}
	
	@Override
	public Iterator<E> iterator () {
		return new Itr<E>(this.firstElement);
	}
	
	@Override
	public boolean remove (Object o) {
		if (o == null) return false;
		if (!this.contains(o)) return false;
		IContainerElement<E> ce = this.firstElement;
		if (ce.getData().equals(o)) {
			this.firstElement = (ce.hasNextElement()) ? ce.getNextElement() : null;
			return true;
		}
		while (ce.hasNextElement()) {
			IContainerElement<E> ne = ce.getNextElement();
			if (ne.getData().equals(o)) {
				ce.setNextElement(ne.getNextElement());
				return true;
			}
			ce = ne;
		}
		return false;
	}
	
	@Override
	public boolean removeAll (Collection<?> c) {
		if (c == null || c.isEmpty()) return false;
		for (Object o:c) {
			boolean success = this.remove(o);
			if (!success) return false;
		}
		return true;
	}
	
	@Override
	public boolean retainAll (Collection<?> c) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not supported!");
	}
	
	@Override
	public int size () {
		if (this.firstElement == null) return 0;
		int i = 1;
		IContainerElement<E> ce = this.firstElement;
		while (ce.hasNextElement()) {
			ce = ce.getNextElement();
			i++;
			if (i == Integer.MAX_VALUE) return Integer.MAX_VALUE;
		}
		return i;
	}
	
	@Override
	public Object[] toArray () throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not supported!");
	}
	
	@Override
	public <T> T[] toArray (T[] a) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("This method is not supported!");
	}
	
	@Override
	public String toString () {
		if (this.firstElement == null) return "Container [ firstElement = null ]";
		String children = "";
		IContainerElement<E> ce = this.firstElement;
		while (ce.hasNextElement()) {
			ce = ce.getNextElement();
			children += ce.toString() + ", ";
		}
		return "Container [ firstElement = " + this.firstElement.toString() + ", children = [ " + children + " ] ]";
	}
	
	@Override
	public Collection<E> searchByFilter (ISearchFilter filter, Object filterObject) {
		if (filter == null || filterObject == null) return null;
		Container<E> c = new Container<E>();
		if (this.firstElement == null) return c;
		IContainerElement<E> ce = this.firstElement;
		if (filter.searchFilterFunction(ce.getData(), filterObject)) {
			c.add(ce.getData());
		}
		while (ce.hasNextElement()) {
			ce = ce.getNextElement();
			if (filter.searchFilterFunction(ce.getData(), filterObject)) {
				c.add(ce.getData());
			}
		}
		return c;
	}
}
