package metroParis; 

public class Liste2 {
	
	private int nStation;
	private int nStationNext;
	private int temps;
	private Liste2 list2;
	
	
	public Liste2(int nStation) {
		this.nStation = nStation;
		this.list2 = null;
	}
	public Liste2(int nStation, int nStationNext, int temps, Liste2 liste2) {
		this.nStation = nStation;
		this.nStationNext = nStationNext;
		this.temps = temps;
		this.list2 = liste2;
	}
	public Liste2(int nStation, Liste2 liste2) {
		this.nStation = nStation;
		this.list2 = liste2;
	}
	
	public int getnStation() {
		return nStation;
	}
	public void setnStation(int nStation) {
		this.nStation = nStation;
	}
	public int getTemps() {
		return temps;
	}
	public void setTemps(int temps) {
		this.temps = temps;
	}
	public Liste2 getList2 () {
		return this.list2;
	}
	public void setList2 (Liste2 list2) {
		this.list2=list2;
	}
	public int getnStationNext() {
		return nStationNext;
	}
	public Liste2 add(int nStationNext) {
		Liste2 listNew = new Liste2(nStationNext, this);
		return listNew;
	}
}
