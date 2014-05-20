package com.esiea.glpoo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class AdvancedCsvLapinDao implements CsvLapinDao {
	/**
	 * Attributs
	 */
    private static final Logger LOGGER = Logger.getLogger(AdvancedCsvLapinDao.class);
    private File file;
    private List<Lapin> lapins;
    private final static String SEPARATOR = ";";
    private List<String> entetes;
    private Map<String, Lapin> lapinMapByNom;
    protected CsvLapinDao dao;

    /**
     * Permet de selectionner un fichier CSV
     */
    @Override
    public void init(File file) 
    {
        //LOGGER.debug("init");
    	System.out.println("AdvancedCsvLapinDao - init");

        this.file = file;

        // On relance une lecture a chaque initialisation, ce qui permet de changer de fichier, ou de recharger ledit fichier.
        reloadLapins();
    }

    /**
     * Permet de filtrer les caracteres dans chaque ligne
     * @return List<String>
     * @throws Exception
     */
    private List<String> getLignesFromFile() throws Exception 
	{
        //LOGGER.debug("getLignesFromFile");
    	System.out.println("AdvancedCsvLapinDao - getLignesFromFile");

        final List<String> lignes = new ArrayList<String>();

        final FileReader fr = new FileReader(file);
        final BufferedReader br = new BufferedReader(fr);

        // Recuperation et filtrage des lignes vides ou commentaires
        for (String ligne = br.readLine(); ligne != null; ligne = br.readLine()) {
            // Suppression des espaces en trop
            ligne = ligne.trim();

            // Filtre des lignes vides
            if(ligne.isEmpty()) {
                continue;
            }

            // Filtre des lignes de commentaire
            if(ligne.startsWith("#")) {
                continue;
            }

            lignes.add(ligne);
        }

        br.close();
        fr.close();

        return lignes;
    }

	/** 
	 * Analyse de chaque ligne
	 * @param ligne
	 * @return Lapin
	 * @throws Exception
	 */
	private Lapin transformLigneToLapin(final String ligne) throws Exception 
	{
       final SimpleLapin lapin = new SimpleLapin();
       final String separator = ";";

       final String[] values = ligne.split(separator);

       System.out.println("ligne : " + ligne);

       // init noms
       lapin.setNom(values[0]);       
       //lapin.setNomComplet(values[1]);

       // init sexe
       final String tempSexe = values[2];
       //final Sexe sexe = Sexe.valueOfByCode(new Integer(tempSexe));
       //chien.setSexe(sexe);

       // init race
       final String tempRace = values[3];
       //final RaceDeChien race = RaceDeChien.valueOfByCode(tempRace);
       //chien.setRace(race);

       // init couleur
       final ArrayList<String> couleursList = new ArrayList<String>();
       final String[] couleurs = values[4].split(",");
       for (String couleur : couleurs) couleursList.add(couleur);
       //chien.setCouleurs(couleursList);

       // init poids
       Double poids = Double.parseDouble(values[5].replace(',', '.'));
       //chien.setPoids(poids);

       return lapin;
    }

    /** 
     * Chargement des lapins via CSV
     */
    private void reloadLapins() 
    {
        //LOGGER.debug("reloadLapins");
    	System.out.println("AdvancedCsvLapinDao - reloadLapins");

        if (file == null) 
            throw new IllegalStateException("Le fichier est nul...");

        try {
        	// Recuperation de chaque ligne
            final List<String> lignes = getLignesFromFile();

            lapins = new ArrayList<Lapin>(lignes.size());
            lapinMapByNom = new HashMap<String, Lapin>(lignes.size());
        	// Recuperation des lapins
            for (String ligne : lignes) {
                final Lapin lapin = transformLigneToLapin(ligne);
                lapins.add(lapin);
            }

        } catch (Exception e) {
            //LOGGER.error("Une erreur s'est produite...", e);
        	System.err.println("Une erreur s'est produite...");
        }
    }

    /**
     * Permet de recuperer tous les lapins
     */
    @Override
    public List<Lapin> findAllLapins() 
    {
        //LOGGER.debug("findAllLapins");
        System.out.println("AdvancedCsvLapinDao - findAllLapins");

        if (lapins == null) 
        	throw new IllegalStateException("La liste n a pas encore ete initialisee...");

        return lapins;
    }

    /**
     * Getter
     */
    public File getFile() { return file; }

    /**
     * Permet de recuperer un lapin avec son nom
     */
    @Override
    public Lapin findLapinByNom(final String nom) 
    {
        if(nom == null || nom.isEmpty()) 
            throw new IllegalArgumentException("Le nom ne peut pas etre vide.");

        if (lapins == null) 
            throw new IllegalStateException("La liste n'a pas encore ete initialisee...");

        for(Lapin lapin : lapins) {
            if(nom.trim().equalsIgnoreCase(lapin.getNom())) {
                return lapin;
            }
        }

        // Si pas trouve...
        return null;
    }

}