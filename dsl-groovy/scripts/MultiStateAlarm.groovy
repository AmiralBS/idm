input "button" on 9
output "led" on 10
output "buzzer" on 11

state "step1" means 
	_ buzzer becomes high

state "step2" means 
	_ led becomes high
	_ buzzer becomes low
	
state "step3" means
	_ led becomes low

from step1 to step2 when
	_ button becomes high
	
from step2 to step3 when
	_ button becomes high
	
from step3 to step1 when
	_ button becomes high

initial off

export "Switch!"