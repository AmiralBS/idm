package fr.unice.polytech.idm.arduinoml.dsl

import fr.unice.polytech.idm.arduinoml.business.Direction;
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.BinaryOperator
import fr.unice.polytech.idm.arduinoml.kernel.behavioral.State
import fr.unice.polytech.idm.arduinoml.kernel.structural.Actuator
import fr.unice.polytech.idm.arduinoml.kernel.structural.LCD;
import fr.unice.polytech.idm.arduinoml.kernel.structural.Sensor


abstract class ArduinoMLBasescript extends Script {
    boolean neutral_added = false;

    // input "name" on n
    def input(String name) {
        [on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createSensor(name, n) }]
    }

    // output "name" on n || output "name off n
    def output(String name) {
        [on: { n -> ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createActuator(name, n) }]
    }

    def joystick(int x, int y, int b) {
        ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createJoystick(x, y, b)
        init_joystick()
    }

    def button(int x) {
        ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createButton(x)
    }

    def init_joystick() {
        joystick left means
        _ lcd display "left"

        joystick right means
        _ lcd display "right"

        joystick up means
        _ lcd display "up"

        joystick down means
        _ lcd display "down"

        joystick pushed means
        _ lcd display "pushed"

        initial neutral
    }

    def joystick(Direction direction) {
        if(! neutral_added) {
            state "neutral" means
            _ lcd display "waiting input"
            neutral_added = true;
        }
        switch(direction) {
            case left :
                state "left" means

                from left to neutral when
                _ joystickX lt 700

                from neutral to left when
                _ joystickX gt 700
                break;
            case right :
                state "right" means

                from right to neutral when
                _ joystickX gt 200

                from neutral to right when
                _ joystickX lt 200
                break;
            case up :
                state "up" means

                from up to neutral when
                _ joystickY gt 200

                from neutral to up when
                _ joystickY lt 200
                break;
            case down :
                state "down" means

                from down to neutral when
                _ joystickY lt 700

                from neutral to down when
                _ joystickY gt 700
                break;
            case pushed :
                state "pushed" means

                from pushed to neutral when
                _ joystickB eq 0

                from neutral to pushed when
                _ joystickB ne 0
                break;
        }
        [means: {
            }]
    }

    // state "name" means actuator becomes signal [and actuator becomes signal]*n
    def state(String name) {
        ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createState(name)
        [means: {
            }]
    }

    def lcd(n) {
        ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().createLCD(n)
    }

    def _(LCD lcd) {
        [display: { message ->
                ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(lcd, message)
            }]
    }

    def _(Actuator actuator) {
        [becomes: { signal ->
                ((ArduinoMLBinding)this.getBinding()).getGroovuinoMLModel().addActionToLastState(actuator, signal)
            }]
    }

    def _(Sensor sensor) {
        [eq: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.EQ)},
            ne: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.NE)},
            lt: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.LT)},
            gt: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.GT)},
            le: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.LE)},
            ge: {n -> ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createCondition(sensor, n, BinaryOperator.GE)}]
    }

    // initial state
    def initial(State state) {
        ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().setInitialState(state)
    }

    // from state1 to state2 when sensor becomes signal
    def from(State state1) {
        [to: { state2 ->
                ((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().createTransition(state1,state2)
                [when: {
                    }]}]
    }


    // export name
    def export(String name) {
        println(((ArduinoMLBinding) this.getBinding()).getGroovuinoMLModel().generateCode(name).toString())
    }
}
