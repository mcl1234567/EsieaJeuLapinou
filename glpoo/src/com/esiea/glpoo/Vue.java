package com.esiea.glpoo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

/**
 * Lorsqu'un lapin rencontre un autre lapin il faut gérer la fin des séquences du lapin en pause avec la mémorisation codée.
 * Créer le menu d'accueil.
 * Créer le tableau d'affichage des scores pour chaque lapin.
 * @author morvan
 *
 */

public class Vue extends JFrame {

	private static final int WIDTH_WINDOWS = 800;
	private static final int HEIGHT_WINDOWS = 650;
	private static final Logger LOGGER = Logger.getLogger(Vue.class);
	// Test d'une image
	public static final String URL_IMAGE = "resources/allemagne.jpg";

	// Panel de la home (menu, accueil)
	final JPanel startPanel;
	// Panel du jeu
	final JPanel jeuPanel;
	// Panel des résultats
	final JPanel resPanel;

	// Tests statiques
	//final JTable tableau;
	public String[][] jardin = {{"1 carotte", "1 rocher", "1 lapin"}, {"1 lapin", "1 rocher", ""}, {"", "", "2 carottes"}};
	public String[] nomLapins = {"Bunny1", "Bunny2"};
	public String[] orientationLapins = {"N", "E"};
	public String[] sequenceLapins = {"AADADAGA", "AADADAGA"};
	public String[] coordonneesLapins = {"0-2", "1-1"};
	public int[] nbCarottesMangees;

	public ArrayList<Integer> memorizedSequences;

	// pas utile
	public ArrayList<JLabel> labels;

	public JLabel labelTest; // test

