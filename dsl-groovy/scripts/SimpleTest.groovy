lcd "screen" on_bus 2
output "led" on 10
	
joystick "joy" on 1, 2, 9 // X, Y, B	

joystick left means
	_ screen display "left"
	_ led value 1

joystick right means
	_ screen display "right"
	_ led value 1

joystick up means
	_ screen display "up"
	_ led value 1

joystick down means
	_ screen display "down"
	_ led value 0
	
joystick pushed means
	_ screen display "pushed"
	_ led value 0


initial neutral

export "Switch!"