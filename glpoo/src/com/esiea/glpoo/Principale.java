package com.esiea.glpoo;

import org.apache.log4j.Logger;

public class Principale {

	private static final Logger LOGGER = Logger.getLogger(Principale.class);

	/**
	 * Main thread
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//LOGGER.debug("Main : Debut");

		Vue jf = new Vue();
		jf.setVisible(true);

		// Joue une partie et affiche le nouveau terrain
		jf.calculate();

		//LOGGER.debug("Main : FIN");
	}

}