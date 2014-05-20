package com.esiea.glpoo;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

public class LapinService {

	private static final Logger LOGGER = Logger.getLogger(LapinService.class);

	private CsvLapinDao csvLapin;
	private static LapinService instance;	// Instance de la classe, pour le singleton.

	/**
	 * Constructeur prive.
	 */
	private LapinService() 
	{
		//LOGGER.debug("Constructeur");

		csvLapin = new AdvancedCsvLapinDao();
	}

	/** 
	 * Singleton classique, synchro, avec creation sur demande.
	 * @return ?
	 */
	public static synchronized LapinService getInstance() 
	{
		if (instance == null) 
			instance = new LapinService();

		return instance;
	}
	
	/**
	 * Permet de recuperer tous les lapins
	 * @param fileName
	 * @return List<Lapin>
	 */
	public List<Lapin> findAllLapins(final String fileName) 
	{
		final File file = new File(fileName);
		csvLapin.init(file);
		return csvLapin.findAllLapins();
	}
}