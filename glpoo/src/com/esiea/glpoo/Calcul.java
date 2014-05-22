package com.esiea.glpoo;


public class Calcul  {
	private boolean a;
	private boolean t;

	//public String[][] jardin = {{"1 carotte", "1 rocher", "1 lapin"}, {"1 lapin", "1 rocher", ""}, {"", "", "2 carottes"}}; // copied
	// Calcul durée action
	private int vitesseJeu;

	public Calcul() 
	{
		// Test
		a = Principale.getA();	// ?
		t = Principale.getT();	// test
		vitesseJeu = Principale.getVitesseJeu();
	}
	
	/**
	 * Calculs des deplacements du lapins
	 * @param x
	 * @param y
	 * @param indice
	 */
	public void deplacementLapins(int i, int j, int x, int y, int nbCarottes, ModeleDynamique modele, 
			String[][] jardin, String type, boolean bunnysSpotted, int k, int l) 
	{
		//if(a) System.out.println("Vue - deplacementLapins()");

		//int base_x = BASE_X;
		//int base_y = BASE_Y;
		//int position_x = base_x;
		//int position_y = base_x;
		//Font font = new Font("Arial", Font.BOLD, 20);

		// Modification du jardin
		// Pour toutes les colonnes..
		//for (int i = 0; i < y; i++) { // copied
			// Calcul de la position de l'emplacement en y
			//if(i > 0) position_y += base_y + 10;

			// Pour toutes les lignes..
			//for (int j = 0; j < x; j++) { // copied
				// Calcul de la position de l'emplacement en x
				//if(j > 0) position_x += base_x + 10; 

				//int nbCarottes = 0; // copied
				//String type = "";		// copied
				// Récupération du type si non vide
				//if(jardin[i][j].length() > 1) type = jardin[i][j].substring(2, jardin[i][j].length()); //copied

				// Lapin repéré dans le jardin
				//if(type.equalsIgnoreCase("lapin")) {
					// Pour tous les lapins
					//for (int k = 0; k < modele.getLapins().size(); k++) {
						//int caseX = j + 1;		// colonnes 
						//int caseY = i + 1;		// lignes

						// Lapin position = jardin position actuelle
						//if(caseX == Integer.parseInt(modele.getLapins().get(k).getPosition().substring(2, 3)) 
						//&& caseY == Integer.parseInt(modele.getLapins().get(k).getPosition().substring(0, 1))
						//&& !modele.getLapins().get(k).isSequenceAchieved()) {

							// Bunny versus bunny.
							//boolean bunnysSpotted = false;

							// Pour chaque mouvements du lapin.. ( avancé ou tourné de 90° -> 1 seconde )
							//for (int l = 0; l<modele.getLapins().get(k).getSequences().length(); l++) {
								// On s'en va, ce n'est plus intéressant ( un lapin est trop innocent pour en manger un autre )
								//if (bunnysSpotted) break;
								

								// Pause d'une seconde ( vitesse variable )
								if(t) System.out.println("pause");
								try { Thread.sleep(vitesseJeu); }
								catch (InterruptedException e) { e.printStackTrace(); }

								// Calculs en fonction du mouvement
								char mouvement = modele.getLapins().get(k).getSequences().charAt(l);
								switch(mouvement) {
									case 'A': {
										if(t) System.out.print("\tLe lapin_" + String.valueOf(k) + " avance ");
										int decalage = 0;

										// -------------  NORD : i - 1
										if(modele.getLapins().get(k).getOrientation().charAt(0) == 'N') {
											if(t) System.out.print(" Nord ");
											// Récupération du type si case pas vide
											decalage = i - 1;
											
											// Le lapin est bloque par le mur
											if(i == 0) {
												if(t) System.out.println(" -> lapin bloque (mur)");
												break;
											}
											if(jardin[decalage][j].length() > 1) {
												String typeSuivant = jardin[decalage][j].substring(2, jardin[decalage][j].length());

												// Inévitable.. le lapin mange la carotte..
												if(typeSuivant.equalsIgnoreCase("carotte") || typeSuivant.equalsIgnoreCase("carottes")) {
													if(t) System.out.print(" -> Carotte mangee et le lapin_"+String.valueOf(k) + " avance a la case");
													nbCarottes = Integer.parseInt(jardin[i-1][j].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide.. A CODER
													// Réécriture du contenu du JLabel (avec carotte - 1) et chgmt de la position du lapin													
													jardin[decalage][j] = String.valueOf(nbCarottes-1) + jardin[decalage][j].substring(1, jardin[decalage][j].length());
													// Sauvegarde de la position du lapin
													modele.getLapins().get(k).setPosition(String.valueOf(decalage+1) + "-" + String.valueOf(j+1));
													// Ajout d'une carotte au score
													modele.getLapins().get(k).addCarottes();

													// Pause de 1 seconde ( vitesse variable )
													if(t) System.out.println("pause");
													try { Thread.sleep(vitesseJeu); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher ( le lapin ne bouge pas )
												else if(typeSuivant.equalsIgnoreCase("rocher")) {
													if(t) System.out.println(" -> lapin bloque (rocher)");
													break;
												}
												// Un autre lapin a été repéré, le lapin devient en pause et l'autre s'active
												else if(typeSuivant.equalsIgnoreCase("lapin")) {
													if(t) System.out.println(" -> lapin en pause");
													// On mémorise la séquence pour y revenir après..
													modele.getLapins().get(k).setSequenceInachieved(l);
													bunnysSpotted = true;
													break;
												}
											}
											// La lapin avance dans la case vide..
											else {
												if(t) System.out.println(" -> le lapin avance");
												jardin[decalage][j] = "1 lapin";
												jardin[i][j] = "";
												// Sauvegarde de la position du lapin
												modele.getLapins().get(k).setPosition(String.valueOf(decalage+1) + "-" + String.valueOf(j+1));
												
												if(t) System.out.println(" " + modele.getLapins().get(k).getOrientation());
												
												// Pause de 1 seconde ( vitesse variable )
												if(t) System.out.println("pause");
												try { Thread.sleep(vitesseJeu); }
												catch (InterruptedException e) { e.printStackTrace(); }
											}
										}
										// -------------  OUEST : j - 1
										else if(modele.getLapins().get(k).getOrientation().charAt(0) == 'O') {
											if(t) System.out.print(" Ouest ");
											decalage = j - 1;

											// Le lapin est bloque par le mur
											if ( j == 0 ) {
												if(t) System.out.println(" -> lapin bloque (mur)");
												break;
											}
											// Récupération du typeSuivant si case pas vide
											if(jardin[i][decalage].length() > 1) {
												String typeSuivant = jardin[i][decalage].substring(2, jardin[i][decalage].length());

												// Inévitable.. le lapin mange la carotte..
												if(typeSuivant.equalsIgnoreCase("carotte") || typeSuivant.equalsIgnoreCase("carottes")) {
													if(t) System.out.print(" -> Carotte mangee et le lapin_"+String.valueOf(k) + " avance a la case");
													nbCarottes = Integer.parseInt(jardin[i][decalage].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide A CODER
													// Réécriture du contenu du JLabel (avec carotte - 1) et chgmt de la position du lapin
													jardin[i][decalage] = String.valueOf(nbCarottes-1) + jardin[i][decalage].substring(1, jardin[i][decalage].length());
													// Sauvegarde de la position du lapin
													modele.getLapins().get(k).setPosition(String.valueOf(i+1) + "-" + String.valueOf(decalage+1));
													// Ajout d'une carotte au score
													modele.getLapins().get(k).addCarottes();
													
													// Pause de 1 seconde ( vitesse variable )
													if(t) System.out.println("pause");
													try { Thread.sleep(vitesseJeu); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher ( le lapin ne bouge pas )
												else if(typeSuivant.equalsIgnoreCase("rocher")) {
													if(t) System.out.println(" -> lapin bloque (rocher)");
													break;
												}
												// Un autre lapin a été repéré, le lapin devient en pause et l'autre s'active
												else if(typeSuivant.equalsIgnoreCase("lapin")) {
													if(t) System.out.println(" -> lapin en pause");
													// On mémorise la séquence pour y revenir après..
													modele.getLapins().get(k).setSequenceInachieved(l);
													bunnysSpotted = true;
													break;
												}
											}
											// La lapin avance dans la case vide..
											else {
												if(t) System.out.println(" -> le lapin avance");
												jardin[i][decalage] = "1 lapin";
												jardin[i][j] = "";

												// Sauvegarde de la position du lapin
												modele.getLapins().get(k).setPosition(String.valueOf(i+1) + "-" + String.valueOf(decalage+1));

												if(t) System.out.println(" " + modele.getLapins().get(k).getOrientation());
												
												// Pause de 1 seconde ( vitesse variable )
												if(t) System.out.println("pause");
												try { Thread.sleep(vitesseJeu); }
												catch (InterruptedException e) { e.printStackTrace(); }
											}
										}
										// ------------- EST : j + 1
										else if(modele.getLapins().get(k).getOrientation().charAt(0) == 'E') {
											if(t) System.out.print(" Est ");
											decalage = j + 1;
											
											// Le lapin est bloque par le mur
											if( decalage == x ) {
												if(t) System.out.println(" -> lapin bloque (mur)");
												break;
											}
											// Récupération du typeSuivant si case pas vide
											if(jardin[i][decalage].length() > 1) {
												String typeSuivant = jardin[i][decalage].substring(2, jardin[i][decalage].length());

												// Inévitable.. le lapin mange la carotte..
												if(typeSuivant.equalsIgnoreCase("carotte") || typeSuivant.equalsIgnoreCase("carottes")) {
													if(t) System.out.print(" -> Carotte mangee et le lapin_"+String.valueOf(k) + " avance a la case");
													nbCarottes = Integer.parseInt(jardin[i][decalage].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide.. A CODER
													// Réécriture du contenu du JLabel (avec carotte - 1) et chgmt de la position du lapin
													jardin[i][decalage] = String.valueOf(nbCarottes-1) + jardin[i][decalage].substring(1, jardin[i][decalage].length());
													// Sauvegarde de la position du lapin
													modele.getLapins().get(k).setPosition(String.valueOf(i+1) + "-" + String.valueOf(decalage+1));
													// Ajout d'une carotte au score
													modele.getLapins().get(k).addCarottes();

													// Pause de 1 seconde ( vitesse variable )
													if(t) System.out.println("pause");
													try { Thread.sleep(vitesseJeu); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher ( le lapin ne bouge pas )
												else if(typeSuivant.equalsIgnoreCase("rocher")) {
													if(t) System.out.println(" -> lapin bloque (rocher)");
													break;
												}
												// Un autre lapin a été repéré, le lapin devient en pause et l'autre s'active
												else if(typeSuivant.equalsIgnoreCase("lapin")) {
													if(t) System.out.println(" -> lapin en pause");
													// On mémorise la séquence pour y revenir après..
													modele.getLapins().get(k).setSequenceInachieved(l);
													bunnysSpotted = true;
													break;
												}
											}
											// La lapin avance dans la case vide..
											else {
												if(t) System.out.println(" -> le lapin avance");
												jardin[i][decalage] = "1 lapin";
												jardin[i][j] = "";
												// Sauvegarde de la position du lapin
												modele.getLapins().get(k).setPosition(String.valueOf(i+1) + "-" + String.valueOf(decalage+1));

												if(t) System.out.println(" " + modele.getLapins().get(k).getOrientation());

												// Pause de 1 seconde ( vitesse variable )
												if(t) System.out.println("pause");
												try { Thread.sleep(vitesseJeu); }
												catch (InterruptedException e) { e.printStackTrace(); }
											}
										}
										// -------------  SUD : i + 1
										else if(modele.getLapins().get(k).getOrientation().charAt(0) == 'S') {
											if(t) System.out.print(" Sud  !! ");
											decalage = i + 1;

											// Le lapin est bloque par le mur
											if( decalage == y ) {
												if(t) System.out.println(" -> lapin bloque (mur)");
												break;
											}
											// Récupération du typeSuivant si case pas vide
											else if(jardin[decalage][j].length() > 1) {
												String typeSuivant = jardin[decalage][j].substring(2, jardin[decalage][j].length());

												// Inévitable.. le lapin mange la carotte..
												if(typeSuivant.equalsIgnoreCase("carotte") || typeSuivant.equalsIgnoreCase("carottes")) {
													if(t) System.out.print(" -> Carotte mangee et le lapin_"+String.valueOf(k) + " avance a la case");
													nbCarottes = Integer.parseInt(jardin[decalage][j].substring(0, 1));
													if(nbCarottes == 1) ; // Devient une case vide.. A CODER
													// Réécriture du contenu du JLabel (avec carotte - 1) et chgmt de la position du lapin
													jardin[decalage][j] = String.valueOf(nbCarottes-1) + jardin[decalage][j].substring(1, jardin[decalage][j].length());
													// Sauvegarde de la position du lapin
													modele.getLapins().get(k).setPosition(String.valueOf(decalage+1) + "-" + String.valueOf(j+1));
													// Ajout d'une carotte au score
													modele.getLapins().get(k).addCarottes();

													// Pause de 1 seconde ( vitesse variable )
													if(t) System.out.println("pause");
													try { Thread.sleep(vitesseJeu); }
													catch (InterruptedException e) { e.printStackTrace(); }
												}
												// Rocher ( le lapin ne bouge pas )
												else if(typeSuivant.equalsIgnoreCase("rocher")) {
													if(t) System.out.println(" -> lapin bloque (rocher)");
													break;
												}
												// Un autre lapin a été repéré, le lapin devient en pause et l'autre s'active
												else if(typeSuivant.equalsIgnoreCase("lapin")) {
													if(t) System.out.println(" -> lapin en pause");
													// On mémorise la séquence pour y revenir après..
													modele.getLapins().get(k).setSequenceInachieved(l);
													bunnysSpotted = true;
													break;
												}
											}
											// La lapin avance dans la case vide..
											else {
												if(t) System.out.println(" -> le lapin avance");
												jardin[decalage][j] = "1 lapin";
												jardin[i][j] = "";
												// Sauvegarde de la position du lapin
												modele.getLapins().get(k).setPosition(String.valueOf(decalage+1) + "-" + String.valueOf(j+1));
												
												if(t) System.out.println("Sud: " + modele.getLapins().get(k).getOrientation());
												
												// Pause de 1 seconde ( vitesse variable )
												if(t) System.out.println("pause");
												try { Thread.sleep(vitesseJeu); }
												catch (InterruptedException e) { e.printStackTrace(); }
											}
										}
									} break;

									// Rotation gauche :
									case 'G': {
										if(t) System.out.println("\tLe lapin_" + String.valueOf(k) + " tourne a gauche ");
										char direction = modele.getLapins().get(k).getOrientation().charAt(0);

										// Mise a jour du skin de la case actuelle et Rotation memorisee
										switch (direction) {
											case 'N': modele.getLapins().get(k).setOrientation("O"); break;
											case 'O': modele.getLapins().get(k).setOrientation("S"); break;
											case 'E': modele.getLapins().get(k).setOrientation("N"); break;
											case 'S': modele.getLapins().get(k).setOrientation("E"); break;
										}
										// Pause de 1 seconde ( vitesse variable )
										if(t) System.out.println("pause");
										try { Thread.sleep(vitesseJeu); }
										catch (InterruptedException e) { e.printStackTrace(); }
									} break;

									// Rotation droite :
									case 'D': {
										if(t) System.out.println("\tLe lapin_" + String.valueOf(k) + " tourne a droite ");
										char direction = modele.getLapins().get(k).getOrientation().charAt(0);

										switch (direction) {
											case 'N': modele.getLapins().get(k).setOrientation("E"); break;
											case 'O': modele.getLapins().get(k).setOrientation("N"); break;
											case 'E': modele.getLapins().get(k).setOrientation("S"); break;
											case 'S': modele.getLapins().get(k).setOrientation("O"); break;
										}
										// Pause de 1 seconde ( vitesse variable )
										if(t) System.out.println("pause");
										try { Thread.sleep(vitesseJeu); }
										catch (InterruptedException e) { e.printStackTrace(); }
									} break;
								} // fin switch mouvement / action
							//} // pour toutes les actions
						//} // lapin identifie
					//}	// pour tous les lapins
				//} // lapin repere dans le jardin
			//}	// boucle j : colonnes
		//}	// boucle i : lignes

		//return true;
	}
	
	public boolean getA() { return this.a; }
	public boolean getT() { return this.t; }
}
