package modele;

/**
 * permet de représenter tous les objets
 * et de définir les méthodes qui seront les mêmes pour tous les objets
 * implémente l'interface Objets
 *
 */
public abstract class AbstractObjet implements Objets{
	/**
	 * le type de l'objet
	 */
	protected String type;
	
	/**
	 * constructeur a qui on donne le type de l'objet
	 * @param type (Mine, Bombe, mur, joueur ou Pastille)
	 */
	public AbstractObjet(String type) {
		this.type=type;
	}
	
	@Override
	public String getType() {
		return this.type;
	}
	
	/**
	 * permet d'effectuer l'action de l'objet sur le joueur mais ici on ne fait rien parce que cela va dépendre de chaque objet 
	 * (et donc être redéfinit dans chaque objet)
	 * @param joueur le joueur qui subit l'action de l'objet
	 */
	@Override
	public void action(Personnage joueur) {}
}
