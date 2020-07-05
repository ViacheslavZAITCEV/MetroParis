package metroParis;

public class TasStations {

	// var StationMetro [] station - le Tas des StationMetro
	//
	// var indicesStationsDansTas [] garde l'indice dans Tas.
	// Donc dans la cellule [1] on trouve l'indice de station 1 (Grande Arche de La
	// Défense) dans Tas

	private StationMetro[] station;
	private int nbElements;
	private int[] indicesStationsDansTas;

	public TasStations() {
		StationMetro[] station = new StationMetro[388];
		this.station = station;
		this.nbElements = 0;
		indicesStationsDansTas = new int[388];
	}

	public void initialiser(StationMetro[] stations) {
		int i = 0;
		for (StationMetro s : stations) {
			s.setDuree(1000);
			s.setStationPrecedente(null);
			insererStation(s);
			i++;
		}
	}

	public int size() {
		return nbElements;
	}

	public int getDuree(StationMetro st) {
		if (st.getIntStation() >= 1 && st.getIntStation() <= 388) {
			return this.station[st.getIntStation()-1].getDuree();
		}
		System.out.println("Error: il y a une demande de station inexiste (TasStations.getDuree)");
		return 100000;
	}

	public StationMetro getMinimum() {
		return station[0];
	}

	public void supprimerElemDansTas(int nStation) {
		int pere = nStation-1;
		indicesStationsDansTas[pere] = -1;
		int fils = nbElements;
		int filsGauche = 2 * pere + 1;
		int filsDroit = 2 * pere + 2;
		while (filsGauche < nbElements) {
			if (filsDroit >= nbElements || station[filsGauche].getDuree() <= station[filsDroit].getDuree()) {
				fils = filsGauche;
			} else {
				fils = filsDroit;
			}
			station[pere] = station[fils];
			indicesStationsDansTas[station[pere].getIntStation()-1] = pere;
			pere = fils;
			filsGauche = 2 * pere + 1;
			filsDroit = 2 * pere + 2;
		}
		nbElements--;
	}

	public void insererStation(StationMetro newst) {
		if (nbElements < 388) {
			int fils = nbElements;
			int pere = (fils - 1) / 2;
			station[fils] = newst;
			indicesStationsDansTas[newst.getIntStation() - 1] = fils;
			while (pere >= 0 && newst.getDuree() < station[pere].getDuree()) {
				station[fils] = station[pere];
				station[pere] = newst;
				indicesStationsDansTas[newst.getIntStation() - 1] = pere;
				fils = pere;
				pere = (fils - 1) / 2;
			}
			nbElements++;
		} else {
			System.out.println("Error: Tas est plein (TasStations.insertStation)");
		}
	}

	public void diminuerDuree(int nStation) {
		nStation --;
		StationMetro temp;
		int fils = nStation;
		int pere = (fils - 1) / 2;
		while (pere > 0 && station[pere].getDuree() >= station[fils].getDuree()) {
//			System.out.println("Diminuer durée de la station n="+fils);
			temp = station[fils];
			station[fils] = station[pere];
			station[pere] = temp;
			indicesStationsDansTas[fils] = fils;
			indicesStationsDansTas[pere] = pere;
			fils = pere;
			pere = (fils - 1) / 2;
		}
	}

	public boolean isExist(int nStation) {
		nStation --;
		if (indicesStationsDansTas[nStation] == -1)
			return false;
		return true;
	}

}
