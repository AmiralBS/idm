#!/bin/bash

java -jar ../dsl-groovy/target/arduinoml.dsl-groovy-1.0-jar-with-dependencies.jar $1 > outputs/$2.ino
