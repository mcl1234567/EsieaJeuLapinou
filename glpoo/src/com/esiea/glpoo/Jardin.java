package com.esiea.glpoo;

import java.util.ArrayList;

public class Jardin {

	private int tailleLignes;
	private int tailleColonnes;
	private String[][] matriceJardin;	
	private ArrayList<String> positionsCarottes;
	private ArrayList<Integer> nombresCarottes;
	private ArrayList<String> positionsRocher;
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
		matriceSet = false;
		lapins = new ArrayList<Lapin>();
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
		matriceJardin = new String[this.tailleLignes][this.tailleColonnes];
		matriceSet = true;
		int caseX = 0;
		int caseY = 0;

		for (int i = 0; i < matriceJardin.length; i++) {
			for (int j = 0; j < matriceJardin.length; j++) {
				caseX = i + 1;
				caseY = j + 1;

				for (int k = 0; k < positionsCarottes.size(); k++) {
					if(Integer.parseInt(positionsCarottes.get(k).substring(0, 1)) == caseX
					&& Integer.parseInt(positionsCarottes.get(k).substring(2, 3)) == caseY) 
						if(nombresCarottes.get(k) == 1) { 
							matriceJardin[i][j] = String.valueOf(nombresCarottes.get(k)) + " carotte";
						}
						else if(nombresCarottes.get(k) > 1) {  
							matriceJardin[i][j] = String.valueOf(nombresCarottes.get(k)) + " carottes";
						}
				}

				for (int k = 0; k < positionsRocher.size(); k++) {
					if(Integer.parseInt(positionsCarottes.get(k).substring(0, 1)) == caseX
					&& Integer.parseInt(positionsCarottes.get(k).substring(2, 3)) == caseY) 
						matriceJardin[i][j] = "rocher";
				}
			}
		}
	}

	public boolean isMatriceSet() 
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
	public String[][] getJardin() { return this.matriceJardin; }
	public ArrayList<Lapin> getLapins() { return this.lapins; }

	public void setTailleColonnes(int tailleColonnes) {	this.tailleColonnes = tailleColonnes; }
	public void setTailleLignes(int tailleLignes) { this.tailleLignes = tailleLignes; }
	public void setPositionCarottes(String _pos) { this.positionsCarottes.add(_pos); }
	public void setNombreCarottes(int _nb) { this.nombresCarottes.add(_nb); }
	public void setPositionRocher(String _pos) { this.positionsRocher.add(_pos); }
	public void setLapin(Lapin _lapin) { this.lapins.add(_lapin); }
}