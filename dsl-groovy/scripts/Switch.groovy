sensor "button1" pin 9
sensor "button2" pin 10
actuator "led" pin 12

state "on" means led becomes high
state "off" means led becomes low

initial off

from on to off when button1 becomes high, and when button2 becomes high, none
from off to on when button1 becomes low, and when button2 becomes low, none

export "Switch!"