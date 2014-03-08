const int ledPin=13;                     //Pin led to 13.
const int cycleTime=10;                  //The cycle of each shine. T.

int brightness=1;                        //brightness level:1-10 (initial is 1);
int loopCount=0;                         //loop count, one brightness level should have 20 cycles.
int mode=1;                              //mode 1 is increase brightness, -1 to decrease.

void setup()
{
  pinMode(ledPin,OUTPUT);
  Serial.begin(9600);
 // Serial.println("in");
}

void loop()
{
loopCount++;
if (loopCount<20)
{ 
 //brightness=(brightness+1) % 10;
 digitalWrite(ledPin,HIGH);
 delay(cycleTime/10*brightness);
 digitalWrite(ledPin,LOW);
 delay((cycleTime/10*(10-brightness)));
// Serial.println(loopCount);
}

else 
  {
   loopCount=0;
   brightness=(brightness+1*mode)%10;
   if (brightness<=1) 
     {
       brightness=1;
       mode=1;
     }
   if (brightness>=9)
   {
     brightness=9;
     mode=-1;
    // Serial.println("to -1");
   }
  // if (brightness==0) brightness=1;
   //Serial.println(brightness);
  }
}
