package com.esiea.glpoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utilisation du pattern DAO
 */
public class AdvancedJardinCsvDao implements CsvJardinDao {
	/**
	 * Attributs
	 */
    //private static final Logger LOGGER = Logger.getLogger(AdvancedCsvLapinDao.class);
    private File file;
    private Jardin jardin;
    private final static String SEPARATOR = " ";
    private List<String> entetes;
    private Map<String, Jardin> jardinMapByNom;
    protected CsvLapinDao dao;
    private ModeleDynamique modele;
    private boolean a;
    private boolean t;

    /**
     * Permet de selectionner un fichier CSV
     */
    @Override
    public void initJardin(File file, ArrayList<Lapin> lapins) 
    {
        //LOGGER.debug("init");
    	if(a) System.out.println("AdvancedJardinCsvDao - init");
   
    	a = Principale.getA();
    	t = Principale.getT();

        this.file = file;

        // On relance une lecture a chaque initialisation, ce qui permet de changer de fichier, ou de recharger ledit fichier.
        reloadJardin(lapins);
    }

    /**
     * Permet de filtrer les caracteres dans chaque ligne
     * @return List<String>
     * @throws Exception
     */
    private List<String> getLignesFromFile() throws Exception 
	{
        //LOGGER.debug("getLignesFromFile");
    	if(a) System.out.println("AdvancedJardinCsvDao - getLignesFromFile");

        final List<String> lignes = new ArrayList<String>();

        final FileReader fr = new FileReader(file);
        final BufferedReader br = new BufferedReader(fr);

        // Recuperation et filtrage des lignes vides ou commentaires
        for (String ligne = br.readLine(); ligne != null; ligne = br.readLine()) {

        	// Suppression des espaces en trop
            ligne = ligne.trim();

            // Filtre des lignes vides
            if(ligne.isEmpty()) continue;

            // Filtre des lignes de commentaire
            if(ligne.startsWith("#")) continue;

            lignes.add(ligne);
        }

        br.close();
        fr.close();

        return lignes;
    }

	/**
	 * Analyse de chaque ligne
	 * @param ligne
	 * @throws Exception
	 */
	private void transformLigneToJardin(final String ligne, Jardin jardin, ArrayList<Lapin> lapins) throws Exception 
	{
       final String[] values = ligne.split(SEPARATOR);

       /**
        * Ajout des valeurs à l'instance
        */
       char type = values[0].charAt(0);
       switch(type) {
       		case 'J': {
       			jardin.setTailleLignes(Integer.parseInt(values[1]));
       			jardin.setTailleColonnes(Integer.parseInt(values[2]));
       		} break;
       		case 'C': {
       			jardin.setPositionCarottes(values[1]);
       			jardin.setNombreCarottes(Integer.parseInt(values[2]));
       		} break;
       		case 'R': {
       			jardin.setPositionRocher(values[1]);
       		} break;
       }

       for (int i=0; i<lapins.size(); i++) {
    	   jardin.setLapin(lapins.get(i));
       }
    }

	/**
     * Chargement des lapins via CSV
     */
    private void reloadJardin(ArrayList<Lapin> lapins) 
    {
        //LOGGER.debug("reloadJardin");
    	if(a) System.out.println("AdvancedJardinCsvDao - reloadJardin");

        if (file == null) 
            throw new IllegalStateException("Le fichier est nul...");

        try {
        	// Recuperation de chaque ligne
            final List<String> lignes = getLignesFromFile();
            jardin = new Jardin();

        	// Recuperation du jardin
            for (String ligne : lignes) {
                transformLigneToJardin(ligne, jardin, lapins);
            }

        } catch (Exception e) {
            //LOGGER.error("Une erreur s'est produite...", e);
        	System.err.println("Une erreur s'est produite...\n");
        }
    }

    /**
     * Permet de recuperer le jardin
     */
    @Override
    public Jardin findAllJardin() 
    {
        //LOGGER.debug("findAll");
        if(a) System.out.println("AdvancedJardinCsvDao - findAllJardin\n");

        if (jardin == null) 
        	throw new IllegalStateException("Le jardin n a pas encore ete initialise...\n");

        return jardin;
    }

    /**
     * Getters
     */
    public File getFile() { return file; }
    public Map<String, Jardin> getJardinMapByNom() { return jardinMapByNom; }
	public List<String> getEntetes() { return entetes; }

	/**
	 * Setters
	 */
	public void setJardinMapByNom(Map<String, Jardin> _mapByNom) { this.jardinMapByNom = _mapByNom; }
	public void setEntetes(List<String> entetes) { this.entetes = entetes; }
}