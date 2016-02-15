input "button" on 9
input "button2" on 10
output "led" on 11

state "on" means
	_ led becomes high

state "off" means
	_ led becomes low


from on to off when
	_ button eq low
		or 
	_ button2 eq low

from off to on when
	_ button eq high
		and
	_ button2 eq high
	
initial off

export "Dualcheck Alarm"