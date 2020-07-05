package metroParis;

/**
 * @author Kvazibus
 * la classe qui est appelée pour aider trouver le successeur suivant
 * 
 * En gros, c'est une liste chainée qui garde la trace des successeurs passées.
 * Une fois l'algorithme arrive au fin d'un impasse (par example, un terminus)
 * l'algo peut faire retour grace à cette liste chainée. 
 *
 */
public class Successeurs {
	
	private int courant;
	private Successeurs parent;
	public Successeurs(int courant, Successeurs parent) {
		super();
		this.courant = courant;
		this.parent = parent;
	}
	public int getCourant() {
		return courant;
	}
	public void setCourant(int courant) {
		this.courant = courant;
	}
	public Successeurs getParent() {
		return parent;
	}
	public void setParent(Successeurs parent) {
		this.parent = parent;
	}
	public Successeurs add(int nStationNext) {
		Successeurs listNew = new Successeurs(nStationNext, this);
		return listNew;
	}
}
