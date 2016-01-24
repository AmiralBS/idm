sensor "button" pin 9
actuator "led" pin 10
actuator "buzzer" pin 11

state "step1" means buzzer becomes high
state "step2" means led becomes high and buzzer becomes low
state "step3" means led becomes low

initial off

from step1 to step2 when button becomes high, none
from step2 to step3 when button becomes high, none
from step3 to step1 when button becomes high, none

export "Switch!"