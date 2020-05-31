/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 02.05.2020
 */
package util.searchable;

public class TextSearchIgnoreCaseFilter implements ISearchFilter {

 	public boolean searchFilterFunction(Object originalObject, Object compareObject) {
 		if (originalObject == null || compareObject == null) return false;
		return originalObject.toString().equalsIgnoreCase(compareObject.toString());
 	}
}
