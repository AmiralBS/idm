input "button" on 9
actuator "led" on 10

state "on" means 
	_ led becomes high

state "off" means 
	_ led becomes low

from on to off when 
	_ button becomes high
	
from off to on when 
	_ button becomes high

initial off

export "Switch!"