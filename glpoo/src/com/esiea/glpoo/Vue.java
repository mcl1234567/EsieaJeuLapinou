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
 * 
 * @author Morvan Calmel
 */

public class Vue extends JFrame {

	private static final int WIDTH_WINDOWS = 800;
	private static final int HEIGHT_WINDOWS = 650;
	private static final Logger LOGGER = Logger.getLogger(Vue.class);
	// Test d'une image
	public static final String URL_IMAGE = "resources/allemagne.jpg";

	private JTable tableau;
	private ModeleDynamique modele;

	// Panel de la home (menu, accueil)
	final JPanel startPanel;
	// Panel du jeu
	final JPanel jeuPanel;
	// Panel des résultats
	final JPanel resPanel;

	// Tests statiques
	//final JTable tableau;
	// Placement d'un chiffre avant pour pouvoir récupérer génériquement le nombre (1, 2, ..) et le type ( rocher, ..)
	public String[][] jardin = {{"1 carotte", "1 rocher", "1 lapin"}, {"1 lapin", "1 rocher", ""}, {"", "", "2 carottes"}};
	public String[] nomLapins = {"Bunny1", "Bunny2"};
	public String[] orientationLapins = {"N", "E"};
	public String[] sequenceLapins = {"AADADAGA", "AADADAGA"};
	// ligne - colonne de (1 à n)
	public String[] coordonneesLapins = {"1-3", "2-1"};
	public int[] nbCarottesMangees;  // utilisé?

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
		modele = new ModeleDynamique();
		tableau = new JTable(modele);

		setTitle("Lapinou");
		this.setVisible(true);
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
		
		// columns : y ; rows : x
		int x = 3, y = 3;

		// affichage initial
		generationTerrain(x, y, 1);

		// Ajouts d'éléments graphiques à la frame par défaut
		//getContentPane().add(start);
		//getContentPane().add(jeu);

		/*
		 * ???
		 * String[][] cases = {{"1", "0"}, {"0", "2"}};
		int nbColonnes = cases[0].length;
		int nbLignes = cases.length;

		creationLegende(nbColonnes, nbLignes);

		tableau = new JTable(cases, cases);
		jeu.add(tableau);
		tableau.setVisible(true);
		tableau.setBounds(0, 0, 100, 100);
		*/

		// Bouton de lancement du jeu
		//start.add(new JButton(new lancement()));

