/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 01.06.2020
 */
package bonus.spring.dto;

import java.util.ArrayList;
import java.util.Collection;

import rbvs.record.IInvoice;

public class Records {
	private Collection<IInvoice> records = new ArrayList<>();

	public Collection<IInvoice> getRecords () {
		return this.records;
	}

	public void setRecords (Collection<IInvoice> records) {
		this.records = records;
	}
	
	public void addRecord (IInvoice record) {
		this.records.add(record);
	}
}
