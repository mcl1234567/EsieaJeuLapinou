package com.esiea.glpoo;

import java.util.ArrayList;

public class Jardin {

	private int tailleLignes;
	private int tailleColonnes;
	private String[][] matriceJardin;	
	private ArrayList<String> positionsCarottes;
	private ArrayList<Integer> nombresCarottes;
	private ArrayList<String> positionsRocher;
	private ArrayList<String> positionsLapin;
	private ArrayList<Lapin> lapins;
	private boolean matriceSet;

	/**
	 * Constructeurs
	 */
	public Jardin() 
	{
		positionsCarottes = new ArrayList<String>();
		nombresCarottes = new ArrayList<Integer>();
		positionsRocher = new ArrayList<String>();
		positionsLapin = new ArrayList<String>();
		lapins = new ArrayList<Lapin>();
		matriceSet = false;
	}

	public Jardin(int _tailleL, int _tailleC) 
	{
		this();
		this.tailleColonnes = _tailleC;
		this.tailleLignes = _tailleL;
	}

	/**
	 * Initialisation du terrain
	 */
	public void initMatrice() 
	{
		System.out.println("initMatrice");
		System.out.println(String.valueOf(this.tailleLignes));
		System.out.println(String.valueOf(this.tailleColonnes));
		matriceJardin = new String[this.tailleLignes][this.tailleColonnes];
		matriceSet = true;
		int caseX = 0;
		int caseY = 0;
		
		System.out.println("test initmatrice1");
		
		// Pour toutes les lignes..
		for (int i = 0; i < matriceJardin.length; i++) {
			// Pour toutes les colonnes..
			for (int j = 0; j < matriceJardin.length; j++) {
				caseX = i + 1;
				caseY = j + 1;
				
				matriceJardin[i][j] = "";

				//System.out.println("init pos carotte");
				for (int k = 0; k < positionsCarottes.size(); k++) {
					if(Integer.parseInt(positionsCarottes.get(k).substring(0, 1)) == caseX
					&& Integer.parseInt(positionsCarottes.get(k).substring(2, 3)) == caseY) { 
						if(nombresCarottes.get(k) == 1) { 
							matriceJardin[i][j] = String.valueOf(nombresCarottes.get(k)) + " carotte";
						}
						else if(nombresCarottes.get(k) > 1) {  
							matriceJardin[i][j] = String.valueOf(nombresCarottes.get(k)) + " carottes";
						}
					}
				}

				//System.out.println("init pos rocher");
				for (int k = 0; k < positionsRocher.size(); k++) {
					if(Integer.parseInt(positionsRocher.get(k).substring(0, 1)) == caseX
					&& Integer.parseInt(positionsRocher.get(k).substring(2, 3)) == caseY) { 
						matriceJardin[i][j] = "1 rocher";
					}
				}

				//System.out.println("init pos lapin");
				for (int k = 0; k < positionsLapin.size(); k++) {
					if(Integer.parseInt(positionsLapin.get(k).substring(0, 1)) == caseX
					&& Integer.parseInt(positionsLapin.get(k).substring(2, 3)) == caseY) { 
						matriceJardin[i][j] = "1 lapin";
					}
				}
			} // Pour toutes les colonnes
		} // Pour toutes les lignes
	}

	public boolean isCaseUsed() 
	{ 
		if(matriceSet) {
			return true;
		}
		return false; 
	}

	public int getTailleColonnes() { return tailleColonnes; }
	public int getTailleLignes() { return tailleLignes; }
	public ArrayList<String> getPositionsCarotte() { return positionsCarottes; }
	public ArrayList<Integer> getNombresCarottes() { return nombresCarottes; }
	public ArrayList<String> getPositionsRocher() { return positionsRocher; }
	public ArrayList<String> getPositionsLapin() { return positionsLapin; }
	public String[][] getMatrice() { return this.matriceJardin; }

	public ArrayList<Lapin> getLapins() { return this.lapins; }

	public void setTailleColonnes(int tailleColonnes) {	this.tailleColonnes = tailleColonnes; }
	public void setTailleLignes(int tailleLignes) { this.tailleLignes = tailleLignes; }
	public void setPositionCarottes(String _pos) { this.positionsCarottes.add(_pos); }
	public void setNombreCarottes(int _nb) { this.nombresCarottes.add(_nb); }
	public void setPositionRocher(String _posR) { this.positionsRocher.add(_posR); }
	public void setPositionLapin(String _posL) { this.positionsLapin.add(_posL); }

	// Init les positions dans la matrice terrain de jeu
	public void setLapin(Lapin _lapin) 
	{
		this.lapins.add(_lapin);
		this.positionsLapin.add(_lapin.getPosition());
	}

}