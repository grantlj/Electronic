/*
  ledCount,ledPin define the count of leds and that's pin.
*/
const int ledCount = 4;
const int ledPin[] = {10,11,12,13};

/*
  row,col define the pin of 4*4 keyborad.
*/
const int rolCount = 4;
const int colCount = 4;
const int rolPin[] = {0,1,2,3};
const int colPin[] = {7,6,5,4};

/*
  define key sets here.
*/
const char keys[][] = {{'1','2','3','F'},
	{'4','5','6','E'},
	{'7','8','9','D'},
	{'A','0','B','C'}};




void setup()
{
	Serial.begin(9600);	
  for (int i=0;i<ledCount;i++)
		pinMode(ledPin[i], OUTPUT);
	welcome();
}

void loop()
{
	/*
	  if key returns AB.
	  row is stored in A;
      col is stored in B;
    */

	int key = scanKey();

	
	if (key != -1)
	{
		Serial.println(key); 	 //For debug.
		int row = key / 10;
		int col = key % 10;
	
		doShine(row, col);
	}
}


/*
  shine specific lights.
*/
void doShine(int row,int col)
{
	
	Serial.println("in!"+row+" "+col); //For debug.
	//Get keys and shine.
	char c = keys[row][ col];
	for (int i=0;i < ledCount;i++)
		digitalWrite(ledPin[i], LOW);
	
	if (c >= '0' && c <= '9')
	//is number.
		for (int i=1;i <= c - '0';i++)
		{
			for (int j=0;j < ledCount;j++)
				digitalWrite(ledPin[j], HIGH);
			delay(200);
			for (int j=0;j < ledCount;j++)
				digitalWrite(ledPin[j], LOW);
			delay(200);
		}
	
	else
	{
		//is alphabet.
		Serial.println("ALPHABET");
		for (int i=0;i < ledCount;i++)
			digitalWrite(ledPin[i], HIGH);
		delay(3000);
		for (int i=0;i < ledCount;i++)
			digitalWrite(ledPin[i], LOW);
	}
	
	delay(5000);
			
		
}

/*
  return current key.
  -1 if not found.
*/
int scanKey()
{
	initPort();
	int col = getCol();
//	Serial.print("getCol:" + col + " ");
	
//	Serial.println();
	if (col != -1)
	{
		int row = getRow(col);
		if (row!=-1) return row * 10 + col;
	}
	return -1;
}


//Second, we get row.
int getRow(int col)
{
	for (int i=0;i < rolCount;i++)
	{
	  for (int j=0;j<rolCount;j++)
			if (i != j) digitalWrite(rolPin[j], HIGH);
			else digitalWrite(rolPin[j], LOW);
		if (digitalRead(colPin[col]) == LOW) return i;
	}
	return -1;
}


//First, we get Col.	
int getCol()
	{
		for (int i=0;i < colCount;i++)
		{
			//	Serial.println("col "+i+" is:"+digitalRead(colPin[i]));
			if (digitalRead(colPin[i]) == LOW) 
				return i;
		}
		return -1;
	
	}


/*
  init the port and set it to default status
*/
void initPort()
{
	for (int i=0;i < colCount;i++)
	{
		pinMode(colPin[i], OUTPUT);
		digitalWrite(colPin[i], HIGH);
		//	pinMode(colPin[i], INPUT);
		//	Serial.println("col " + i + ":"+"is:"+digitalRead(colPin[i]));
	}

	for (int i=0;i < rolCount;i++)
	{
		pinMode(rolPin[i], OUTPUT);
		digitalWrite(rolPin[i], LOW);
	}
}

/*
  welcome interface.
*/	
void welcome()
{
	for (int i=0;i < ledCount;i++)
	{
		digitalWrite(ledPin[i], HIGH);
		delay(1000);
	}

	for (int i=0;i < ledCount;i++)
	{
		digitalWrite(ledPin[ledCount - 1 - i], LOW);
		delay(500);
	}
}