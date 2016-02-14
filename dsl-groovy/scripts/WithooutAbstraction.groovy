lcd "screen" bus 2
input "button" on 9
joystick "joy" on 1, 2, 9 // X, Y, B	
		
state "left" means
	_ screen display "left"

state "right" means
	_ screen display "right"
	
state "up" means
	_ screen display "up"
	
state "down" means
	_ screen display "down"

state "pushed" means
	_ screen display "pushed"

state "neutral" means
	_ screen display "waiting input"

from left to neutral when
	_ joyX gt 200
	
from right to neutral when
	_ joyX lt 700
	
from up to neutral when
	_ joyY lt 700
	
from down to neutral when
	_ joyY gt 200
	
from pushed to neutral when
	_ joyB ne 0
	
from neutral to pushed when
	_ joyB eq 0
	
from neutral to left when
	_ joyX lt 200

from neutral to right when
	_ joyX gt 700

from neutral to up when
	_ joyX lt 200
	
from neutral to down when
	_ joyX gt 700
	
initial neutral

export "Switch!"