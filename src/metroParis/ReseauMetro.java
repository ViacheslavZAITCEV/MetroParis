package metroParis;

import java.util.ArrayList;


public class ReseauMetro {

	private TasStations tas;
	private StationMetro[] station;
	private Successeurs successeurs;
	private Liste2 trace;

	public ReseauMetro(Metro metro) {
		this.station = metro.copyStations();
		this.tas = new TasStations();
		tas.initialiser(station);
	}

	public int tempsEntreStations() {
		return 0;
	}

	public String getNomStation(StationMetro nStation) {
		return nStation.getNomStation();
	}

	public int getLigneStation(StationMetro nStation) {
		return nStation.getIntLigne();
	}

	public StationMetro addStation(int intStation, String nomStation, int intLigne) {
		StationMetro res = new StationMetro(intStation, nomStation, intLigne);
		tas.insererStation(res);
		return res;
	}

//	public Liste2 addListe2(int nStation, int nStationNext, int temps) {
//		Liste2 lll = new Liste2(nStation, nStationNext, temps);
//		return lll;
//	}

	/**
	 * je vois deux possibilit�s de recuperer l'info de trajet: 1 - chercher la
	 * station precedente dans l'objet StationMetro en utilisant la classe
	 * StationMetro comme une liste chain�e 2 - utliliser la classe Liste2 car on
	 * l'a cr�� pour �a
	 * 
	 * @param nStationStart
	 * @param nStationFin
	 */
	public void trajet(int nStationStart, int nStationFin) {
		trace = new Liste2(nStationStart);
		dijkstra(nStationStart, nStationFin);

//	1 variant:		
		int courant = nStationFin;
		ArrayList<StationMetro> trajet = new ArrayList<StationMetro>();
		while (courant != nStationStart || station[courant - 1].getStationPrecedente() != null) {
			trajet.add(station[courant - 1]);
			System.out.println(courant);
			courant = station[courant - 1].getStationPrecedente().getIntStation();
		}
		trajet.add(station[courant - 1]);
		afficher(trajet);

////	2 variant:		
//		afficherTrace();	
	}

//	private void afficherTrace() {
//		System.out.println("======================================");
//		System.out.println("    itineraire optmale est:");
//		int i=1;
//		int duree=0;
//		StationMetro s;
//		while(trace.getList2()==null) {
//			s= station[trace.getnStation()-1];
//			System.out.println("Station " + i + ": " + s.getNomStation() + " (" + s.getNomLigne() + ")");
//			System.out.println("[ Trajet de " + trace.getTemps()+ " minutes]");
//			duree += trace.getTemps();
//			trace = trace.getList2();
//		}
//		System.out.println("Station " + i + ": " + station[trace.getnStation()-1].getNomStation() + " (" + station[trace.getnStation()-1].getNomLigne() + ")");
//		System.out.print("Dur�e de trajet est " +duree);
//		System.out.println("====================================== \n");
//}

	public void afficher(ArrayList<StationMetro> trajet) {
		System.out.println("......................................");
		System.out.println("    itineraire optimale est:");
		StationMetro s, sOld;
		int duree = 0;
		System.out.println("Depart, station " + 1 + ": " + trajet.get(trajet.size() - 1).getNomStation() + " ("
				+ trajet.get(trajet.size() - 1).getNomLigne() + ")");
		sOld = trajet.get(trajet.size() - 1);
		int ligne = sOld.getIntLigne();
		for (int i = trajet.size() - 2, j = 2; i >= 0; i--, j++) {
			s = trajet.get(i);
			if (ligne == s.getIntLigne()) {
				System.out.println("[ Trajet de " + s.getIntTempsArc() + " minutes]");
				duree += s.getIntTempsArc();
				System.out.println("Station " + j + ": " + s.getNomStation() + " (" + s.getNomLigne() + ")");
				sOld = s;
			} else {
				System.out.print(">>>>>>>> Changement [" + s.getIntTempsArc() + " minutes]");
				duree += s.getIntTempsArc();
				System.out.println(" � " + s.getNomStation() + " (" + sOld.getNomLigne()+ " --> " + s.getNomLigne() + ")");
				ligne = s.getIntLigne();
			}
		}
		System.out.println("Vous �tes arriv�(e)");
		System.out.print("Dur�e de trajet de " + trajet.get(trajet.size() - 1).getNomStation());
		System.out.print(" � " + trajet.get(0).getNomStation());
		System.out.println(" est " + duree + " minutes");
		System.out.println("...................................... \n");
	}

