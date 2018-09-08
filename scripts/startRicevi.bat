@echo off
REM  *************** 
REM  Esegue Ricevi.bat e redirige LOG 
REM  Da chiamare con un'attività pianificata ogni 5 minuti  
REM  *************** 


c:
cd \Users\dogadmin\desktop

ricevi.bat >> ricevi_bat.log 2>&1