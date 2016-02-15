input "button" on 9
output "led" on 11

state "on" means
	_ led becomes high

state "off" means
	_ led becomes low

from on to off when
	_ button eq high
	
from off to on when
	_ button eq high

initial off

export "Statebased Alarm!"