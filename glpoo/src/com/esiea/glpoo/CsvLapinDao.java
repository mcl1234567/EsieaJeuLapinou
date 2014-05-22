package com.esiea.glpoo;

import java.io.File;
import java.util.List;

/**
 * DAO pour la gestion des lapins en CSV.
 */
public interface CsvLapinDao extends LapinDao {

	/** 
	 * Initialisation du DAO.
	 * @param file 
	 */
	void initLapin(File file);

	/**
	 * Gets the CSV file used.
	 * @return 
	 */
	File getFile();

	List<Lapin> findAllLapins();
}