		pack();
	}
	
	// Après initialisation, on calcul
	public void calculate() 
	{
		// columns : y ; rows : x
		int x = 3, y = 3;

		// calculs
		deplacementLapins(x, y, 2);

		// Tests calculs
		launchTest();

		// affichage final
		generationTerrain(x, y, 3);
		
		/* generationResultats(); */

		
	}

	// Non utilisé
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

	// Permet de faire des tests de la matrice jardin contenant tous les éléments (affichage console)
	public void launchTest() 
	{
		System.out.println("Tests calculs\n");
		for (int i = 0; i < jardin.length; i++) {
			for (int j = 0; j < jardin[i].length; j++) {
				System.out.println("( " + String.valueOf(i) + " - " + String.valueOf(j) + " ) : " + jardin[i][j]);				
			}
			System.out.println("");
		}
	}

	// Génération du terrain de jeu
	public void generationTerrain(int x, int y, int indice) 
	{
		System.out.println("generationTerrain()\n");
		jeuPanel.removeAll();
		jeuPanel.setVisible(false);

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

		// Config. bouton resultats -> renvoie vers les résultats de la partie ( si elle est terminee )
		JButton boutonResultats = new JButton("Resultats");
		boutonResultats.setBounds(100, 10, 100, 20);
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

				// Récupération du type si pas vide // décalage de 2 pour pouvoir récupérer le nombre d'elements -> ^[2 ][carottes]$
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

		jeuPanel.setVisible(true);
	}

	//
	public void deplacementLapins(int x, int y, int indice) 
	{
		System.out.println("deplacementLapins()\n");
		//labelTest.setBounds(50, 0, 100, 100);
		//jeuPanel.add(labelTest);

		int base_x = 100;
		int base_y = 100;
		int taille = 100;
		int position_x = base_x;
		int position_y = base_x;
		String type1 = "carotte";
		String type2 = "carottes";
		String type3 = "rocher";
		String type4 = "lapin";
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

				// smoothie [Carotte]
				if(type.length() == type1.length() || type.length() == type2.length()) {
					nbCarottes = Integer.parseInt(jardin[i][j].substring(0, 1));
					caseLabel.setText(String.valueOf(nbCarottes));
					caseLabel.setBackground(Color.ORANGE);
				}
				// [Rocher]
				else if(type.length() == type3.length()) {
					caseLabel.setBackground(Color.WHITE);
					caseLabel.setText("X");
					caseLabel.setFont(font);
				}
				// [Lapin]-robot a été repéré
				else if(type.length() == type4.length()) {
					caseLabel.setBackground(Color.BLUE);
					caseLabel.setText("lapin");

					// Pour tous les lapins-robot..
					for (int k = 0; k < nomLapins.length; k++) {
						int caseX = j + 1;		// colonnes 
						int caseY = i + 1;		// lignes
						/*System.out.println("");
						// caseX : colonne j
						System.out.print("( i : "+String.valueOf(i)+" ) " + "( j : "+String.valueOf(j)+" ) " + "( k : "+String.valueOf(k)+" ) \n");
						System.out.print("caseX : " + String.valueOf(caseX) + "\t" + "coords : " + String.valueOf(coordonneesLapins[k].substring(0, 1)) + "\t" + "jardins : " + jardin[i][j].substring(2, 3));
						System.out.println("");
						// caseY : ligne i
						System.out.print("caseY : " + String.valueOf(caseY) + "\t" +  "coords : " + String.valueOf(coordonneesLapins[k].substring(2, 3)) + "\t" + "jardins : " + jardin[i][j].substring(0, 1));
						System.out.println("");System.out.println("");*/

						// Modification du jardin en cours.. le lapin attaque le chasseur..
						if(caseX == Integer.parseInt(coordonneesLapins[k].substring(2, 3)) 
						&& caseY == Integer.parseInt(coordonneesLapins[k].substring(0, 1))
						&& jardin[i][j].substring(2, jardin[i][j].length()).equalsIgnoreCase("lapin")) {
							// Bunny versus bunny.
							boolean bunnysSpotted = false;

							System.out.println(sequenceLapins[k]);

							// Pour chaque mouvements du lapin robot.. ( avancé ou faire une rotation de 90° )
							for (int l = 0; l < sequenceLapins[k].length(); l++) {
								// On s'en va, ce n'est plus intéressant ( un lapin est trop innocent pour en manger un autre )
								if (bunnysSpotted) break;

								try { Thread.sleep(1000); }
								catch (InterruptedException e) { e.printStackTrace(); }

								char mouvement = sequenceLapins[k].charAt(l);
								switch(mouvement) {
									case 'A': {
										System.out.print("Le lapin avance ");
										int decalage = 0;
										// -------------  NORD : i - 1
										if(orientationLapins[k].charAt(0) == 'N') {
											System.out.println("N");
											// Récupération du type si case pas vide
											decalage = i - 1;
											if(i == 0) break;
											if(jardin[decalage][j].length() > 1) {
												type = jardin[i-1][j].substring(2, jardin[i-1][j].length());

												// Inévitable.. le lapin mange la carotte..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i-1][j].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel
													System.out.println("Avant : " + jardin[decalage][j]);
													jardin[decalage][j] = String.valueOf(nbCarottes-1) + jardin[decalage][j].substring(1, jardin[decalage][j].length());
													System.out.println("Apres : " + jardin[decalage][j]);
													nbCarottesMangees[k]++;

													// Pause d'une seconde
													try { Thread.sleep(1000); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher
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
												jardin[decalage][j] = "1 lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										} 
										// -------------  OUEST : j - 1
										else if(orientationLapins[k].charAt(0) == 'O') {
											System.out.println("O");
											decalage = j - 1;
											if ( j == 0 ) break;
											// Récupération du type si case pas vide
											if(jardin[i][decalage].length() > 1) {
												type = jardin[i][decalage].substring(2, jardin[i][decalage].length());

												// Inévitable.. le lapin mange la carotte..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i][decalage].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel

													System.out.println("Avant : " + jardin[i][decalage]);
													jardin[i][decalage] = String.valueOf(nbCarottes-1) + jardin[i][decalage].substring(1, jardin[i][decalage].length());
													System.out.println("Avant : " + jardin[i][decalage]);

													// Pause d'une seconde
													try { Thread.sleep(1000); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher 
												else if(type.length() == type3.length()) break;
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													memorizedSequences.add(l);
													break;
												}
											} 
											// Case vide pour le lapin
											else {
												jardin[i][decalage] = "1 lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										}

										// ------------- EST : j + 1
										else if(orientationLapins[k].charAt(0) == 'E') {
											System.out.println("E");
											decalage = j + 1;
											if(decalage == x) break;
											// Récupération du type si case pas vide
											if(jardin[i][decalage].length() > 1) {
												type = jardin[i][decalage].substring(2, jardin[i][decalage].length());

												// Inévitable.. le lapin mange la carotte..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[i][decalage].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel

													System.out.println("Avant : " + jardin[i][decalage]);
													jardin[i][decalage] = String.valueOf(nbCarottes-1) + jardin[i][decalage].substring(1, jardin[i][decalage].length());
													System.out.println("Avant : " + jardin[i][decalage]);

													// Pause d'une seconde
													try { Thread.sleep(1000); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher
												else if(type.length() == type3.length()) break;
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													memorizedSequences.add(l);
													break;
												}
											}
											// Case vide pour le lapin
											else {
												jardin[i][decalage] = "1 lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										}
										// -------------  SUD : i + 1
										else if(orientationLapins[k].charAt(0) == 'S') {
											System.out.println("S");
											decalage = i + 1;
											if(decalage == y) break;
											// Récupération du type si case pas vide
											if(jardin[decalage][j].length() > 1) {
												type = jardin[decalage][j].substring(2, jardin[decalage][j].length());

												// Inévitable.. le lapin mange la carotte..
												if(type.length() == type1.length() || type.length() == type2.length()) {
													nbCarottes = Integer.parseInt(jardin[decalage][j].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide..
													// Réécriture du contenu du JLabel
													System.out.println("Avant : " + jardin[decalage][j]);
													jardin[decalage][j] = String.valueOf(nbCarottes-1) + jardin[decalage][j].substring(1, jardin[decalage][j].length());
													System.out.println("Avant : " + jardin[decalage][j]);

													// Pause d'une seconde
													try { Thread.sleep(1000); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher 
												else if(type.length() == type3.length()) break;
												// Un autre lapin a été repéré // Lapin en pause..
												else if(type.length() == type4.length()) {
													memorizedSequences.add(l);
													break;
												}
											}
											// Case vide pour le lapin
											else {
												jardin[decalage][j] = "1 lapin";
												jardin[i][j] = "";
												// Changement des JLabels à faire
											}
										}
									} break;

									// Rotation gauche :
									case 'G': {
										System.out.println("Le lapin tourne a gauche ");
										char direction = orientationLapins[k].charAt(0);
										switch (direction) {
											case 'N': orientationLapins[k] = "O"; break;
											case 'O': orientationLapins[k] = "S"; break;
											case 'E': orientationLapins[k] = "N"; break;
											case 'S': orientationLapins[k] = "E"; break;											
										}
									} break;

									// Rotation droite :
									case 'D': {
										System.out.println("Le lapin tourne a droite ");
										char direction = orientationLapins[k].charAt(0);
										switch (direction) {
											case 'N': orientationLapins[k] = "E"; break;
											case 'O': orientationLapins[k] = "N"; break;
											case 'E': orientationLapins[k] = "S"; break;
											case 'S': orientationLapins[k] = "O"; break;											
										}
									} break;
								} // fin switch mouvement / action
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
	
	// Non fonctionnel
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