	public void dijkstra(int depart, int destination) {
		if (!tas.isExist(depart)) {
//			 inserer la station de depart dans tas s'il n'y existe pas
			tas.insererStation(station[depart - 1].getStation(depart));
		}

		// le temps de depart � depart est 0
		station[depart - 1].getStation(depart).setDuree(0);
		tas.diminuerDuree(depart);

		StationMetro suc;
		successeurs = new Successeurs(depart, null);
		int i = depart;
		ArrayList<Arc> sucs;

		// boucle de recherche l'itineraire optimale
		// (alogorithme de Dijkstra,
		// https://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra)
		do {

			// sucs = une liste de successeurs de Station 'i'
			sucs = station[i - 1].getSuccesseurs();
			System.out.println("station numero: " + i +", n(sucs)="+sucs.size());
			for (int j = 0; j < sucs.size(); j++) {

				// j-eme successeur de la liste sucs
				suc = station[sucs.get(j).getNumStationNext() - 1];
				System.out.println("i=" + i + ", suc="+suc.getIntStation()+", duree=" + suc.getDuree() );

//				 pour les graphes non-orient�es
//				 si le chemin vers Station 'j' n'est pas trait� ( j est existe dans le Tas) AND
//				 si le chemin vers Station 'j' est plus courte par le sommet 'i'
//				if (tas.isExist(suc.getIntStation()) && 
//					suc.getDuree() > station[i - 1].getDuree() + sucs.get(j).getMinutes()) {
				
//				// pour les graphes orient�es
//				// si le chemin vers Station 'j' est plus courte par le sommet 'i'
				if (suc.getDuree() > station[i - 1].getDuree() + sucs.get(j).getMinutes()) {

					// on �crire, que la station precedant dans le chemin sera 'i'
					suc.setStationPrecedente(station[i - 1]);

					// on ajoute la trace d'itineraire dans la liste chain�e
					trace = new Liste2(i, suc.getIntStation(), sucs.get(j).getMinutes(), trace);

					// on �crire que le chemin vers Station 'j' est la somme des trajes (depart ->
					// 'i') + ('i'->'j')
					suc.setDuree(station[i - 1].getDuree() + sucs.get(j).getMinutes());

					// on ajoute le temps de passage dans StationMetro
					station[i - 1].setIntTempsArc(sucs.get(j).getMinutes());

					// on monte la station 'j' dans Tas
					tas.diminuerDuree(suc.getIntStation());
					System.out.println("Le noveau temps entre " + i + " et "+suc.getIntStation()+" et " + suc.getDuree() );
				}
			}
			tas.supprimerElemDansTas(i);
			System.out.println("Station "+i+" est supprim� de Tas");
			System.out.println("Taille de Tas est "+tas.size());
			i = choisirSuccesseurSuivant(i, depart);
//		} while (tas.isExist(destination));
		} while (tas.size()>0);
	}


/**
 * @author Kvazibus
 * 	 M�thod qui est en charge de trouver le successeur suivant.
 * 	 Le m�thod contient trois boucles imbriqu�s:
 * 
 * 1 - gerer la liste chain� pour laisser la trace de successeur 
 * 		il est capable de trouver le retour d'un impasse
 * 		la compl�xit� en op�rations est O(n) 
 * 		o� n=longeur de la liste chain� (dans pire cas - longeur d'un impasse)
 *  
 * 2 - testeur:
 * 		il est en charge de refuser les successeur suprim�es de la Tas
 *   	la compl�xit� en op�rations est O(m)
 *   	o� m=quantit� des successeurs de cette sommet
 *   	(au pire cas m=5 dans notre graphe: Chatelet, R�public, Opera, Saint-Lazar, ...) 
 * 
 * 3 - trouver le successeur avec le nombre minimal
 * 		boucle 'for' est r�alis� dans le m�thod 'getSucMin'
 *		la compl�xit� en op�rations est O(m-k)
 *		o� m-k= quantit� des successeurs 
 *				de cette sommet qui n'est pas pass� dans boucle 2
 *	 	(au pire cas m=5 dans notre graphe: Chatelet, R�public, Opera, Saint-Lazar, ...)

 *
 * la compl�xit� de deux derni�res boucles est O(m*(m-k)) = O (m*m)
 * la compl�xit� de trois derni�res boucles est O(n * m * m)
 * Les algorithmes avec la compl�xit� en op�rations de puissance '3' sont 
 * tr�s lourds et inefficaces. 
 * Dans le cadre de notre projet (m<=5, n<20) ce n'est pas tr�s grave. 
 * Mais pour les graphes plus compliques il faut chercher d'autre moyen.
 * Par exepmle: 
 * - introduire les successeur dans la Station par ordre croissant
 * 		�a nous permet de supprime la troisi�me boucle.
 * - introduire marquage des arcs pass�s
 * - ...   
 * 
 */ 
	
	private int choisirSuccesseurSuivant(int i, int depart) {
		ArrayList<Arc> sucs;
		int suc = 0;
		int sortir = 1;
//		int sucMin=0;
		do {
			sucs = station[i - 1].getSuccesseurs();
			int k = 0;
			while (k < sucs.size()) {
				sortir = 1;
//				sucMin = getSucMin(sucs, k);
				suc = sucs.get(k).getNumStationNext();
				System.out.println(sortir+"-me test le successeur suivant: " +suc);
				if (tas.isExist(suc)) {
					successeurs = successeurs.add(sucs.get(k).getNumStationNext());
					System.out.println("Test test bien pass� pour " +suc);
					return suc;
				}
				k++;
			}
			if (successeurs.getParent() == null) {
				sortir++;
			}else {
				successeurs = successeurs.getParent();
			}
			i = successeurs.getCourant();
		} while (sortir < 2);
		throw new RuntimeException("La destination est inaccessible");
		
	}

//	//M�thod qui trouve le plus petit successeur dans List 'sucs' � partir de numero de la liste 'k' 
//	private int getSucMin(ArrayList<Arc> sucs, int k) {
//		int sucMin = sucs.get(k).getNumStationNext();
//		for (int i=k; i<sucs.size(); i++) 
//			if (sucMin>sucs.get(i).getNumStationNext())
//				sucMin=sucs.get(i).getNumStationNext();
//		return sucMin;
//	}

}
