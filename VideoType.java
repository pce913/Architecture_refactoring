
public enum VideoType {

	DEFAULT(0, 0, 0),
	VHS(1, 1, 5),
	CD(2, 2, 3),
	DVD(3, 3, 2);

	int type;

	int limit;

	int pentalty;

	VideoType(int type, int pentalty, int limit) {
		this.type = type;
		this.pentalty = pentalty;
		this.limit = limit;
	}

	public int getType() {
		return type;
	}

	public int getLimit() {
		return limit;
	}

	public int getPentalty() {
		return pentalty;
	}
}
