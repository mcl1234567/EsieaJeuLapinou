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
	public void init(File file);

	/** 
	 * Gets the CSV file used.
	 * @return 
	 */
	public File getFile();

	/** 
	 * Gets les entetes des colonnes. 
	 * @return 	 
	 */
	public List<String> getEntetes();
}