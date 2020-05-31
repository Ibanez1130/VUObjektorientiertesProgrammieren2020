/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 13.03.2020
 */
package rbvs.record;

public abstract class Record implements IRecord {
	private long id;

	public Record(long identifier) {
		this.id = identifier;
	}

	@Override
	public long getIdentifier() {
		return id;
	}

}
