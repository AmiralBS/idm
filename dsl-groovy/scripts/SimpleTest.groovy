input "button" on 9
input "button2" on 11
output "led" on 10

state "on" means
	_ led value low

state "off" means
	_ led value high

from on to off when
	_ button eq low
		or
	_ button2 ne low
	
from on to off when
	_ button lt low
		and
	_ button2 gt high

from off to on when
	_ button le high
		and 
	_ button2 ge high

initial off

export "Switch!"