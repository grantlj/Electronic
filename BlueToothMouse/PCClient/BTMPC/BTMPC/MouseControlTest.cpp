// BTMPC.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "windows.h"
#include "atltypes.h"


int _tmain(int argc, _TCHAR* argv[])
{
	CPoint point;
	printf("Test mouse move event:\n");

	

		GetCursorPos(&point);
		SetCursorPos(20,20);
		printf("cursor pos, x:%d, y:%d\n", point.x, point.y);
		system("pause");
	

	printf("Test single click event:\n");
	system("pause");
	mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, NULL);
	mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, NULL);

	printf("Test right click event:\n");
	system("pause");
	mouse_event(MOUSEEVENTF_RIGHTDOWN, 0, 0, 0,NULL);
	mouse_event(MOUSEEVENTF_RIGHTUP, 0, 0, 0,NULL);

	printf("Test double click event;\n");
	system("pause");
	for (int i = 0; i < 2; i++)
	{
		mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0,0, NULL);
		mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0,NULL);
		Sleep(200);
	}
	system("pause");
	  return 0;
}

