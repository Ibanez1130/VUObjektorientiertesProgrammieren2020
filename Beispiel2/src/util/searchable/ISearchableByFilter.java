package util.searchable;

import java.util.Collection;

public interface ISearchableByFilter<T> {

	public Collection<T> searchByFilter(ISearchFilter filter, Object compareObject);
}
