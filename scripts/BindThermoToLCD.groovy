// 1°) Déclaration des bricks 
input "thermo" on 3
lcd "screen" bus 2

// 2°) Définition des comportements

// Définition des états
state "hot" means
	_ screen display thermo
	
state "cold" means
	_ screen display thermo
	
state "good" means
	_ screen display thermo

// Définition des transitions
from good to cold when
	_ thermo lt 200

from cold to good when
	_ thermo gt 200
	
from good to hot when
	_ thermo gt 800

from cold to good when
	_ thermo lt 800
	
// 3°) Définition de l'état initial
initial good

// 4°) Appel pour la génération du code
export "Bind Thermo to LCD"