	public Vue() 
	{
		super();
		//LOGGER.debug("Vue constructeur");
		
		labels = new ArrayList<JLabel>();
		memorizedSequences = new ArrayList<Integer>();

		setTitle("Lapinou");
		setPreferredSize(new Dimension(WIDTH_WINDOWS, HEIGHT_WINDOWS));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	// 2 pages ( home, jeu de lapins )
		startPanel = new JPanel();
		jeuPanel = new JPanel();
		resPanel = new JPanel();

	// Init. des panels :
		// Config. du panel start
		startPanel.setLayout(null);
		startPanel.setVisible(false);

		// Config. du panel jeu
		jeuPanel.setBounds(0, 0, WIDTH_WINDOWS, HEIGHT_WINDOWS);
		jeuPanel.setLayout(null);
		jeuPanel.setVisible(true);
		this.add(jeuPanel);
		
		// Config. bouton -> renvoie vers les résultats
		JButton boutonResultats = new JButton();
		boutonResultats.setBounds(10, 10, 100, 100);
		boutonResultats.setVisible(true); // utile?
		jeuPanel.add(boutonResultats);
		boutonResultats.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("\nAffichage des résultats\n----------------------\n");
				jeuPanel.setVisible(false);
				resPanel.setVisible(true);				
			}
		});

		// Ajouts d'éléments graphiques à la frame par défaut
		//getContentPane().add(start);
		//getContentPane().add(jeu);

		// columns : y ; rows : x
		int x = 3, y = 3;

		// affichage initial
		generationTerrain(x, y, 1);

		// calculs
		deplacementLapins(x, y, 2);

		// affichage final
		generationTerrain(x, y, 3);
		
		generationResultats();

		/*String[][] cases = {{"1", "0"}, {"0", "2"}};
		int nbColonnes = cases[0].length;
		int nbLignes = cases.length;

		creationLegende(nbColonnes, nbLignes);

		tableau = new JTable(cases, cases);
		jeu.add(tableau);
		tableau.setVisible(true);
		tableau.setBounds(0, 0, 100, 100);*/

		// Bouton de lancement du jeu
		//start.add(new JButton(new lancement()));

		pack();
	}
	
	public String[] creationLegende(int nbColonnes, int nbLignes) 
	{	
		String[] colonnes = null;
		String[] lignes = null;
	
		for (int i = 0; i < nbColonnes; i++) {
			int val = i + 1;
			colonnes[i] = String.valueOf(val);		
		}
		for (int i = 0; i < nbLignes; i++) {
			int val = i + 1;
			lignes[i] = String.valueOf(val);		
		}
		return colonnes;
	}

	public void generationTerrain(int x, int y, int indice) 
	{
		System.out.println("generationTerrain");
		jeuPanel.removeAll();
		labelTest = new JLabel("OKKK"+String.valueOf(indice));
		labelTest.setBounds(50, 0, 100, 100);
		jeuPanel.add(labelTest);

		int base_x = 100;
		int base_y = 100;
		int taille = 100;
		int position_x = base_x;
		int position_y = base_x;
		String type1 = "carotte";
		String type2 = "carottes";
		String type3 = "rocher";
		String type4 = "lapin";
		int legendePosition = 20;
		Font font = new Font("Arial", Font.BOLD, 20);

		// Analyse du jardin // Pour toutes les colonnes..
		for (int i = 0; i < y; i++) {
			if(i > 0) position_y += base_y + 10;

			// Légende colonne
			JLabel colonneLabel = new JLabel(String.valueOf(i+1), SwingConstants.CENTER);
			colonneLabel.setBounds(legendePosition, position_y, taille, taille);
			colonneLabel.setFont(font);
			jeuPanel.add(colonneLabel);

			// Pour toutes les lignes..
			for (int j=0; j<x; j++) {
				if(j > 0) position_x += base_x + 10;
				int nbCarottes = 0;
				String type = "";
				JLabel caseLabel = new JLabel("", SwingConstants.CENTER);
				caseLabel.setBounds(position_x, position_y, taille, taille);
				caseLabel.setOpaque(true);

				// Légende ligne
				JLabel ligneLabel = new JLabel((String.valueOf(j+1)), SwingConstants.CENTER);
				ligneLabel.setBounds(position_x, legendePosition, taille, taille);
				ligneLabel.setFont(font);
				jeuPanel.add(ligneLabel);

				// Récupération du type si pas vide
				if(jardin[i][j].length() > 1) type = jardin[i][j].substring(2, jardin[i][j].length());				

				// Emplacement d'une carotte
				if(type.length() == type1.length() || type.length() == type2.length()) {
					nbCarottes = Integer.parseInt(jardin[i][j].substring(0, 1));
					caseLabel.setText(String.valueOf(nbCarottes));
					caseLabel.setBackground(Color.ORANGE);
				}
				// Emplacement d'un rocher
				else if(type.length() == type3.length()) {
					caseLabel.setBackground(Color.WHITE);
					caseLabel.setText("X");
					caseLabel.setFont(font);
				}
				// Emplacement d'un lapin
				else if(type.length() == type4.length()) {
					caseLabel.setBackground(Color.BLUE);
					caseLabel.setText("lapin");
				}

				// Intégration de l'image et création du label
				ImageIcon imgThisImg = new ImageIcon(URL_IMAGE);
				Image img_ = imgThisImg.getImage();  
				Image newimg = img_.getScaledInstance(base_x, base_y, java.awt.Image.SCALE_SMOOTH);  
				ImageIcon newIcon = new ImageIcon(newimg);

				//caseLabel.setIcon(newIcon);

				//caseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
				jeuPanel.add(caseLabel);
				labels.add(caseLabel);
			}
			position_x = base_x;
		}
	}

	//
	public void deplacementLapins(int x, int y, int indice) 
	{
		System.out.println("deplacementLapins");
		labelTest = new JLabel("OKKK"+String.valueOf(indice));
		labelTest.setBounds(50, 0, 100, 100);
		jeuPanel.add(labelTest);
		
		int base_x = 100;
		int base_y = 100;
		int taille = 100;
		int position_x = base_x;
		int position_y = base_x;
		String type1 = "carotte";
		String type2 = "carottes";
		String type3 = "rocher";
		String type4 = "lapin";
		int legendePosition = 20;
		Font font = new Font("Arial", Font.BOLD, 20);

		// Modification du jardin
		// Pour toutes les colonnes..
		for (int i = 0; i < y; i++) {
			if(i > 0) position_y += base_y + 10;

			// Pour toutes les lignes..
			for (int j = 0; j < x; j++) {
				if(j > 0) position_x += base_x + 10;

				int nbCarottes = 0;
				String type = "";
				JLabel caseLabel = new JLabel("", SwingConstants.CENTER);
				caseLabel.setBounds(position_x, position_y, taille, taille);
				caseLabel.setOpaque(true);

				// Récupération du type si pas vide
				if(jardin[i][j].length() > 1) type = jardin[i][j].substring(2, jardin[i][j].length());				

				// Smoothie carotte
				if(type.length() == type1.length() || type.length() == type2.length()) {
					nbCarottes = Integer.parseInt(jardin[i][j].substring(0, 1));
					caseLabel.setText(String.valueOf(nbCarottes));
					caseLabel.setBackground(Color.ORANGE);
				}
				// Rocher Suchard 
				else if(type.length() == type3.length()) {
					caseLabel.setBackground(Color.WHITE);
					caseLabel.setText("X");
					caseLabel.setFont(font);
				}
				// Un lapin-robot a été repéré
				else if(type.length() == type4.length()) {
					caseLabel.setBackground(Color.BLUE);
					caseLabel.setText("lapin");

					// Pour tous les lapins-robot..
					for (int k = 0; k < nomLapins.length; k++) {
						int caseX = j + 1, caseY = i + 1;
						
						// Modification du jardin en cours.. le lapin attaque le chasseur..
						if(caseX == Integer.parseInt(coordonneesLapins[k].substring(2, 3)) 		
						&& caseY == Integer.parseInt(coordonneesLapins[k].substring(0, 1))
						&& caseX == Integer.parseInt(jardin[i][j].substring(2, 3))
						&& caseY == Integer.parseInt(jardin[i][j].substring(2, 3))) {

							// Bunny versus bunny.
							boolean bunnysSpotted = false;

							// Pour chaque mouvements du lapin robot.. ( avancé ou faire une rotation de 90° )
							for (int l = 0; l < sequenceLapins[k].length(); l++) {
								// On s'en va, ce n'est plus intéressant ( un lapin est trop innocent pour en manger un autre )
								if (bunnysSpotted) break;

								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								char mouvement = sequenceLapins[k].charAt(l);
								switch(mouvement) {
									case 'A': {
										// -------------  NORD : i - 1
										if(orientationLapins[k].charAt(0) == 'N') {
											// Récupération du type si case pas vide
											if(jardin[i-1][j].length() > 1) {
												type = jardin[i-1][j].substring(2, jardin[i-1][j].length());

												// Inévitable..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i-1][j].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel
													jardin[i-1][j] = String.valueOf(nbCarottes-1) + jardin[i-1][j].substring(1, jardin[i-1][j].length());
													nbCarottesMangees[k]++;

													try {
														Thread.sleep(1000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												// Rocher Suchard 
												else if(type.length() == type3.length()) {
													break;
												}
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													// On mémorise la séquence pour y revenir après..
													memorizedSequences.add(l);
													bunnysSpotted = true;
													break;
												}												
											} 
											// Case vide pour le lapin
											else {
												jardin[i-1][j] = "lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										} 
										// -------------  OUEST : j - 1
										else if(orientationLapins[k].charAt(0) == 'O') {
											// Récupération du type si case pas vide
											if(jardin[i][j-1].length() > 1) {
												type = jardin[i][j-1].substring(2, jardin[i][j-1].length());

												// Inévitable..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i][j-1].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel
													jardin[i][j-1] = String.valueOf(nbCarottes-1) + jardin[i][j-1].substring(1, jardin[i][j-1].length());

													try {
														Thread.sleep(1000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												// Rocher Suchard 
												else if(type.length() == type3.length()) break;
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													memorizedSequences.add(l);
													break;
												}
											} 
											// Case vide pour le lapin
											else {
												jardin[i][j-1] = "lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										}
										
										// ------------- EST : j + 1
										else if(orientationLapins[k].charAt(0) == 'E') {
											// Récupération du type si case pas vide
											if(jardin[i][j+1].length() > 1) {
												type = jardin[i][j+1].substring(2, jardin[i][j+1].length());

												// Inévitable..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i][j+1].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel
													jardin[i][j+1] = String.valueOf(nbCarottes-1) + jardin[i][j+1].substring(1, jardin[i][j+1].length());

													try {
														Thread.sleep(1000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												// Rocher Suchard 
												else if(type.length() == type3.length()) break;
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													memorizedSequences.add(l);
													break;
												}
											} 
											// Case vide pour le lapin
											else {
												jardin[i][j+1] = "lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										}
										// -------------  SUD : i + 1
										else if(orientationLapins[k].charAt(0) == 'S') {
											// Récupération du type si case pas vide
											if(jardin[i+1][j].length() > 1) {
												type = jardin[i+1][j].substring(2, jardin[i+1][j].length());

												// Inévitable..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i+1][j].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel
													jardin[i+1][j] = String.valueOf(nbCarottes-1) + jardin[i+1][j].substring(1, jardin[i+1][j].length());

													try {
														Thread.sleep(1000);
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
												// Rocher Suchard 
												else if(type.length() == type3.length()) break;
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													memorizedSequences.add(l);
													break;
												}
											} 
											// Case vide pour le lapin
											else {
												jardin[i+1][j] = "lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										}
									} break;

									// Rotation gauche :
									case 'G': {
										char direction = orientationLapins[k].charAt(0);
										switch (direction) {
											case 'N': orientationLapins[k] = "O"; break;
											case 'O': orientationLapins[k] = "S"; break;
											case 'E': orientationLapins[k] = "N"; break;
											case 'S': orientationLapins[k] = "E"; break;											
										}
									}
									
									// Rotation droite :
									case 'D': {
										char direction = orientationLapins[k].charAt(0);
										switch (direction) {
											case 'N': orientationLapins[k] = "E"; break;
											case 'O': orientationLapins[k] = "N"; break;
											case 'E': orientationLapins[k] = "S"; break;
											case 'S': orientationLapins[k] = "O"; break;											
										}
									}
								}
							}

						}
					}
				}

				// Intégration de l'image et création du label
				ImageIcon imgThisImg = new ImageIcon(URL_IMAGE);
				Image img_ = imgThisImg.getImage();  
				Image newimg = img_.getScaledInstance(base_x, base_y, java.awt.Image.SCALE_SMOOTH);  
				ImageIcon newIcon = new ImageIcon(newimg);

				//caseLabel.setIcon(newIcon);

				//caseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
				jeuPanel.add(caseLabel);
				labels.add(caseLabel);
			}
			position_x = base_x;
		}
	}
	
	// Génération des résultats des lapins ( nombre de carottes mangées )
	void generationResultats() 
	{
		String[][] resLapins = null;

		// init
		for (int i = 0; i < nomLapins.length; i++) {			
			for (int j = 0; j < resLapins.length; j++) {
				resLapins[i][j] = "";
			}			
		}

		// Attribution
		for (int i = 0; i < nomLapins.length; i++) {
			//Lapin lapin = new Lapin();
			// rang
			resLapins[i][0] = "1";
			// nom
			resLapins[i][1] = nomLapins[i];
			// score
			resLapins[i][2] = String.valueOf(nbCarottesMangees[i]);
		}

		String[] entetes = { "Rang", "Nom lapin", "Score" };

		JTable tableau = new JTable(resLapins, entetes);
		//JTable test = new 
		resPanel.add(tableau);
	}

}