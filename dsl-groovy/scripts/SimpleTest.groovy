lcd "screen" on_bus 2
	
joystick "joy" on 1, 2, 9 // X, Y, B	

joystick left means
	_ screen display "left"

joystick right means
	_ screen display "right"

joystick up means
	_ screen display "up"

joystick down means
	_ screen display "down"
	
joystick pushed means
	_ screen display "pushed"


initial neutral

export "Switch!"