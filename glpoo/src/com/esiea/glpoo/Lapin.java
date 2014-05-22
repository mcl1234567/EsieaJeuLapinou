package com.esiea.glpoo;

import java.io.Serializable;

/**
 *  Cette interface représente un lapin.
 */
public interface Lapin extends Serializable {
	// Note : Les methodes d'une interfaces sont automatiquement "public".

	String getNom();
	String getOrientation();
	String getSequences();
	String getPosition();
	int getScore();
	int getIndexSequenceInachieved();
	boolean isSequenceAchieved();

	void setNom(String _nom);
	void setPosition(String _position);
	void setOrientation(String _orientation);
	void setSequences(String _sequences);
	void addCarottes();
	void setSequenceInachieved(int index);
	void sequenceAchieved();
}