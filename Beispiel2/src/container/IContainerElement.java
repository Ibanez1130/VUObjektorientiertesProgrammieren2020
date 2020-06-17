/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 02.05.2020
 */
package container;

public interface IContainerElement<E> {
	public E getData();
	public IContainerElement<E> getNextElement();
	public boolean hasNextElement();
	public void setNextElement(IContainerElement<E> next);
}
