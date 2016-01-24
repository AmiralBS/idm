sensor "button" pin 9
sensor "button2" pin 11
actuator "led" pin 10

state "on" means led becomes high
state "off" means led becomes low

initial off

from on to off when button becomes low, or when button2 becomes low, none

from off to on when button becomes high, and when button2 becomes high, none

export "Switch!"