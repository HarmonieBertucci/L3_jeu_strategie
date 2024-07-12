package modele;
import observer.AbstractModeleEcoutable;

import java.util.*;

/**
 * permet de représenter les personnages/joueurs sur le plateau
 *
 */
public class Personnage extends AbstractModeleEcoutable {
	/**
	 * on définit la vie max du personnage a 100
	 */
	protected final int VIE_MAX=100;

	/**
	 * le nom du personnage
	 */
	protected String nom;

	/**
	 * l'energie du personnage
	 */
	protected int  energie;

	/**
	 * la case sur laquelle est le personnage
	 */
	protected CasePlateau casePlateau;

	/**
	 * l'inventaire du parsonnage
	 */
	protected Map<String,Integer> inventaire;

	/**
	 * si le personnage est protégé par un bouclier
	 */
	protected boolean bouclier;

	/**
	 * si le personnage est mort
	 */
	protected boolean mort;

	/**
	 * le proxy du joueur
	 */
	protected Proxy proxy;

	/**
	 * constructeur initialise l'inventaire, définit que le joueur n'est pas protégé par un bouclier, que sa vie est égal a VIE_MAX
	 * @param nom le nom du joueur donné par l'utilisateur
	 */
	public Personnage(String nom) {
		this.nom=nom;
		initialisationInventaire();
		this.bouclier=false;
		this.energie=VIE_MAX;
	}

	/**
	 * permet de donner un proxy au personnage après l'avoir créé
	 * @param p proxy donné
	 */
	public void setProxy(Proxy p){
		this.proxy=p;
	}

	public Proxy getProxy(){
		return this.proxy;
	}

	/**
	 * permet d'initialiser l'inventaire (5 bombes, 5 mines, 100 munitions)
	 */
	public void initialisationInventaire() {
		this.inventaire = new HashMap<>();
		this.inventaire.put("bombe", 5);
		this.inventaire.put("mine", 5);
		this.inventaire.put("munition", 100);

	}

	/**
	 * permet de récupérer l'énergie du personnage
	 * @return l'énergie
	 */
	public int getEnergie() {
		return this.energie;
	}

	/**
	 * permet de récupérer la case sur laquelle est le personnage
	 */
	public CasePlateau getCase() {
		return this.casePlateau;
	}

	public String getName() {
		return this.nom;
	}

	public Map<String, Integer> getInventaire(){
		return this.inventaire;
	}

	/**
	 * permet de savoir si le personnage est protégé par un bouclier
	 */
	public boolean estProtege() {
		return this.bouclier;
	}

	/**
	 * permet de savoir si le personnage est mort
	 */
	public boolean estMort() {
		return this.mort;
	}

	/**
	 * permet de retirer de l'énergie au personnage et de le déclarer mort si c'est le cas
	 * @param nombre le nombre de points d'energie a retirer
	 */
	public void retraitEnergie(int nombre) {
		this.energie-=nombre;
		if(this.energie<=0) {
			this.mort=true;
		}
		fireChangement();
	}

	/**
	 * permet d'ajouter de l'énergie au personnage (si son energie dépasse VIE_MAX on le remet a VIE_MAX
	 * @param nombre le nombre de points d'energie a ajouter
	 */
	public void ajoutEnergie(int nombre) {
		this.energie+=nombre;
		if(this.energie>VIE_MAX) {
			this.energie=VIE_MAX;
		}
		fireChangement();
	}

	/**
	 * permet de définir casePlateau comme étant la case sur laquelle est le personnage
	 * @param casePlateau la case sur laquelle est le personnage
	 */
	public void setCase(CasePlateau casePlateau) {
		this.casePlateau= casePlateau;
	}

	/**
	 * permet de définir que le personnage est protégé par un bouclier jusqu'a son prochain tour
	 */
	public void setBouclier(){
		this.bouclier=true;
		fireChangement();
	}

	/**
	 * permet de lui retirer son bouclier
	 */
	public void finBouclier(){
		this.bouclier=false;
		fireChangement();
	}

	/**
	 * permet de savoir si il a une mine dans son inventaire et si oui de la poser
	 * @return si il a pu la poser
	 */
	public boolean poseMine(){
		int nombreMine= this.inventaire.get("mine");
		if(nombreMine>0){
			this.inventaire.put("mine",nombreMine-1);
			fireChangement();
			return true;
		}
		return false;
	}

	/**
	 * permet de savoir si il a une bombe dans son inventaire et si oui de la poser
	 * @return si il a pu la poser
	 */
	public boolean poseBombe(){
		int nombreBombe= this.inventaire.get("bombe");
		if(nombreBombe>0){
			this.inventaire.put("bombe",nombreBombe-1);
			fireChangement();
			return true;

		}
		return false;
	}

	/**
	 * permet de savoir si il a assez de munitions dans son inventaire, si oui on en enlève pour qu'il puisse tirer
	 * @return si il peut tirer
	 */
	public boolean gachette(){
		int nombreMunition= this.inventaire.get("munition");
		if(nombreMunition>0){
			this.inventaire.put("munition",nombreMunition-1);
			fireChangement();
			return true;
		}
		return false;
	}

}
