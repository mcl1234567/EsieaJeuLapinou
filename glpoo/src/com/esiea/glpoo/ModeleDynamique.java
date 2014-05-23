package com.esiea.glpoo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Le modèle contient les objets du jeu
 */

public class ModeleDynamique extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	//private static final Logger LOGGER = Logger.getLogger(ModeleDynamique.class);

	private LapinService lapinService = LapinService.getInstance();
	private JardinService jardinService = JardinService.getInstance();
	final private static String LAPINS_FILE_NAME = "resources/lapin-1.csv";
	final private static String JARDIN_FILE_NAME = "resources/jardin-1.csv";
	private String[] entetes;
	private List<Lapin> lapins;
	private ArrayList<Lapin> arrayLapins;
	private Jardin jardin;

	/**
	 * Constructeur pour le tableau d'affichage
	 */
	public ModeleDynamique() 
	{
		super();
		arrayLapins = new ArrayList<Lapin>();

		entetes = new String[] { "Nom", "Position", "Orientation", "Sequences", "Score" };
	}

	/**
	 * Initialisation
	 */
	public void init() 
	{
		// Entêtes du tableau d'affichage des résultats
		entetes = new String[] { "Nom", "Position", "Orientation", "Sequences", "Score" };
		lapins = lapinService.findAllLapins(LAPINS_FILE_NAME);

		for (int i = 0; i < lapins.size(); i++) {
			arrayLapins.add(lapins.get(i));
		}

		// init Jardin
		jardin = jardinService.findAllJardin(JARDIN_FILE_NAME, arrayLapins);
	}

	/**
	 * Ajout d'un lapin
	 * @param lapin
	 */
	public void ajouterLapin(final Lapin lapin) 
	{
		//LOGGER.debug("ajouterLapin");

		lapins.add(lapin);

		final int position = lapins.size() - 1;
		fireTableRowsInserted(position, position);
	}

	/**
	 * Suppression d'un lapin
	 * @param rowIndex
	 */
	public void supprimerLapin(final int rowIndex) 
	{
		//LOGGER.debug("supprimerLapin");

		lapins.remove(rowIndex);
		fireTableRowsDeleted(rowIndex, rowIndex);
	}

	/**
	 * Getters
	 */
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
			case 1: return lapin.getPosition();
			case 2:	return lapin.getOrientation();
			case 3: return lapin.getSequences();			
			case 4: return lapin.getScore();
			default: throw new IllegalArgumentException("Le numero de colonne indique n'est pas valide.");
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) 
	{
		switch (columnIndex) {
			case 0:
			case 1: return String.class;
				// case 4:
				// return List.class;
				//
				// case 5:
				// return Double.class;
			default: return Object.class;
		}
	}

	//public List<Lapin> getLapins() { return this.lapins; }
	public List<Lapin> getLapins() { return this.lapins; }
	public Jardin getJardin() { return this.jardin; }
	public ArrayList<Lapin> getArrayLapins() { return this.arrayLapins; }
}