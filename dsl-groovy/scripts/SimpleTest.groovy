output "greenled" on 8

joystick "joy" on 5, 4, 9 // X, Y, B

//bind joy to screen	

konami joy, screen
	code L, D, R, U attempts 3
	success
		_ greenled becomes high
	failure
	gameover
	
export "Dev!"