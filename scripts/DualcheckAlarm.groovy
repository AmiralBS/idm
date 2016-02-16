// 1°) Déclaration des bricks
input "button" on 9
input "button2" on 10
output "led" on 1 // Analog car pin <= 5

// 2°) Définition des comportements

// Définition des états
state "on" means
	_ led becomes 12

state "off" means
	_ led becomes 1023

// Définition des transitions
from on to off when
	_ button eq low
		or 
	_ button2 eq low

from off to on when
	_ button eq high
		and
	_ button2 eq high

// 3°) Définition de l'état initial
initial off

// 4°) Appel pour la génération du code
export "Dualcheck Alarm"