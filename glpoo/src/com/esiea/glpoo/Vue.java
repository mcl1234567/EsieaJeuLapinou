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

public class Vue extends JFrame {

	/**
	 * Attributs
	 */
	private static final long serialVersionUID = 1L;
	//private static final Logger LOGGER = Logger.getLogger(Vue.class);
	private static final int WIDTH_WINDOWS = 800;
	private static final int HEIGHT_WINDOWS = 650;
	public static final int BASE_X = 100;
	public static final int BASE_Y = 100;
	public static final int TAILLE = 80;
	public static final String URL_IMAGE = "resources/allemagne.jpg";		// Test d'une image
	private JTable tableau;

	// Main
	private boolean a;
	private boolean t;
	private String[][] jardin;
	private ModeleDynamique modele;
	// Main - Calcul durée action
	public static final int _TEMPS_ = 1000;
	public int scalevitesseJeu;
	public int vitesseJeu;

	/**
	 * Panels
	 */
	final JPanel startPanel;		// Panel home (menu, accueil)
	final JPanel jeuPanel;			// Panel du jeu
	final JPanel resPanel;			// Panel des résultats

	/**
	 * JButton
	 */
	JButton boutonResultats;
	JButton	boutonAcceleration;
	JButton	boutonDecceleration;
	ArrayList<JLabel> labels;

	/** 
	 * Tests statiques à modifier
	 * Placement d'un chiffre avant pour pouvoir recuperer generiquement le nombre (1, 2, ..) et l'element ( rocher, ..)
	 */
	//public String[][] jardin = {{"1 carotte", "1 rocher", "1 lapin"}, {"1 lapin", "1 rocher", ""}, {"", "", "2 carottes"}}; // copied
	//public ArrayList<Integer> nbCarottesMangees; // copied
	//public ArrayList<Integer> memorizedSequences; // copied

	/**
	 * Constructeur des differents elements graphiques
	 */
	public Vue() 
	{
		super();
		//LOGGER.debug("Vue constructeur"); // A modifier - intégrer les LOGGER..

		// Test
		a = Principale.getA();
		t = Principale.getT();
		jardin = Principale.getJardin();
		modele = Principale.getModeleD();
		vitesseJeu = Principale.getVitesseJeu();

		if(a) System.out.println("Vue - Constructeur");
	
		// Récupération du csv et ajout des lapins
		//modele.init();					// copied

		labels = new ArrayList<JLabel>();

		setTitle("Lapinou");
		this.setVisible(true);
		setPreferredSize(new Dimension(WIDTH_WINDOWS, HEIGHT_WINDOWS));
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 3 panels ( accueil, jeu, resultats )
		startPanel = new JPanel();
		jeuPanel = new JPanel();
		resPanel = new JPanel();

		// Initialisation des panels :
		// Config. du panel start
		startPanel.setBounds(0, 0, WIDTH_WINDOWS, HEIGHT_WINDOWS);
		startPanel.setLayout(null);
		startPanel.setVisible(false);
		this.add(startPanel);

		// Config. du panel jeu
		jeuPanel.setBounds(0, 0, WIDTH_WINDOWS, HEIGHT_WINDOWS);
		jeuPanel.setLayout(null);
		jeuPanel.setVisible(true);
		this.add(jeuPanel);

		// Config. du panel resultats
		resPanel.setBounds(0, 0, WIDTH_WINDOWS, HEIGHT_WINDOWS);
		resPanel.setLayout(null);		
		this.add(resPanel);

		// Bouton 'Lancement du jeu'
		//start.add(new JButton(new lancement()));

		//tableau = new JTable(modele);

		// columns : y ; rows : x
		int x = 3, y = 3;
		// Affichage initial
		generationTerrain(x, y);

		/*
		 * ???
		 * String[][] cases = {{"1", "0"}, {"0", "2"}};
		int nbColonnes = cases[0].length;
		int nbLignes = cases.length;

		tableau = new JTable(cases, cases);
		jeu.add(tableau);
		tableau.setVisible(true);
		tableau.setBounds(0, 0, 100, 100);
		*/

		pack();
	}

	// Après la fin d'une partie : accès disponible aux résultats des lapins
	public void finDePartie() 
	{
		boutonResultats.setEnabled(true);
	}

	/**
	 * Permet de faire des tests de la matrice jardin contenant tous les éléments (affichage console)
	 */
	public void launchTest() 
	{
		if(a) System.out.println("Vue - launchTests");

		for (int i = 0; i < jardin.length; i++) {
			for (int j = 0; j < jardin[i].length; j++) {
				System.out.println("( " + String.valueOf(i) + " - " + String.valueOf(j) + " ) : " + jardin[i][j]);				
			}
			System.out.println();
		}
	}
	
