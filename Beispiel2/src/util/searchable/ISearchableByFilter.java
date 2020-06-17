/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 02.05.2020
 */
package util.searchable;

import java.util.Collection;

public interface ISearchableByFilter<T> {

	public Collection<T> searchByFilter(ISearchFilter filter, Object compareObject);
}
