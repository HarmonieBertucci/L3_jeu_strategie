package observer;

/**
 * permet de représenter les écouteurs
 */
public interface EcouteurModele {
	/**
	 * permet de se mettre a jour quand ce qu'on écoute a changé
	 * @param source
	 */
	public void modeleMisAJour(Object source);
}
