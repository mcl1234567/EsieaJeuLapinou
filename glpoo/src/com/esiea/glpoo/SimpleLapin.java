package com.esiea.glpoo;


/** 
 * Represente un lapin standard.
 */
public class SimpleLapin implements Lapin {

	/**
	 *  Attributs
	 */
	private static final long serialVersionUID = -1225454238084424608L;
	private String position;
	private String orientation;
	private String sequences;
	private String nom;
	private int score;
	private int indexSequenceInachieved;
	private boolean sequenceInachieved;
	private boolean _sequenceAchieved;

	/** 
	 * Constructeurs
	 */
	public SimpleLapin()  
	{ }

	public SimpleLapin(final String nom) 
	{ 
		this.nom = nom;
		this.score = 0;
		this.indexSequenceInachieved = 0;
		this.sequenceInachieved = false;
		this._sequenceAchieved = false;
	}

	public SimpleLapin(String _nom, String _orientation, String _position, String _sequences) 
	{
		this(_nom);
		this.orientation = _orientation;
		this.position = _position;
		this.sequences = _sequences;
	}

	/** 
	 * Permet de convertir un String[] en List<String> - non utilisé
	 * @param couleurs
	 * @return List<String>
	 *
	private static List<String> tabToList(String[] couleurs) 
	{
		List<String> couleurList = new ArrayList<String>();
		for (String couleur : couleurs) {
			couleurList.add(couleur);
		}
		return couleurList;
	}*/

	/** 
	 * Lors d'un affichage sur un objet, celui-ci retourne la chaine specifee
	 */
	@Override
	public String toString() { return "SimpleLapin [nom=" + nom + "]"; }

	// Note : J'ai genere les getters/setters a l'aide d'Eclipse. Bouton droit puis "Sources" puis "Generate getters and setters".

	/**
	 * Getters
	 */
	public String getNom() { return this.nom; }
	public String getOrientation() { return this.orientation; }
	public String getSequences() { return this.sequences; }
	public String getPosition() { return this.position; }
	public int getScore() { return this.score; }
	public int getIndexSequenceInachieved() { return this.indexSequenceInachieved; }
	public boolean isSequenceFinished() { return this.sequenceInachieved; }
	public boolean isSequenceAchieved() { return this._sequenceAchieved; }

	/**
	 * Setters
	 */
	public void setNom(String _nom) { this.nom = _nom; }
	public void setPosition(String _position) { this.position = _position; }
	public void setOrientation(String _orientation) { this.orientation = _orientation; }
	public void setSequences(String _sequences) { this.sequences = _sequences; }
	public void addCarottes() { this.score++; }
	public void setSequenceInachieved(int index) 
	{ 
		this.indexSequenceInachieved = index;
		this.sequenceInachieved = true;
	}
	public void sequenceAchieved() { this._sequenceAchieved = true; }

}