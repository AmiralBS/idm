// Wiring code generated from an ArduinoML model
// Application name: Very Simple Alarm


void setup(){
  pinMode(9, INPUT); // button [DigitalSensor]
  pinMode(11, OUTPUT); // led [DigitalActuator]
}

long time = 0; long debounce = 200;

int state_on() {
  digitalWrite(11,HIGH);

  boolean guard = millis() - time > debounce;
  if(digitalRead(9) == 0 && guard) {
    time = millis();
    return 2; // to off();
  } else {
    return 1; // to on();
  }
}

int state_off() {
  digitalWrite(11,LOW);

  boolean guard = millis() - time > debounce;
  if(digitalRead(9) == 1 && guard) {
    time = millis();
    return 1; // to on();
  } else {
    return 2; // to off();
  }
}

int state = 2;
void loop() {
  switch(state) {
    case 1:
      state = state_on();
      break;
    case 2:
      state = state_off();
      break;
    default:
      break;
  }
}

