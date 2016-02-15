input "A" on 8
	
lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16

joystick "joy" on 5, 4, 9 // X, Y, B

//bind joy to screen	

konami joy, screen, A
	code  L, D, R, U, A attempts 5
//	success :
//		_ greenled is HIGH
//	failure:
//	
//	gameover: 
	
export "Dev!"