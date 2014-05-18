package com.esiea.glpoo;

import fr.ybonnel.csvengine.annotation.CsvFile;

// Represente un lapin standard.
@CsvFile(separator = ";")
public class SimpleLapin implements Lapin {

	// serial Version UID
	private static final long serialVersionUID = -1225454238084424608L;
	private String nom;
	private String nbCarottesMangees;
	private int score;

	//@CsvColumn(value = "Nom complet", order = 1)
	//private String nomComplet;

	//@CsvColumn(value = "sexe", adapter = AdapterSexe.class, order = 2)
	//private Sexe sexe;

	//@CsvColumn(value = "race", adapter = AdapterRace.class, order = 3)
	//private RaceDeChien race;

	//@CsvColumn(value = "couleurs", adapter = AdapterCouleurs.class, order = 4)
	//private List<String> couleurs;

	//@CsvColumn(value = "poids", adapter = AdapterPoids.class, order = 5)
	//private Double poids;

	// Construit un lapin.
	public SimpleLapin() 
	{
		// rien...
	}

	public SimpleLapin(final String nom) 
	{
		this.nom = nom;
	}

	@Override
	public String toString() { return "SimpleLapin [nom=" + nom + "]"; }

	// Note : J'ai genere les getters/setters a l'aide d'Eclipse. Bouton droit puis "Sources" puis "Generate getters and setters".
	public String getNom() { return this.nom; }
	public int getScore() { return this.score; }

	public void setNom(String nom) { this.nom = nom; }
	public void setScore(int score) { this.score = score; }
}
