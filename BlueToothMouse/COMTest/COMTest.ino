int led=13;
int interval=1000;
void setup()
{
  pinMode(led,OUTPUT);
  digitalWrite(led,LOW);
  Serial.begin(9600);
}

void loop()
{
  Serial.println(interval);
  delay(10);
  digitalWrite(led,HIGH);
  delay(interval);
  digitalWrite(led,LOW);
  delay(1000);
  char c;
  String str="";
  while (Serial.available())
  {
    c=(char) Serial.read();
    str+=c;
  }
  
  if (str!="")
    interval=str.toInt(); 
  
  
}
