package com.esiea.glpoo;

import java.io.File;
import java.util.ArrayList;

/**
 * DAO pour la gestion du jardin en CSV.
 */
public interface CsvJardinDao extends JardinDao {

	/**
	 * Initialisation du DAO.
	 * @param file 
	 */
	void initJardin(File file, ArrayList<Lapin> lapins);

	/** 
	 * Gets the CSV file used.
	 * @return 
	 */
	File getFile();

	Jardin findAllJardin();
}