// blanco 5v
// negro Ground
// azul digital 6 - Boton
// gris A0 - Eje X
// morado A1 - Eje Y

#include "Mouse.h"

const int mouseButton = 6;  // Boton del joystick
const int xPin = A0;        // eje X del joystick
const int yPin = A1;        // eje Y del joystick
const int ledPin = 5;       // Led de Funcionamiento

int range = 12;             // Rango de Movimiento
int Delay = 5;              // Tiempo de Respuesta
int threshold = range / 4;  // Limites de movimiento
int center = range / 2;     // resting position value



void setup() {
  pinMode(ledPin, OUTPUT); 
  pinMode(mouseButton, INPUT);
  digitalWrite(mouseButton, HIGH);
  Mouse.begin();
  digitalWrite(ledPin, HIGH); //Indica que el joystick esta operativo
}

void loop() {

  int xVal = readVal(xPin); //Asigna el valor del eje en X
  int yVal = readVal(yPin); //Asigna el valor del eje en X
  int buttonVal = digitalRead(mouseButton); //Lee el valor del boton

  Mouse.move(xVal, yVal, 0); //Mueve el cursor con los valores asignados
  
  //Activa el click si el boton del joystick es presionado
  if (digitalRead(mouseButton) == 0) {
    //Presiona el boton si no lo esta
    if (!Mouse.isPressed(MOUSE_LEFT)) {
      Mouse.press(MOUSE_LEFT);
    }
  }
  //
  else {
    //Si el boton esta presionado, realiza la accion
    if (Mouse.isPressed(MOUSE_LEFT)) {
      Mouse.release(MOUSE_LEFT);
    }
  }
  //Tiempo de respuesta entre iteraciones del loop
  delay(Delay);
}


int readVal(int thisAxis) {
  //Lee el valor analogico del eje dado en la funcion
  int reading = analogRead(thisAxis);

  //Convierte la lectura analogica en un rango de valores mas pequeños
  reading = map(reading, 0, 1023, 0, range);


  int distance = reading - center;

  if (abs(distance) < threshold) {
    distance = 0;
  }

  //retorna la distancia del eje dado
  return distance;
}
