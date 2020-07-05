package metroParis;

public class Arc {

	private int numArc;
	private int numStation;
	private int numStationNext;
	private int minutes;
	
	
	public Arc(int numArc, int numStation, int numStationNext, int minutes) {
		super();
		this.numArc = numArc;
		this.numStation = numStation;
		this.numStationNext = numStationNext;
		this.minutes = minutes;
	}
	
	public int getNumArc() {
		return numArc;
	}

	public void setNumArc(int numArc) {
		this.numArc = numArc;
	}

	public int getNumStation() {
		return numStation;
	}

	public void setNumStation(int numStation) {
		this.numStation = numStation;
	}

	public int getNumStationNext() {
		return numStationNext;
	}

	public void setNumStationNext(int numStationNext) {
		this.numStationNext = numStationNext;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
}
