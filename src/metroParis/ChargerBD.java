package metroParis;

import java.io.IOException;
import java.util.*;

public class ChargerBD {

	
	// M�thode � savoir le chemin de disposition la programme sur la disque d�r 
	public static String chercherPathBD() {
		String workingDIR = System.getProperty("user.dir");
		return workingDIR;
	}

	// M�thode qui lit les donn�s de fichier et retourne List des lignes de fichier 
	public static ArrayList<String> readALfromFile(ArrayList<String> arl, String file) {
		java.io.BufferedReader lines;
		System.out.println("Chargement la base de donn�e");
		try {
			lines = new java.io.BufferedReader(new java.io.FileReader(file));
			while (lines.ready()) {
				arl.add(lines.readLine());
			}
			lines.close();
		} catch (IOException E) {
			System.out.println("can't find the file " + file);
		}
		return arl;
	}

	// M�thod pour charger la BD. Il rassemble:
	// - charger BD dans ArrayList, type String
	// - puis, en utilisant ArrayList, on complete les StationMetro 
	// - enfin en utilisant ArrayList, on complete les Arcs
	public static Metro readBDfromHD() {
		ArrayList<String> arl = new ArrayList<String>();
		String file = chercherPathBD() + "\\bin"+"\\metroParis"+"\\reseau_metro_projet2020.txt";
		arl = readALfromFile(arl, file);
		int ligne = 3;
		int[] nStationInLine = nombreStationLines(arl.get(ligne));
		int lignesMetro = nStationInLine.length;
		ligne = 1;
		StationMetro[] station = traiterStations(arl, ligne, nStationInLine);
		ligne = 414;
		Arc[] arcs = traiterArcs(station, arl, ligne);
		Metro res = new Metro(station, arcs, lignesMetro, nStationInLine);
		return res;
	}

	// M�thode � savoir le nombre des lignes
	private static int[] nombreStationLines(String inLine) {
		String[] nStationInLinesStr = inLine.split(":");
		int[] nStationInLine = new int[nStationInLinesStr.length];
		for (int i = 0; i < nStationInLinesStr.length; i++) {
			nStationInLine[i] = Integer.parseInt(nStationInLinesStr[i]);
		}
		return nStationInLine;
	}

	private static StationMetro[] traiterStations(ArrayList<String> arl, int numLigne, int[] nStationInLine) {
		int max = Integer.parseInt(arl.get(numLigne));
		numLigne +=3;
		System.out.println("Le nombre de stations est "+max);
		StationMetro[] station = new StationMetro[max];
		String line;
		String[] stat = new String[3];
		int courant = 0;
		while (courant < max && numLigne < arl.size()) {
			line = arl.get(numLigne);
			if (!line.substring(0, 6).equals("######")) {
				stat = line.split(":");
				station[courant] = new StationMetro(Integer.parseInt(stat[0]), stat[1], Integer.parseInt(stat[2]));
				courant++;
			}
			numLigne++;
		}
		System.out.println("Le "+station.length+ " stations sont t�l�charg�es");
		return station;
	}

	private static Arc[] traiterArcs(StationMetro[] station, ArrayList<String> arl, int ligne) {
		Arc[] res = new Arc[Integer.parseInt(arl.get(ligne))];
		ligne++;
		String line;
		String[] arc = new String[4];
		int courant = 0;
		while (courant < res.length && ligne < arl.size()) {
			line = arl.get(ligne);
			if (!line.substring(0, 6).equals("######")) {
				arc = line.split(":");
				res[courant] = new Arc(Integer.parseInt(arc[0]), Integer.parseInt(arc[1]), Integer.parseInt(arc[2]),
						Integer.parseInt(arc[3]));
				station[Integer.parseInt(arc[1])-1].addSuccesseur(res[courant]);
				courant++;
			}
			ligne++;
		}

		return res;
	}
}
