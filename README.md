# DSL ArduinoML

DSL ArduinoML is an interpreter enabling to describe easily the behaviour of bricks and generate the source code.

Our meta­model can describe

1. Very simple alarm​: Pushing a button activates a LED and a buzzer. Releasing the
button switches the actuators off.

2. Dual­check alarm​: Pushing a button will trigger a buzzer if and only if two buttons are
pushed at the very same time. Releasing at least one of the button stop the sound.

3. State­based alarm​: Pushing the button once switch the system in a mode where the
LED is switched on. Pushing it again switches it off.

4. Multi­state alarm​: Pushing the button starts the buzz noise. Pushing it again stop the
buzzer and switch the LED on. Pushing it again switch the LED off, and makes the
system ready to make noise again after one push, and so on.

5. Handling Analogical Bricks: As a user, one can use the ArduinoML language to use
analogical bricks.

6. Supporting the LCD screen: As an ArduinoML user, one can use the
language to write text messages on the screen.

7. “Konami™” Code Generator: We consider here a fixed hardware: a Playstation™ joystick, a green LED, a red LED and a piezoelectric buzzer

For more information, visit Sébastion [Mosser] 's [courses]

### Installation

You need Maven installed globally:

```sh
$ git clone https://github.com/AmiralBS/idm.git dsl-arduinoml
$ cd dsl-arduinoml
$ mvn clean package 
```

### Development

DSl ArduinoML contains a list of samples

Open your favorite Terminal and run these commands.

First Step: Check samples proposed 
```sh
$ cd dsl-groovy\scripts
$ ls
```

Second Step: Choose a sample and run it
```sh
$ run [Sample_File] [Output_File]
Example :
$ run KonamiCode.groovy KonamiCode
```

### Version
3.2

### Technology

DSL ArduinoML uses

* Java
* Groovy
* Maven

Of course DSL ArduinoML is open source with a [public repository][here] on GitHub.

### Todos

 - Write Tests
 - Add Code Comments


License
----

Open Source GPL

   [courses]: http://www.i3s.unice.fr/~mosser/teaching/15_16/dsl/start
   [Mosser]: http://www.i3s.unice.fr/~mosser/start
   [here]: https://github.com/AmiralBS/idm

