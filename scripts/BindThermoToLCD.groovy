// 1°) Déclaration des bricks 
input "thermo" on 3
lcd "screen" bus 2

// 2°) Définition des comportements

// Définition des états
state "loop" means
	_ screen display thermo


// Définition des transitions

	
// 3°) Définition de l'état initial
initial loop

// 4°) Appel pour la génération du code
export "Bind Thermo to LCD"