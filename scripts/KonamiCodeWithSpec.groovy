// 1°) Déclaration des bricks
input "A" on 8
output "greenled"  on 7
joystick "joy" on 5, 4, 9 // X, Y, B
lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16

// 2°) Définition des comportements
// Les inputs dont on a besoin pour entrer le code
konami joy, screen, A
	// le code suivi du nombre de tentatives
	code  L, D, R, U, A attempts 5
	// actions si success
	success
		_ greenled becomes high
	// actions si erreur
	failure
		_ greenled becomes low
	// actions si game over
	gameover
		_ greenled becomes high
		
// 3°) Définition de l'état initial
// Automagiquement défini là haut
	
// 4°) Appel pour la génération du code
export "Konami Code With Spec"
