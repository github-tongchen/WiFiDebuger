@echo off
:adb_control
title adb ���ߵ���
echo,
echo --------- adb ���ߵ��� ---------
echo,
echo  1 ����adb�������ߵ���
echo,
echo  2 �Ͽ���������
echo,
echo  3 �Ͽ���������
echo,
echo  0 �˳�
echo,
echo --------------------------------

echo,
set/p a=��ѡ��
call:%a% ""
goto adb_control
:1 
adb connect 192.168.0.111:7777
goto :eof
:=2 
adb disconnect 192.168.0.111:7777
goto :eof
:3 
adb disconnect
goto :eof
:0 
exit
