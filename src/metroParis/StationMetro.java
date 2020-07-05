package metroParis;

import java.util.ArrayList;

public class StationMetro {

	/**
	 * Classe d'objet StationMetro
	 * j'ai ajouté deux champs suivants:
	 * intTempsArc = durée de trajet jusqu'à la stationPrecedent
	 * successuers = List de successeurs pour cette Station
	 */
	private int intStation=0;
	private String nomStation;
	private int intLigne;
	private int intTempsMin;
	private int intTempsArc;
	private StationMetro stationPrecedent;
	private ArrayList<Arc> successuers = new ArrayList<Arc> ();
	
	public StationMetro(int intStation, String nomStation, int intLigne) {
		this.intStation = intStation;
		this.nomStation = nomStation;
		this.intLigne = intLigne;
		this.intTempsMin=1000;
		this.stationPrecedent=null;
	}
	public StationMetro(StationMetro stMetro) {
		this.intStation = stMetro.getIntStation();
		this.nomStation = stMetro.getNomStation();
		this.intLigne = stMetro.getIntLigne();
		this.intTempsMin=stMetro.getDuree();
		this.stationPrecedent=stMetro.getStationPrecedente();
		this.successuers = stMetro.successuers;
	}
	public int getIntStation() {
		return intStation;
	}
	public void setIntStation(int intStation) {
		this.intStation = intStation;
	}
	public String getNomStation() {
		return nomStation;
	}
	public void setNomStation(String nomStation) {
		this.nomStation = nomStation;
	}
	public int getIntLigne() {
		return intLigne;
	}
	public void setIntLigne(int intLigne) {
		this.intLigne = intLigne;
	}
	public String getNomLigne() {
		String res = "Ligne ";
		if (this.intLigne <= 14) {
			res += this.intLigne;
		}
		if (this.intLigne == 33) {
			res =res+ "3bis";
		}
		if (this.intLigne == 77) {
			res =res+ "7bis";
		}
		return res;
	}
	public int getDuree() {
		return this.intTempsMin;
	}
	public void setDuree(int minutes) {
		intTempsMin=minutes;
	}
	public int getIntTempsArc() {
		return intTempsArc;
	}
	public void setIntTempsArc(int intTempsArc) {
		this.intTempsArc = intTempsArc;
	}
	public StationMetro getStationPrecedente() {
		return this.stationPrecedent;
	}
	public void setStationPrecedente(StationMetro stationPrecedent) {
		this.stationPrecedent=stationPrecedent;
	}
	public StationMetro getStation (int intStation) {
		return this;
	}
	public ArrayList<Arc> getSuccesseurs() {
		return this.successuers;
	}
	public void addSuccesseur (Arc arc) {
		this.successuers.add(arc);
	}
	@Override
	public String toString() {
		return "StationMetro [intStation=" + intStation + ", nomStation=" + nomStation + ", intLigne=" + intLigne
				+ ", intTempsMin=" + intTempsMin + ", stationPrecedent=" + stationPrecedent + ", successuers="
				+ successuers + "]";
	}

}
