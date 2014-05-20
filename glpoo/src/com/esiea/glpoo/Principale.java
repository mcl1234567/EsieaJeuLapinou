package com.esiea.glpoo;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

public class Principale {
	private static final Logger LOGGER = Logger.getLogger(Principale.class);

	public static void main(String[] args) 
	{
		//LOGGER.debug("Main : Debut");
		System.out.println("DEBUT");
		System.out.println("Loading..");

		doFindChiensAdv();
		Vue jf = new Vue();
		jf.setVisible(true);

		// Joue une partie et affiche le nouveau terrain
		jf.calculate();

		System.out.println("FIN");
		//LOGGER.debug("Main : FIN");
	}

	/**
	 * Charge les CSV
	 */
	private static void doFindChiensAdv() 
	{
	    //LOGGER.debug("Recherche ...");
		System.out.println("Vue - Recherche ...");

	    final String fileName = "resources/lapin-1.csv";
	    final File file = new File(fileName);

	    final AdvancedCsvLapinDao dao = new AdvancedCsvLapinDao();

	    dao.init(file);

	    final List<Lapin> lapins = dao.findAllLapins(); // a debugger

	    final int nombreDeLapins = lapins.size();
	    //LOGGER.debug("Nombre de lapins : " + nombreDeLapins);
	    System.out.println("Nombre de lapins : " + nombreDeLapins);

	    printLapins(lapins);
	}

	/**
	 * Tests
	 */
	private static void printLapins(final List<Lapin> lapins) 
	{
		System.out.println("Vue - Liste des lapins");

		for (final Lapin lapin : lapins) {
			System.out.print(lapin + "\t");
			System.out.print(lapin.getOrientation() + "\t");
			System.out.print(lapin.getPosition() + "\t");
			System.out.print(lapin.getSequences() + "\n");
		}
	}

}