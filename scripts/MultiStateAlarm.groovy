// 1°) Déclaration des bricks
input "button" on 9
output "led" on 10
output "buzzer" on 11

// 2°) Définition des comportements

// Définition des états
state "step1" means
	_ buzzer becomes high

state "step2" means
	_ led becomes high
	_ buzzer becomes low
	
state "step3" means
	_ led becomes low
	
// Définition des transitions
from step1 to step2 when
	_ button eq high
	
from step2 to step3 when
	_ button eq high
	
from step3 to step1 when
	_ button eq high

// 3°) Définition de l'état initial
initial step1

// 4°) Appel pour la génération du code
export "Multi State Alarm"