	/*public void testImage() {
		// Create an ARGB BufferedImage
		File file = new File(URL_IMAGE);
		BufferedImage img = ImageIO.read(file);
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, null);

		//  Create a rescale filter op that makes the image 50% opaque.		 
		float[] scales = { 1f, 1f, 1f, 0.5f };
		float[] offsets = new float[4];
		RescaleOp rop = new RescaleOp(scales, offsets, null);

		// Draw the image, applying the filter
		g2d.drawImage(bi, rop, 0, 0);
	}*/

	/**
	 * Generation du terrain de jeu
	 * @param x
	 * @param y
	 * @param indice
	 */
	public void generationTerrain(int x, int y) 
	{
		if(a) System.out.println("Vue - generationTerrain()");

		jeuPanel.removeAll();
		jeuPanel.setVisible(false);

		int base_x = BASE_X;
		int base_y = BASE_Y;
		int position_x = base_x;
		int position_y = base_x;
		int legendePosition = 20;
		Font font = new Font("Arial", Font.BOLD, 20);

		// Config. Bouton Resultats -> renvoie vers les résultats de la partie ( si elle est terminee )
		boutonResultats = new JButton("Resultats");
		boutonResultats.setBounds(260, 10, 100, 20);	// Position et taille du bouton
		boutonResultats.setVisible(true); // utile?
		jeuPanel.add(this.boutonResultats);
		boutonResultats.setEnabled(false);
		boutonResultats.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("\nAffichage des résultats\n----------------------\n");
				startPanel.setVisible(false);	// menu off				
				jeuPanel.setVisible(false);		// plateau de jeu off
				resPanel.setVisible(true);		// resultats off
				generationResultats();
			}
		});

		// Bouton - Acceleration de la vitesse du jeu
		boutonAcceleration = new JButton("Accelerer le jeu");
		boutonAcceleration.setBounds(20, 10, 100, 20);				// Position et taille du bouton
		boutonAcceleration.setVisible(true); 						// utile?
		jeuPanel.add(this.boutonAcceleration);
		boutonAcceleration.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				accelererJeu();
			}
		});

		// Bouton - Decceleration de la vitesse du jeu
		boutonDecceleration = new JButton("Ralentir le jeu");
		boutonDecceleration.setBounds(130, 10, 100, 20);				// Position et taille du bouton
		boutonDecceleration.setVisible(true); 						// utile?
		jeuPanel.add(this.boutonDecceleration);
		boutonDecceleration.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				ralentirJeu();
			}
		});

		// Création du jardin - Pour toutes les colonnes..
		for (int i = 0; i < y; i++) {
			if(i > 0) position_y += base_y + 10;

			// Légende des colonnes
			JLabel colonneLabel = new JLabel(String.valueOf(i+1), SwingConstants.CENTER);
			colonneLabel.setBounds(legendePosition, position_y, TAILLE, TAILLE);
			colonneLabel.setFont(font);
			jeuPanel.add(colonneLabel);

			// Pour toutes les lignes..
			for (int j=0; j<x; j++) {
				// Position de la case ( JLabel )
				if(j > 0) position_x += base_x + 10;
				int nbCarottesLabel = 0;
				String type = "";

				// Label case
				JLabel caseLabel = new JLabel("", SwingConstants.CENTER);
				caseLabel.setBounds(position_x, position_y, TAILLE, TAILLE);
				caseLabel.setOpaque(true);
				//caseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

				// Légende des lignes
				JLabel ligneLabel = new JLabel((String.valueOf(j+1)), SwingConstants.CENTER);
				ligneLabel.setBounds(position_x, legendePosition, TAILLE, TAILLE);
				ligneLabel.setFont(font);
				jeuPanel.add(ligneLabel);

				// Récupération du type si non vide // décalage de 2 pour pouvoir récupérer le nombre d'elements -> ^[2 ][carottes]$
				if(jardin[i][j].length() > 1) type = jardin[i][j].substring(2, jardin[i][j].length());

				// Emplacement d'une carotte
				if(type.equalsIgnoreCase("carotte") || type.equalsIgnoreCase("carottes")) {
					nbCarottesLabel = Integer.parseInt(jardin[i][j].substring(0, 1));
					caseLabel.setText(String.valueOf(nbCarottesLabel));
					caseLabel.setBackground(Color.ORANGE);
					//ajoutImageToLabel("resources/carotte.png", caseLabel, 1);
				}
				// Emplacement d'un rocher
				else if(type.equalsIgnoreCase("rocher")) {
					caseLabel.setBackground(Color.WHITE);
					caseLabel.setText("X");
					caseLabel.setFont(font);
					ajoutImageToLabel("resources/stone.png", caseLabel, 0);
				}
				// Emplacement d'un lapin
				else if(type.equalsIgnoreCase("lapin")) {
					caseLabel.setBackground(Color.BLUE);
					caseLabel.setText("lapin");

					// Ajout d'une image au label
					for (int k = 0; k < modele.getLapins().size(); k++) {
						// On cherche à comparer la position de la case du jardin où se situe un lapin avec la position connue de tous les lapins
						if(i == Integer.parseInt(modele.getLapins().get(k).getPosition().substring(0, 1))-1
						&& j == Integer.parseInt(modele.getLapins().get(k).getPosition().substring(2, 3))-1) {
							char direction = modele.getLapins().get(k).getOrientation().charAt(0);
							switch (direction) {
								case 'N': ajoutImageToLabel("resources/lapin_nord.png", caseLabel, 0); break;
								case 'O': ajoutImageToLabel("resources/lapin_ouest.png", caseLabel, 0); break;
								case 'E': ajoutImageToLabel("resources/lapin_est.png", caseLabel, 0); break;
								case 'S': ajoutImageToLabel("resources/lapin_sud.png", caseLabel, 0); break;
							}
						}
					}
				}

				//caseLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
				jeuPanel.add(caseLabel);
				labels.add(caseLabel);
			}
			position_x = base_x;
		}

		jeuPanel.setVisible(true);
	}
	
	/**
	 * Ajout d'une image a un JLabel
	 * @param url
	 * @param label
	 * @param decalage
	 */
	public void ajoutImageToLabel(String url, JLabel label, int decalage) 
	{
		if(a) System.out.println("Vue - ajoutImageToLabel()");
		if(t) System.out.println(url);

		labels.remove(label);
		// Intégration de l'image et création du label
		ImageIcon imgThisImg = new ImageIcon(url);
		Image img_ = imgThisImg.getImage();
		Image newimg = img_.getScaledInstance(BASE_X, BASE_Y, java.awt.Image.SCALE_SMOOTH);  
		ImageIcon newIcon = new ImageIcon(newimg);

		label.setIcon(newIcon);
		labels.add(label);
	}

