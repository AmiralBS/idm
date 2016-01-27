lcd "screen" on_bus 2 // 10 11 12 13 14 15 16
output "led" on 8
	
joystick "joy" on 5, 4, 9 // X, Y, B	

joystick left means
	_ screen display "left"
	_ led value high

joystick right means
	_ screen display "right"
	_ led value high

joystick up means
	_ screen display "up"
	_ led value high

joystick down means
	_ screen display "down"
	_ led value low
	
joystick pushed means
	_ screen display "pushed"
	_ led value low
	
initial neutral

export "Switch!"