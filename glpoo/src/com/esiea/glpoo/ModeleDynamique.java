package com.esiea.glpoo;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.apache.log4j.Logger;

public class ModeleDynamique extends AbstractTableModel {

	private static final Logger LOGGER = Logger.getLogger(ModeleDynamique.class);
	private String[] entetes;
	private List<Lapin> lapins;
	final private static String LAPINS_FILE_NAME = "src/main/resources/chiens.csv";			// A MODIFIER
	private LapinService lapinService = LapinService.getInstance();

	public ModeleDynamique() 
	{
		super();

		lapins = lapinService.findAllLapins(LAPINS_FILE_NAME);
		entetes = new String[] { "Rang", "Nom lapin", "Score" };
	}

	@Override
	public String getColumnName(int columnIndex) { return entetes[columnIndex]; }
	@Override
	public int getRowCount() { return lapins.size(); }

	@Override
	public int getColumnCount() { return entetes.length; }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) 
	{
		final Lapin lapin = lapins.get(rowIndex);

		// Ordre : "Nom", 
		switch (columnIndex) {
			case 0:	return lapin.getNom();
			default: throw new IllegalArgumentException("Le numero de colonne indique n'est pas valide.");
		}
	}

}
