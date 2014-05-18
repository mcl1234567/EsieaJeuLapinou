package com.esiea.glpoo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import fr.ybonnel.csvengine.CsvEngine;
import fr.ybonnel.csvengine.factory.AbstractCsvReader;
import fr.ybonnel.csvengine.factory.DefaultCsvManagerFactory;
import fr.ybonnel.csvengine.factory.OpenCsvReader;
import fr.ybonnel.csvengine.model.Error;
import fr.ybonnel.csvengine.model.Result;

public class EngineCsvLapinDao extends AbstractCsvLapinDao {

	private static final Logger LOGGER = Logger.getLogger(EngineCsvLapinDao.class);

	private void setEngineFactory(final CsvEngine engine) 
	{
		engine.setFactory(new DefaultCsvManagerFactory() {
			@Override
			public AbstractCsvReader createReaderCsv(Reader reader, char separator) {
				return new OpenCsvReader(reader, separator) {
					@Override
					public String[] readLine() throws IOException 
					{
						String[] nextLine = super.readLine();
						if (isLineAComment(nextLine)) {
							nextLine = readLine();
						}
						return nextLine;
					}

					private boolean isLineAComment(String[] line) 
					{
						return line != null && line.length > 0 && line[0].startsWith("#");
					}
				};
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void reloadLapins() 
	{
		//LOGGER.debug("reloadLapins");

		try {
			final CsvEngine engine = new CsvEngine(SimpleLapin.class);
			setEngineFactory(engine);

			final FileInputStream fis = new FileInputStream(file);

			final Result<SimpleLapin> resultat = engine.parseInputStream(fis, SimpleLapin.class);

			final List<? extends Lapin> bunnies = resultat.getObjects();
			lapins = (List<Lapin>) bunnies;
			lapinMapByNom = new HashMap<String, Lapin>(lapins.size());
			for (Lapin lapin : lapins) {
				//LOGGER.debug("[lapin] " + lapin);
				lapinMapByNom.put(lapin.getNom(), lapin);
			}

			List<Error> errors = resultat.getErrors();
			//LOGGER.debug(errors);

			entetes = engine.getColumnNames(SimpleLapin.class);

			//LOGGER.debug("[entetes] " + entetes);

		} catch (Exception e) {
			//LOGGER.error("Une erreur s'est produite...", e);
		}
	}

	@Override
	/**
	 * Return null
	 */
	public Lapin findLapinByNom(String nom) { return null; }

}