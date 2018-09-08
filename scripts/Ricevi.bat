@rem  *************** CLIENT RICEZIONE Movimentazioni PINO ***********************
@REM  *************** 
@REM  Da chiamare con un'attività pianificata ogni 5 minuti  
@REM
DATE /t
TIME /t
@rem  ***************

@echo Connecting remote drive ...

net use s: /DELETE
net use s: \\itpvssrv005\movimentazioni

@echo Checking file ...

@for %%a in ( c:\pesi\stor_db03.mdb ) do set tf1=%%~ta

@for %%a in ( s:\stor_db03.mdb ) do set tf2=%%~ta

@echo.
@echo Local File Time: %tf1% - Remote File Time: %tf2%
@echo.

@if "%tf1%" equ "%tf2%" echo Nothing to do && goto :end


@c:
@cd \Users\dogadmin\Desktop


wget -O - "http://ITPVSWKS25002/DoganaPV/.main?cmd=lockMdb&lock=1"

copy s:\anagrafiche.mdb c:\pesi\anagrafiche.mdb /y
copy s:\stor_db03.mdb c:\pesi\stor_db03.mdb /y

wget -O - "http://ITPVSWKS25002/DoganaPV/.main?cmd=lockMdb&lock=0"


:end
net use s: /DELETE
