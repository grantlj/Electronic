/*
 Drive HC-05 System.
 Bluetooth Mouse.
 Author: Grant Liu
 */
 
#include <SoftwareSerial.h>
int ledPin=13;
int TxPin=3;
int RxPin=2;
String str="";
int strlength=0;
SoftwareSerial blueToothSerial(RxPin,TxPin);

void setup()
{
  Serial.begin(9600);
  
  pinMode(ledPin,OUTPUT);
  pinMode(TxPin,OUTPUT);
  pinMode(RxPin,INPUT);
  
  digitalWrite(ledPin,LOW);
  
  blueToothSerial.begin(9600);
}

void loop()
{
  boolean flag=false;

  while (blueToothSerial.available())
  {
    digitalWrite(ledPin,HIGH);
    flag=true;
    str+=(char)blueToothSerial.read();
    strlength++;
    
 if (flag && strlength==9) 
 {
   Serial.print('a');
   Serial.print(str);
   Serial.print('b');
   str="";
   strlength=0;
   flag=false;
 }
  }
  digitalWrite(ledPin,LOW);
 

}


