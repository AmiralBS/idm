sensor "button" pin 9
actuator "led" pin 10

state "on" means led becomes high
state "off" means led becomes low

initial off

from on to off when button becomes high, none
from off to on when button becomes high, none

export "Switch!"