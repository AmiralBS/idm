input "A" on 8
output "greenled"  on 7

joystick "joy" on 5, 4, 9 // X, Y, B

lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16

//bind joy to screen	

konami joy, screen, A
	code  L, D, R, U, A attempts 5
	success
		_ greenled becomes high
	failure
		_ greenled becomes low
	gameover
		_ greenled becomes high
	
export "Dev!"
