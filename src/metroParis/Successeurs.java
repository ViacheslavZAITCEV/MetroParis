package metroParis;

/**
 * @author Kvazibus
 * la classe qui est appel�e pour aider trouver le successeur suivant
 * 
 * En gros, c'est une liste chain�e qui garde la trace des successeurs pass�es.
 * Une fois l'algorithme arrive au fin d'un impasse (par example, un terminus)
 * l'algo peut faire retour grace � cette liste chain�e. 
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
