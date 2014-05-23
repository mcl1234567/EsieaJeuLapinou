package com.esiea.glpoo;

/**
 * Lorsqu'un lapin rencontre un autre lapin il faut gérer la fin des séquences du lapin en pause avec la mémorisation codée.
 * Créer le menu d'accueil. (samir)
 * Créer le tableau d'affichage des scores pour chaque lapin.
 * Implémenter les LOGGER ?
 * Déplacer les lapins simultanément
 */

public class Principale {

	//private static final Logger LOGGER = Logger.getLogger(Principale.class);

	static ModeleDynamique modele;
	//private static String[][] jardin = {{"1 carotte", "1 rocher", "1 lapin"}, {"1 lapin", "1 rocher", ""}, {"", "", "2 carottes"}};
	private static boolean a = false;
	private static boolean t = false;
	// Calcul durée action
	public static final int _TEMPS_ = 1000;
	public static int scalevitesseJeu = 1;
	public static int vitesseJeu = 1000;

	/**
	 * Main thread
	 * @param args
	 */
	public static void main(String[] args) 
	{
		//LOGGER.debug("Main : Debut");

		// columns : y ; rows : x
		int x = 3, y = 3;

		modele = new ModeleDynamique();
		// Récupération du csv et ajout des elements (lapins)
		modele.init();

		Vue jf = new Vue();
		jf.setVisible(true);

		// Joue une partie et affiche le nouveau terrain
		Calcul c = new Calcul();

		String type = "";
		// Récupération du type si non vide

		// Analyse du terrain
		// Lignes
		for (int i = 0; i < y; i++) {
			// Colonnes
			for (int j = 0; j < x; j++) {
				// Nombre carottes restantes dans cette case
				int nbCarottes = 0;
				if(modele.getJardin().getMatrice()[i][j].length() > 1) 
					type = modele.getJardin().getMatrice()[i][j].substring(2, modele.getJardin().getMatrice()[i][j].length());

				// Lapin repéré dans le jardin
				if(type.equalsIgnoreCase("lapin")) {
					// Pour tous les lapins
					for (int k = 0; k < modele.getLapins().size(); k++) {
						int caseY = i + 1;		// lignes
						int caseX = j + 1;		// colonnes

						// Lapin position = jardin position actuelle
						if(caseY == Integer.parseInt(modele.getLapins().get(k).getPosition().substring(0, 1)) 
						&& caseX == Integer.parseInt(modele.getLapins().get(k).getPosition().substring(2, 3))
						&& !modele.getLapins().get(k).isSequenceAchieved()) {

							// Bunny versus bunny.
							boolean bunnySpotted = false;

							// Pour chaque mouvement du lapin.. ( avancé ou tourné de 90° -> 1 seconde, vitesse modifiable )
							for (int l = 0; l<modele.getLapins().get(k).getSequences().length(); l++) {
								// On s'en va, ce n'est plus intéressant ( un lapin est trop innocent pour en manger un autre )
								if (bunnySpotted) break;
	
								// Calculs (c)
								// indice_i, indice_j, length_x, length_y, ... type(: rocher, lapin), indice_k, indice_l
								c.deplacementLapins(i, j, x, y, nbCarottes, modele, modele.getJardin().getMatrice(), type, bunnySpotted, k, l);

								// Regénération du terrain ( affichage IHM )
								jf.generationTerrain(x, y);

								// Lorsque toutes les actions sont finies, on envoie un sinal de fin ( mise en pause du lapin ) 
								if(l == modele.getLapins().get(k).getSequences().length()-1)
									modele.getLapins().get(k).sequenceAchieved();
							}
						}
					} // pour tous les lapins
				} // compare type lapin
			} // pour toutes les lignes
		} // pour toutes les colonnes

		// Après la fin d'une partie : accès disponible aux résultats des lapins
		jf.finDePartie();
	}

	/**
	 * Getters
	 */
	public static boolean getA() { return a; }
	public static boolean getT() { return t; }
	public static String[][] getJardin() { return modele.getJardin().getMatrice(); }
	public static ModeleDynamique getModeleD() { return modele; }
	public static int getScaleVitesseJeu() { return scalevitesseJeu; }
	public static int getVitesseJeu() { return vitesseJeu; }

}
