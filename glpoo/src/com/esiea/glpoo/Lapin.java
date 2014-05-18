package com.esiea.glpoo;

import java.io.Serializable;

/**
 *  Cette interface représente un lapin.
 */
public interface Lapin extends Serializable {
	// Note : Les methodes d'une interfaces sont automatiquement "public".

	String getNom();
	int getScore();
}