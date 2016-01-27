// 1°) Déclaration des bricks 
input "button" on 9
output "led" on 10

// 2°) Définition des comportements

// Définition des états
state "on" means
	_ led value high

state "off" means
	_ led value low

// Définition des transitions
from on to off when
	_ button eq low

from off to on when
	_ button eq high
	
// 3°) Définition de l'état initial
initial off

// 4°) Appel pour la génération du code
export "Switch!"