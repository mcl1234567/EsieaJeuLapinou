package com.esiea.glpoo;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public abstract class AbstractCsvLapinDao implements CsvLapinDao {

	private static final Logger LOGGER = Logger.getLogger(AbstractCsvLapinDao.class);

	protected File file;
	protected List<Lapin> lapins;
	protected Map<String, Lapin> lapinMapByNom;
	protected List<String> entetes;
	protected abstract void reloadLapins();

	@Override
	public void init(File file) 
	{
		//LOGGER.debug("init");
		System.out.println("init");

		this.file = file;

		reloadLapins();
	}

	@Override
	public List<Lapin> findAllLapins() 
	{
		//LOGGER.debug("findAllChiens");

		if (lapins == null) 
			throw new IllegalStateException("La liste n'a pas encore ete initialisee...");

		return lapins;
	}

	@Override
	public Lapin findLapinByNom(final String nom) 
	{
		if (nom == null || nom.isEmpty()) 
			throw new IllegalArgumentException("Le nom ne peut pas etre vide.");

		if (lapins == null) 
			throw new IllegalStateException("La liste n'a pas encore ete initialisee...");

		return lapinMapByNom.get(nom);
	}

	@Override
	public File getFile() {	return file; }
	@Override
	public List<String> getEntetes() { return entetes; }
}