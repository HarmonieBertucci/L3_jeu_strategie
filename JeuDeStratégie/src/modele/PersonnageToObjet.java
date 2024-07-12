package modele;

/**
 * classe permettant d'adapter un personnage en objet pour le représenter sur le plateau
 */
public class PersonnageToObjet extends AbstractObjet{
	private Personnage joueur;

	public PersonnageToObjet (Personnage j) {
		super("joueur");
		this.joueur = j;
	}

	public Personnage getPersonnage(){
		return this.joueur;
	}

	@Override
	public void action (Personnage j) {
	}
}
