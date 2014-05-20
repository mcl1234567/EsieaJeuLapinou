package com.esiea.glpoo;

import java.util.ArrayList;
import java.util.List;
import fr.ybonnel.csvengine.annotation.CsvColumn;
import fr.ybonnel.csvengine.annotation.CsvFile;

/** 
 * Represente un lapin standard.
 */
public class SimpleLapin implements Lapin {

	/**
	 *  serial Version UID
	 */
	private static final long serialVersionUID = -1225454238084424608L;

	//private String nbCarottesMangees;
	private int score;

	private String position;
	private String orientation;
	private String sequences;
	private String nom;

	/** 
	 * Constructeurs
	 */
	public SimpleLapin()  
	{ }

	public SimpleLapin(final String nom) 
	{ this.nom = nom; }

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
	 * @return
	 */
	private static List<String> tabToList(String[] couleurs) 
	{
		List<String> couleurList = new ArrayList<String>();
		for (String couleur : couleurs) {
			couleurList.add(couleur);
		}
		return couleurList;
	}

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

	/**
	 * Setters
	 * @param nom
	 */
	public void setNom(String nom) { this.nom = nom;  System.out.println("ezfvd: " + nom);}
	public void setPosition(String position) { this.position = position; }
	public void setOrientation(String orientation) { this.orientation = orientation; }
	public void setSequences(String sequences) { this.sequences = sequences; }

	public void setScore(int score) { this.score = score; }
}