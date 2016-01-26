input "button" on 9
input "button2" on 11
output "led" on 10

state "on" means 
	_ led becomes high

state "off" means 
	_ led becomes low


from on to off when 
	_ button becomes low
		or 
	_ button2 becomes low

from off to on when 
	_ button becomes high
		and 
	_ button2 becomes high
	
initial off

export "Switch!"