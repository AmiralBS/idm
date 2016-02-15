output "led" on 8
	
lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16

joystick "joy" on 5, 4, 9 // X, Y, B

//bind joy to screen	

konami joy, screen, L, D, R, U, L, R, U, D


export "Switch!"