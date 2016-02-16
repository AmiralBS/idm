output "led" on 8
input "A" on 10
input "B" on 10
	
lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16

joystick "joy" on 5, 4, 9 // X, Y, B

konami joy, screen, A, B
	code L, D, A, A, B, R

export "Konami Code"