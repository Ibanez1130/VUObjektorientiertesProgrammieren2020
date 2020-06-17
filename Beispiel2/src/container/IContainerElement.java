package container;

public interface IContainerElement<E> {
	public E getData();
	public IContainerElement<E> getNextElement();
	public boolean hasNextElement();
	public void setNextElement(IContainerElement<E> next);
}
