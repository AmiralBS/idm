input "button" on 9
input "button2" on 11
output "led" on 10

state "on" means
	_ led value high

state "off" means
	_ led value low


from on to off when
	_ button eq low
		or 
	_ button2 eq low

from off to on when
	_ button eq high
		and
	_ button2 eq high
	
initial off

export "Switch!"