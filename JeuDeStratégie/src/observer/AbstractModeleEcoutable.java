package observer;
import java.util.*;

/**
 * implémente ModeleEcoutable ; permet que tous les écoutables aient des méthodes communes sans les réécrire
 */
public abstract class AbstractModeleEcoutable implements ModeleEcoutable{
	protected List<EcouteurModele> ecouteurs;

	/**
	 * permet d'initialiser la liste des écouteurs
	 */
	public AbstractModeleEcoutable() {
		this.ecouteurs=new LinkedList<>();
	}

	@Override
	public void ajoutEcouteur(EcouteurModele ecouteur) {
		this.ecouteurs.add(ecouteur);
		fireChangement();
	}

	@Override
	public void retraitEcouteur(EcouteurModele ecouteur) {
		this.ecouteurs.remove(ecouteur);
	}

	/**
	 * permet de prévenir les ecouteurs que l'écoutable a changé
	 */
	protected void fireChangement() {
		for(EcouteurModele e : ecouteurs) {
			e.modeleMisAJour(this);
		}
	}
}
