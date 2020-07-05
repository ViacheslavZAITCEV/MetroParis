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
	 * je vois deux possibilités de recuperer l'info de trajet: 1 - chercher la
	 * station precedente dans l'objet StationMetro en utilisant la classe
	 * StationMetro comme une liste chainée 2 - utliliser la classe Liste2 car on
	 * l'a créé pour ça
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
//		System.out.print("Durée de trajet est " +duree);
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
				System.out.println(" à " + s.getNomStation() + " (" + sOld.getNomLigne()+ " --> " + s.getNomLigne() + ")");
				ligne = s.getIntLigne();
			}
		}
		System.out.println("Vous êtes arrivé(e)");
		System.out.print("Durée de trajet de " + trajet.get(trajet.size() - 1).getNomStation());
		System.out.print(" à " + trajet.get(0).getNomStation());
		System.out.println(" est " + duree + " minutes");
		System.out.println("...................................... \n");
	}

	public void dijkstra(int depart, int destination) {
		if (!tas.isExist(depart)) {
//			 inserer la station de depart dans tas s'il n'y existe pas
			tas.insererStation(station[depart - 1].getStation(depart));
		}

		// le temps de depart à depart est 0
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

//				 pour les graphes non-orientées
//				 si le chemin vers Station 'j' n'est pas traité ( j est existe dans le Tas) AND
//				 si le chemin vers Station 'j' est plus courte par le sommet 'i'
//				if (tas.isExist(suc.getIntStation()) && 
//					suc.getDuree() > station[i - 1].getDuree() + sucs.get(j).getMinutes()) {
				
//				// pour les graphes orientées
//				// si le chemin vers Station 'j' est plus courte par le sommet 'i'
				if (suc.getDuree() > station[i - 1].getDuree() + sucs.get(j).getMinutes()) {

					// on écrire, que la station precedant dans le chemin sera 'i'
					suc.setStationPrecedente(station[i - 1]);

					// on ajoute la trace d'itineraire dans la liste chainée
					trace = new Liste2(i, suc.getIntStation(), sucs.get(j).getMinutes(), trace);

					// on écrire que le chemin vers Station 'j' est la somme des trajes (depart ->
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
			System.out.println("Station "+i+" est supprimé de Tas");
			System.out.println("Taille de Tas est "+tas.size());
			i = choisirSuccesseurSuivant(i, depart);
//		} while (tas.isExist(destination));
		} while (tas.size()>0);
	}


/**
 * @author Kvazibus
 * 	 Méthod qui est en charge de trouver le successeur suivant.
 * 	 Le méthod contient trois boucles imbriqués:
 * 
 * 1 - gerer la liste chainé pour laisser la trace de successeur 
 * 		il est capable de trouver le retour d'un impasse
 * 		la compléxité en opérations est O(n) 
 * 		où n=longeur de la liste chainé (dans pire cas - longeur d'un impasse)
 *  
 * 2 - testeur:
 * 		il est en charge de refuser les successeur suprimées de la Tas
 *   	la compléxité en opérations est O(m)
 *   	où m=quantité des successeurs de cette sommet
 *   	(au pire cas m=5 dans notre graphe: Chatelet, Républic, Opera, Saint-Lazar, ...) 
 * 
 * 3 - trouver le successeur avec le nombre minimal
 * 		boucle 'for' est réalisé dans le méthod 'getSucMin'
 *		la compléxité en opérations est O(m-k)
 *		où m-k= quantité des successeurs 
 *				de cette sommet qui n'est pas passé dans boucle 2
 *	 	(au pire cas m=5 dans notre graphe: Chatelet, Républic, Opera, Saint-Lazar, ...)

 *
 * la compléxité de deux dernières boucles est O(m*(m-k)) = O (m*m)
 * la compléxité de trois dernières boucles est O(n * m * m)
 * Les algorithmes avec la compléxité en opérations de puissance '3' sont 
 * très lourds et inefficaces. 
 * Dans le cadre de notre projet (m<=5, n<20) ce n'est pas très grave. 
 * Mais pour les graphes plus compliques il faut chercher d'autre moyen.
 * Par exepmle: 
 * - introduire les successeur dans la Station par ordre croissant
 * 		ça nous permet de supprime la troisième boucle.
 * - introduire marquage des arcs passés
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
					System.out.println("Test test bien passé pour " +suc);
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

//	//Méthod qui trouve le plus petit successeur dans List 'sucs' à partir de numero de la liste 'k' 
//	private int getSucMin(ArrayList<Arc> sucs, int k) {
//		int sucMin = sucs.get(k).getNumStationNext();
//		for (int i=k; i<sucs.size(); i++) 
//			if (sucMin>sucs.get(i).getNumStationNext())
//				sucMin=sucs.get(i).getNumStationNext();
//		return sucMin;
//	}

}
