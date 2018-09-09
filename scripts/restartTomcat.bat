@echo off
net stop Tomcat8

REM Sleep 500ms
ping -n 5 -w 100 127.0.0.1 > nul
net start Tomcat8