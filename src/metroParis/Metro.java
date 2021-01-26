package metroParis;

import java.util.Scanner;

/**
 * @author Kvazibus
 *
 *         stations = tableau des Stations Metro 
 *         
 *         arcs = tableau des Arcs 
 *         
 *         lignes = quntité des lignes 
 *         
 *         nombreStation = tableau de quantité des Station par ligne
 *
 */
public class Metro {

	private StationMetro[] stations;
	private Arc[] arcs;
	private int lignes;
	private int[] nombreStation;

	public Metro(StationMetro[] stations, Arc[] arcs, int lignes, int[] nombreStation) {
		this.stations = stations;
		this.arcs = arcs;
		this.lignes = lignes;
		this.nombreStation = nombreStation;
	}

	public Arc getArc(int arc) {
		return this.arcs[arc];
	}

	public String[] getLignes() {
		String[] l = new String[lignes];
		for (int i = 0; i < lignes; i++) {
			l[i] = (i + 1) + " - Ligne ";
			if (i < 14) {
				l[i] += Integer.toString(i + 1);
			}
			if (i == 14) {
				l[i] += "3bis";
			}
			if (i == 15) {
				l[i] += "7bis";
			}
		}
		return l;
	}

	public StationMetro[] getStations(int ligne) {
		StationMetro[] res = new StationMetro[nombreStation[ligne - 1]];
		if(ligne==15)
			ligne=33;
		if(ligne==16)
			ligne=77;
		int i = 0;
		for (StationMetro s : stations) {
			if (s.getIntLigne() == ligne) {
				res[i] = s;
				i++;
			}
		}
		return res;
	}

	public StationMetro[] getStations() {
		return stations;
	}

	public StationMetro[] copyStations() {
		StationMetro[] newstations = new StationMetro[stations.length];
		for (int i = 0; i < stations.length; i++) {
			newstations[i] = new StationMetro(stations[i]);
		}
		return newstations;
	}

	private static void menu(Metro metro) {
		String[] menuChapeau = {
				"*******************************************************\n----------  Choisissez la ligne de depart   ----------",
				"-------------------------------------------------------\n--------   Choisissez la station de depart   ---------",
				"*******************************************************\n---------   Choisissez la ligne d'arrivé   -----------",
				"-------------------------------------------------------\n--------   Choisissez la station d'arrivé   ----------" };
		Scanner scan = new Scanner(System.in);
		int choix = -1;
		int[] ch = new int[4];
		int compteur = 0;
		StationMetro[] st;
		String[] stations;
		do {
			choix = menuScan(scan, menuChapeau[compteur], metro.getLignes());
			if (choix == 0) {
				System.out.println("Merci et à bientôt");
			} else {
				st = metro.getStations(choix);
				stations = metro.tabSTtoString(st);
				ch[compteur] = choix;
				compteur++;
				choix = menuScan(scan, menuChapeau[compteur], stations);
				if (choix == 0) {
					System.out.println("Merci et à bientôt");
				} else {
					ch[compteur] = st[choix - 1].getIntStation();
					compteur++;
					if (compteur == 4) {
						compteur = 0;
						ReseauMetro reseauMetro = new ReseauMetro(metro);
						reseauMetro.trajet(ch[1], ch[3]);
					}
				}
			}
		} while (choix != 0);
	}

	private String[] tabSTtoString(StationMetro[] st) {
		String[] tab = new String[st.length];
		for (int i = 0; i < st.length; i++) {
			tab[i] = (i+1) +" - "+ st[i].getNomStation();
		}
		return tab;
	}

	public static int menuScan(Scanner scan, String menuChapeau, String[] menu) {
		int res = -1;
		do {
			System.out.println(menuChapeau);
			for (String m : menu)
				System.out.println(m);
			System.out.println("0 - exit");
			System.out.print("votre choix: ");
			try {
				res = scan.nextInt();
				if (res < 0 || res > menu.length) {
					System.out.println("Erreur: votre choix doit etre compris entre 0 et " + (menu.length));
				};
			} catch (Exception e) {
				res = -1;
				System.out.println("Erreur: votre choix doit etre nombre entier");
			}
		} while (res < 0 || res > menu.length);
		return res;
	}
	// commit git

	
	
	public static void main(String[] args) {
		Metro runMetro = ChargerBD.readBDfromHD();
		menu(runMetro);

	}

}
