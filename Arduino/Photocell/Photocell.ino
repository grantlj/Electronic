int tempPin=0;
int state=0;
int freq=5;
int ledPin=13;

unsigned long timeBase;

boolean flag=false;
boolean totalflag=false;

void setup()
{
  Serial.begin(9600);
  pinMode(ledPin,OUTPUT);
 
 digitalWrite(ledPin,HIGH);
 delay(3000);
 digitalWrite(ledPin,LOW);
 // pinMode(lightPin,INPUT);
}

void loop()
{
  
  flag=false;
  String args="";
  while (Serial.available()>0)
  {
    char c=Serial.read();
    totalflag=true;
    flag=true;
    args=args+c;
    delay(2);
  }
  
  if (flag)
  {
       state=(args.toInt())%10; 
       
       if (state==1)
       {
         freq=args.toInt()/10;
         timeBase=millis();
         digitalWrite(ledPin,HIGH);
       }
       else
        digitalWrite(ledPin,LOW);
  }
  
  if (state==1)
    {
      float temperature=analogRead(tempPin)*0.0048828125*100;
      unsigned long timeNow=millis();
      Serial.print("aaaaa");
      Serial.print((timeNow-timeBase)/1000);
      if ((timeNow-timeBase)/1000>32760)
        timeBase=0;
      Serial.print(" ");
      Serial.print(temperature);
      Serial.print("bbbbb");
      delay(freq*1000);
    }

//if (totalflag) Serial.println(state);
}
