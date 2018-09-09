@echo off
REM  *************** 
REM  Esegue Ricevi.bat e redirige LOG 
REM  Da chiamare con un'attività pianificata ogni 5 minuti  
REM  *************** 

set Y=%DATE:~6,4%
set M=%DATE:~3,2%

c:
cd \Users\dogadmin\desktop

ricevi.bat >> ricevi_%Y%-%M%.log 2>&1