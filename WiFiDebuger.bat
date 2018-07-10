@echo off
:adb_control
title adb 无线调试
echo,
echo --------- adb 无线调试 ---------
echo,
echo  1 连接adb进行无线调试
echo,
echo  2 断开无线连接
echo,
echo  3 断开所有连接
echo,
echo  0 退出
echo,
echo --------------------------------

echo,
set/p a=请选择：
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
