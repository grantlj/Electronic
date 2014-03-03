import com.virtualbreadboard.interfaces.*;
import com.muvium.compatibility.arduino.*; 
class Class0 extends com.muvium.compatibility.arduino.Arduino{//Automatically Added VBB Framework Code - do not remove
/* //!>0:0
  ledCount,ledPin define the count of leds and that's pin. //!>0:1
*/ //!>0:2
static final int ledCount = 4; //!>0:3
static final int ledPin[] = {10,11,12,13}; //!>0:4
 //!>0:5
/* //!>0:6
  row,col define the pin of 4*4 keyborad. //!>0:7
*/ //!>0:8
static final int rolCount = 4; //!>0:9
static final int colCount = 4; //!>0:10
static final int rolPin[] = {0,1,2,3}; //!>0:11
static final int colPin[] = {7,6,5,4}; //!>0:12
 //!>0:13
/* //!>0:14
  define key sets here. //!>0:15
*/ //!>0:16
static final char keys[][] = {{'1','2','3','F'}, //!>0:17
	{'4','5','6','E'}, //!>0:18
	{'7','8','9','D'}, //!>0:19
	{'A','0','B','C'}}; //!>0:20
 //!>0:21
 //!>0:22
 //!>0:23
 //!>0:24

public void setup() //!>0:25
{ //!>0:26
	Serial.begin(9600);	 //!>0:27
  for (int i=0;i<ledCount;i++) //!>0:28
		pinMode(ledPin[i], OUTPUT); //!>0:29
	welcome(); //!>0:30
} //!>0:31
 //!>0:32
public void loop() //!>0:33
{ //!>0:34
	/* //!>0:35
	  if key returns AB. //!>0:36
	  row is stored in A; //!>0:37
      col is stored in B; //!>0:38
    */ //!>0:39
 //!>0:40
	int key = scanKey(); //!>0:41
 //!>0:42
	 //!>0:43
if( iff (key != -1)) //!>0:44
	{ //!>0:45
		Serial.println(key); 	 //For debug. //!>0:46
		int row = key / 10; //!>0:47
		int col = key % 10; //!>0:48
	 //!>0:49
		doShine(row, col); //!>0:50
	} //!>0:51
} //!>0:52
 //!>0:53
 //!>0:54
void doShine(int row,int col) //!>0:55
{ //!>0:56
	 //!>0:57
	Serial.println("in!"+row+" "+col); //For debug. //!>0:58
	//Get keys and shine. //!>0:59
	char c = keys[row][ col]; //!>0:60
	for (int i=0;i < ledCount;i++) //!>0:61
		digitalWrite(ledPin[i], LOW); //!>0:62
	 //!>0:63
if( iff (c >= '0' && c <= '9')) //!>0:64
	//is number. //!>0:65
		for (int i=1;i <= c - '0';i++) //!>0:66
		{ //!>0:67
			for (int j=0;j < ledCount;j++) //!>0:68
				digitalWrite(ledPin[j], HIGH); //!>0:69
			delay(200); //!>0:70
			for (int j=0;j < ledCount;j++) //!>0:71
				digitalWrite(ledPin[j], LOW); //!>0:72
			delay(200); //!>0:73
		} //!>0:74
	 //!>0:75
	else //!>0:76
	{ //!>0:77
		//is alphabet. //!>0:78
		Serial.println("ALPHABET"); //!>0:79
		for (int i=0;i < ledCount;i++) //!>0:80
			digitalWrite(ledPin[i], HIGH); //!>0:81
		delay(3000); //!>0:82
		for (int i=0;i < ledCount;i++) //!>0:83
			digitalWrite(ledPin[i], LOW); //!>0:84
	} //!>0:85
	 //!>0:86
	delay(5000); //!>0:87
			 //!>0:88
		 //!>0:89
} //!>0:90
 //!>0:91
int scanKey() //!>0:92
{ //!>0:93
	initPort(); //!>0:94
	int col = getCol(); //!>0:95
//	Serial.print("getCol:" + col + " "); //!>0:96
	 //!>0:97
//	Serial.println(); //!>0:98
if( iff (col != -1)) //!>0:99
	{ //!>0:100
		int row = getRow(col); //!>0:101
if( iff (row!=-1)) return row * 10 + col; //!>0:102
	} //!>0:103
	return -1; //!>0:104
} //!>0:105
 //!>0:106
 //!>0:107
//Second, we get row. //!>0:108
int getRow(int col) //!>0:109
{ //!>0:110
	for (int i=0;i < rolCount;i++) //!>0:111
	{ //!>0:112
	  for (int j=0;j<rolCount;j++) //!>0:113
if( iff (i != j)) digitalWrite(rolPin[j], HIGH); //!>0:114
			else digitalWrite(rolPin[j], LOW); //!>0:115
if( iff (digitalRead(colPin[col]) == LOW)) return i; //!>0:116
	} //!>0:117
	return -1; //!>0:118
} //!>0:119
 //!>0:120
 //!>0:121
//First, we get Col.	 //!>0:122
int getCol() //!>0:123
	{ //!>0:124
		for (int i=0;i < colCount;i++) //!>0:125
		{ //!>0:126
			//	Serial.println("col "+i+" is:"+digitalRead(colPin[i])); //!>0:127
if( iff (digitalRead(colPin[i]) == LOW))  //!>0:128
				return i; //!>0:129
		} //!>0:130
		return -1; //!>0:131
	 //!>0:132
	} //!>0:133
 //!>0:134
void initPort() //!>0:135
{ //!>0:136
	for (int i=0;i < colCount;i++) //!>0:137
	{ //!>0:138
		pinMode(colPin[i], OUTPUT); //!>0:139
		digitalWrite(colPin[i], HIGH); //!>0:140
		//	pinMode(colPin[i], INPUT); //!>0:141
		//	Serial.println("col " + i + ":"+"is:"+digitalRead(colPin[i])); //!>0:142
	} //!>0:143
 //!>0:144
	for (int i=0;i < rolCount;i++) //!>0:145
	{ //!>0:146
		pinMode(rolPin[i], OUTPUT); //!>0:147
		digitalWrite(rolPin[i], LOW); //!>0:148
	} //!>0:149
} //!>0:150
	 //!>0:151
void welcome() //!>0:152
{ //!>0:153
	for (int i=0;i < ledCount;i++) //!>0:154
	{ //!>0:155
		digitalWrite(ledPin[i], HIGH); //!>0:156
		delay(1000); //!>0:157
	} //!>0:158
 //!>0:159
	for (int i=0;i < ledCount;i++) //!>0:160
	{ //!>0:161
		digitalWrite(ledPin[ledCount - 1 - i], LOW); //!>0:162
		delay(500); //!>0:163
	} //!>0:164
} //!>0:165

public static void main(String[] args ){ 
  new Class0().run(); }
};
