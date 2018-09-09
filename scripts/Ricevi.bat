@rem  *************** CLIENT RICEZIONE Movimentazioni PINO ***********************
@REM  *************** 
@REM  Da chiamare con un'attività pianificata ogni 5 minuti  
@REM  via startRicevi.bat per poter avere il log
@REM  *************** 

@echo ************************* %DATE% %TIME%  *************************

@rem echo Connecting remote drive ...

if exist w:\ net use w: /DELETE /yes

net use w: \\itpvssrv005\movimentazioni

@rem echo Checking file ...

@echo off

for %%a in ( c:\pesi\stor_db03.mdb ) do set tf1=%%~ta
for %%a in ( w:\stor_db03.mdb ) do set tf2=%%~ta

@rem echo Local File Time: %tf1% - Remote File Time: %tf2%

@if "%tf1%" equ "%tf2%" echo Nothing to do && goto :end

@c:
@cd \Users\dogadmin\Desktop
@echo on

wget -O - "http://ITPVSWKS25002/DoganaPV/.main?cmd=lockMdb&lock=1"

copy w:\anagrafiche.mdb c:\pesi\anagrafiche.mdb /y
copy w:\stor_db03.mdb c:\pesi\stor_db03.mdb /y

wget -O - "http://ITPVSWKS25002/DoganaPV/.main?cmd=lockMdb&lock=0"


:end
net use w: /DELETE /yes
