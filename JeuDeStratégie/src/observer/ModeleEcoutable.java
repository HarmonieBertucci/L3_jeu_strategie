package observer;

/**
 * permet de représenter les écoutables
 */
public interface ModeleEcoutable {
	/**
	 * permet d'ajouter un écouteur a l'écoutable
	 * @param ecouteur l'écouteur a ajouter
	 */
	public void ajoutEcouteur(EcouteurModele ecouteur);

	/**
	 * permet d'enlever un écouteur a l'écoutable
	 * @param ecouteur l'écouteur a retirer
	 */
	public void retraitEcouteur(EcouteurModele ecouteur);
}
