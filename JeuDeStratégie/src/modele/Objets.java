package modele;

/**
 * permet de représenter les objets présents sur le plateau (bombe, mine, mur, pastille d'energie...)
 *
 */
public interface Objets {
	/**
	 * permet de connaitre le type de l'objet
	 * @return le type de l'objet
	 */
	public String getType();
	
	/**
	 * permet d'effectuer l'action de l'objet sur le joueur
	 * @param joueur le joueur qui subit l'action de l'objet
	 */
	public void action(Personnage joueur);

}
