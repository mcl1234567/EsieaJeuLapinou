package com.esiea.glpoo;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

public class Principale {
	private static final Logger LOGGER = Logger.getLogger(Principale.class);

	public static void main(String[] args) 
	{
		//LOGGER.debug("Main : Debut");

		System.out.println("Loading..");
		JFrame jf = new Vue();
		jf.setVisible(true);

		//LOGGER.debug("Main : FIN");
	}
}