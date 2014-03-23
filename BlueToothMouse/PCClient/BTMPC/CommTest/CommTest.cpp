// CommTest.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "stdio.h"
#include <string>
#include "windows.h"
#include <iostream>
using namespace std;

int _tmain(int argc, _TCHAR* argv[])
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
	LPDCB lpDCB=&dcb;
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

	PurgeComm(hCom, PURGE_TXCLEAR|PURGE_RXCLEAR);
	cout << "Config Comm ok...." << endl;


	//IO Comm.
	char readBuf[100];
	DWORD wCount;
	BOOL bReadStat;

	do
	{
		
		wCount = 0;
		bReadStat = ReadFile(hCom, readBuf, 4, &wCount, NULL);
		if (bReadStat && wCount==4)
		{
			for (int i = 0; i < wCount; i++)
				cout << readBuf[i];
			cout << endl;
			PurgeComm(hCom,PURGE_RXCLEAR);

		}
		Sleep(1000);
	} while (true);
	system("pause");
	return 0;
}

