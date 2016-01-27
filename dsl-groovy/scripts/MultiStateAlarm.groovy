input "button" on 9
output "led" on 10
output "buzzer" on 11

state "step1" means
	_ buzzer value high

state "step2" means
	_ led value high
	_ buzzer value low
	
state "step3" means
	_ led value low

from step1 to step2 when
	_ button eq high
	
from step2 to step3 when
	_ button eq high
	
from step3 to step1 when
	_ button eq high

initial step1

export "Switch!"