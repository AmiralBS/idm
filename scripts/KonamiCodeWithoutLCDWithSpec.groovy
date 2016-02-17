// 1°) Déclaration des bricks
output "greenled" on 8
output "redled" on 7
input "A" on 10
input "B" on 11
joystick "joy" on 5, 4, 9 // X, Y, B

// 2°) Définition des comportements
// Les inputs dont on a besoin pour entrer le code
konami joy, A, B
	// le code suivi du nombre de tentatives
	code L, D, A, A, B, R attempts 2
	// actions si success
	success
		_ greenled becomes high
	// actions si erreur
	failure
		_ greenled becomes low
		_ redled becomes high
	// actions si game over
	gameover
		_ redled becomes high

// 3°) Définition de l'état initial
// Automagiquement défini là haut

// 4°) Appel pour la génération du code
export "Konami Code Without LCD"