/** emplacement de deplacementsLapins() **/

	/**
	 * Generation des resultats des lapins ( nombre de carottes mangees )
	 * Non fonctionnel
	 */
	void generationResultats() 
	{
		if(a) System.out.println("Vue - generationResultats");
		//getContentPane().add(resPanel, BorderLayout.NORTH);
		String[][] resLapins = {{"", "", ""}, {"", "", ""}, {"", "", ""}};

		// Attribution
		for (int i = 0; i < modele.getLapins().size(); i++) {
			resLapins[i][0] = String.valueOf(i+1);										// rang
			resLapins[i][1] = modele.getLapins().get(i).getNom();							// Nom
			resLapins[i][2] = String.valueOf(modele.getLapins().get(i).getScore());			// Score
		}

		String[] entetes = { "Rang", "Nom lapin", "Nombre de carotte mangee" };

		this.tableau = new JTable(resLapins, entetes);
		this.tableau.setBounds(100, 20, 300, 50);
		resPanel.add(this.tableau);
		resPanel.setVisible(true);
	}

	/**
	 * Permet d'accélerer le jeu
	 */
	public void accelererJeu() 
	{
		if(a) System.out.println("Vue - accelererJeu");
		this.scalevitesseJeu++;

		if(this.scalevitesseJeu == 0) {
			this.scalevitesseJeu = 1;
		}

		if(this.scalevitesseJeu < 0) {
			this.scalevitesseJeu = this.scalevitesseJeu * (-1);
			this.vitesseJeu = (_TEMPS_ * this.scalevitesseJeu);
			this.scalevitesseJeu = this.scalevitesseJeu * (-1);
		} else if (this.scalevitesseJeu > 0) {
			this.vitesseJeu = (_TEMPS_ / this.scalevitesseJeu);
		}
		//System.out.println("Vitesse : " + String.valueOf(this.vitesseJeu));
	}

	/**
	 * Cela permet de ralentir le jeu
	 */
	public void ralentirJeu() 
	{
		if(a) System.out.println("Vue - ralentirJeu");
		this.scalevitesseJeu--;

		if(this.scalevitesseJeu == 0) {
			this.scalevitesseJeu = -1;
		}

		if(this.scalevitesseJeu < 0) {
			this.scalevitesseJeu = this.scalevitesseJeu * (-1);	
			this.vitesseJeu = (_TEMPS_ * this.scalevitesseJeu);
			this.scalevitesseJeu = this.scalevitesseJeu * (-1);
		} else if (this.scalevitesseJeu > 0) {
			this.vitesseJeu = (_TEMPS_ / this.scalevitesseJeu);
		}
		//System.out.println("Vitesse : " + String.valueOf(this.vitesseJeu));
	}

}