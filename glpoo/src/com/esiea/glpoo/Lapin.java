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

	int getScore();	// Utile ?
	
}