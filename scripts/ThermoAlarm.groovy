// 1°) Déclaration des bricks
input "thermo" on 3
output "buzzer" on 9

// 2°) Définition des comportements

// Définition des états
state "alarm" means
	_ buzzer becomes high

state "off" means
	_ buzzer becomes low

// Définition des transitions
from off to alarm when
	_ thermo gt 57

from alarm to off when
	_ thermo le 57
	
// 3°) Définition de l'état initial
initial off

// 4°) Appel pour la génération du code
export "Thermo Alarm"