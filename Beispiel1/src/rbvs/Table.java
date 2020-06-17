package rbvs;

public class Table {
	private String id;
	private int seats;

	public Table(String id) {
		this.id = id;
		this.seats = 2;
	}
	
	public Table(String id, int seats) {
		this.id = id;
		this.seats = seats;
	}

	public int getSeatCount() {
		return this.seats;
	}
	
	public void setSeatCount(int seats) {
		this.seats = seats;
	}
	
	public String getTableIdentifier() {
		return this.id;
	}
	
	public String toString() {
		return "Table [ identifier = " + this.getTableIdentifier() + ", seats = " + this.getSeatCount() + " ]";
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Table)) return false;
		Table t = (Table) obj;
		if (!(this.getTableIdentifier().equals(t.getTableIdentifier()))) return false;
		if (this.getSeatCount() == t.getSeatCount()) return true;
		return false;
	}
}
