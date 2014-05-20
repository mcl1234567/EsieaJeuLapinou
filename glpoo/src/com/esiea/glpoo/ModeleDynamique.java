package com.esiea.glpoo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;

public class ModeleDynamique extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(ModeleDynamique.class);

	private String[] entetes;
	private List<Lapin> lapins;
	final private static String LAPINS_FILE_NAME = "resources/lapin-1.csv";
	private LapinService lapinService = LapinService.getInstance();

	public ModeleDynamique() 
	{
		super();

		lapins = lapinService.findAllLapins(LAPINS_FILE_NAME);
		entetes = new String[] { "nom", "position", "orientation", "sequences", "score" };
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

		// Ordre : "Nom", orientation, sequences, positions
		switch (columnIndex) {
			case 0:	return lapin.getNom();
			case 1:	return lapin.getOrientation();
			case 2: return lapin.getSequences();
			case 3: return lapin.getPosition();
			default: throw new IllegalArgumentException("Le numero de colonne indique n'est pas valide.");
		}
	}

}