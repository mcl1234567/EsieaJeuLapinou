package com.esiea.glpoo;

import java.io.Serializable;

/**
 *  Cette interface repr�sente un lapin.
 */
public interface Lapin extends Serializable {
	// Note : Les methodes d'une interfaces sont automatiquement "public".

	String getNom();
	int getScore();
}