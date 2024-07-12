package modele;

/**
 * permet de représenter les pastilles d'énergies sur le plateau
 * étend de AbstractObjet
 *
 */
public class Pastille extends AbstractObjet {
	/**
	 * on définit qu'une pastille redonne 25 points d'énergie au joueur
	 */
	protected final int RECUPERATION = 25;
	
	/**
	 * constructeur donnant a la pastille le type 'Pastille' a AbstractObjet
	 */
	public Pastille () {
		super("Pastille");
	}

	/**
	 * permet d'effectuer l'action de l'objet sur le joueur -- l'action d'une pastille est de donner de l'énergie (RECUPERATION) au joueur
	 * @param joueur le joueur qui subit l'action de l'objet
	 */
	@Override
	public void action(Personnage joueur) {
		joueur.ajoutEnergie( RECUPERATION);
	}
	

}
