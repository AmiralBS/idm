// 1°) Déclaration des bricks
output "led" on 8
input "A" on 10
input "B" on 10
lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16
joystick "joy" on 5, 4, 9 // X, Y, B

// 2°) Définition des comportements
// Les inputs dont on a besoin pour entrer le code
konami joy, screen, A, B
	// le code suivi du nombre de tentatives
	code L, D, A, A, B, R attempts 4

// 3°) Définition de l'état initial
// Automagiquement défini là haut
	
// 4°) Appel pour la génération du code
export "Konami Code"