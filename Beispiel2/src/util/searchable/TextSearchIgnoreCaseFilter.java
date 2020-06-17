package util.searchable;

public class TextSearchIgnoreCaseFilter implements ISearchFilter {

 	public boolean searchFilterFunction(Object originalObject, Object compareObject) {
 		if (originalObject == null || compareObject == null) return false;
		return originalObject.toString().equalsIgnoreCase(compareObject.toString());
 	}
}
