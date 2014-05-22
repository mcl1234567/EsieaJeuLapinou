package com.esiea.glpoo;

import java.io.File;
import java.util.List;

/**
 * Singleton
 */
public class LapinService {

	//private static final Logger LOGGER = Logger.getLogger(LapinService.class);
	private CsvLapinDao csvLapin;
	private static LapinService instance;	// Instance de la classe, pour le singleton.

	/**
	 * Constructeur prive.
	 */
	private LapinService() 
	{
		//LOGGER.debug("Constructeur");
		System.out.println("LapinService - Constructeur");

		csvLapin = new AdvancedLapinCsvDao();

		//csvLapin = new AdvancedCsvLapinDao();
	}

	/** 
	 * Singleton classique, synchro, avec creation sur demande.
	 * @return LapinService
	 */
	public static synchronized LapinService getInstance() 
	{
		if (instance == null) instance = new LapinService();

		return instance;
	}

	/**
	 * Permet de recuperer tous les lapins
	 * @param fileName
	 * @return List<Lapin>
	 */
	public List<Lapin> findAllLapins(final String fileName) 
	{
		System.out.println("LapinService - findAllLapins");
		File file = new File(fileName);
		csvLapin.initLapin(file);
		return csvLapin.findAllLapins();
	}

}
