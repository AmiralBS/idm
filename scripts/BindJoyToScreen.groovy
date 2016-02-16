// 1°) Déclaration des bricks
lcd "screen" bus 2 // bus 2 : 10 11 12 13 14 15 16
joystick "joy" on 5, 4, 9 // X, Y, B

// 2°) Définition des comportements
// Crée automagiquement tous les états et transitions 
// afin d'afficher les actions sur le joystick
bind joy to screen

// 3°) Définition de l'état initial
// Automagiquement défini lors du bind

// 4°) Appel pour la génération du code
export "Bind Joystick to LCD"