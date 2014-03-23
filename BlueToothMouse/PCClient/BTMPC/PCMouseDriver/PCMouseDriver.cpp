// PCMouseDriver.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "stdio.h"
#include <string>
#include "windows.h"
#include "atltypes.h"

#include <iostream>
using namespace std;

const int codeLen = 9;

typedef struct Topt
{
	int opCode;
	int arg1, arg2;
};

HANDLE getCommHandle()
{
	//Open COM.
	HANDLE hCom;
	hCom = CreateFile(_T("COM3"),
		GENERIC_READ | GENERIC_WRITE,
		0,
		NULL,
		OPEN_EXISTING,
		0,
		NULL);

	if (hCom == INVALID_HANDLE_VALUE)
	{
		cout << "Open COMM failed..." << endl;

		system("pause");
		return 0;
	}

	cout << "Open COMM successfully..." << endl;

	//Config comm;
	DCB dcb;
	LPDCB lpDCB = &dcb;
	SetupComm(hCom, 1024, 1024);
	COMMTIMEOUTS TimeOuts;
	TimeOuts.ReadIntervalTimeout = 1000;
	TimeOuts.ReadTotalTimeoutMultiplier = 500;
	TimeOuts.ReadTotalTimeoutConstant = 5000;

	TimeOuts.WriteTotalTimeoutMultiplier = 500;
	TimeOuts.WriteTotalTimeoutConstant = 2000;

	SetCommTimeouts(hCom, &TimeOuts);
	GetCommState(hCom, lpDCB);
	lpDCB->BaudRate = 9600;
	lpDCB->ByteSize = 8;
	lpDCB->Parity = NOPARITY;
	lpDCB->StopBits = TWOSTOPBITS;
	SetCommState(hCom, lpDCB);

	PurgeComm(hCom, PURGE_TXCLEAR | PURGE_RXCLEAR);
	cout << "Config Comm ok...Ready to receive data..." << endl;
	return hCom;

}


void getOpt(Topt* popt, char* buf)
{
	popt->opCode = buf[0] - '0';  //operate type;
	switch (popt->opCode)
	{
	case 1:{
			   //mouse move change.
			   popt->arg1 = ((buf[2] - '0') * 100 + (buf[3] - '0') * 10 + (buf[4] - '0'))*3;  //x pos change.
			   if (buf[1] == '0')
				   popt->arg1 = -popt->arg1;                                            //x pos negative change.

			   popt->arg2 = ((buf[6] - '0') * 100 + (buf[7] - '0') * 10 + (buf[8] - '0'))*3;
			   if (buf[5] == '0')
				   popt->arg2 = -popt->arg2;
			   break;
	}
	case 2:{
			 
			   //single left click.
			   break;
	}
	
	case 3:{
			  //double left click.
			   break;
	}
	
	case 4:{
			   //single right click.
			   break;
	}

	case 5:{
			  //wheel operate.
			   popt->arg1 = (buf[2] - '0') * 100 + (buf[3] - '0') * 10 + (buf[4] - '0');  //rotate operation.
			   if (buf[1] == '1')
				   popt->arg1 = -popt->arg1;                                              //rotate up.
	}
	}

}

void doOpt(Topt opt)
{
	switch (opt.opCode)
	{
	case 1:
	{
			  CPoint point;
			  GetCursorPos(&point);
			  SetCursorPos(point.x + opt.arg1, point.y + opt.arg2);
			  break;
	}

	case 2:
	{
			  mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, NULL);
			  mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, NULL);
			  break;
	}

	case 3:
	{
			  mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, NULL);
			  mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, NULL);
			  break;
	}

	case 4:
	{
			  mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, NULL);
			  mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0, NULL);
			  break;
	}

	case 5:
	{
			  mouse_event(MOUSEEVENTF_WHEEL, 0, 0, opt.arg1, NULL);
			  break;
	}

	}
}

void receiveData(PHANDLE pHcom)
{
	//IO Comm.
	char readBuf[codeLen];
	char t;
	DWORD wCount=0;
	BOOL bReadStat;

	do
	{

		DWORD nowCount = 0;
		bReadStat = ReadFile(*pHcom, &t, 1, &nowCount, NULL);
		if (bReadStat)
		{
			if (t == 'a')
				wCount = 0;
			else if (t == 'b' && wCount==9)
			{
				//do operation here.
				PurgeComm(*pHcom, PURGE_RXCLEAR);
				for (int i = 0; i < wCount; i++)
					cout << readBuf[i];
				cout << endl;
				struct Topt opt;
				getOpt(&opt, readBuf);
				doOpt(opt);
			}
			else
			{
				//is char.
				
				readBuf[wCount] = t;
				wCount++;
			}
		}

	
		
			


         
		
	} while (true);
}


int _tmain(int argc, _TCHAR* argv[])
{
	HANDLE hCom;
	hCom = getCommHandle();
	if (hCom != 0)
		receiveData(&hCom);
	else
	{
		cout << "Error in get Comm Handle...";
		system("puase");
	}

}

