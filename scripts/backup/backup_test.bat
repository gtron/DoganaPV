;@echo off
echo Starting
set D=%DATE:~6,4%%DATE:~3,2%%DATE:~0,2%
set T=%TIME:~0,2%%TIME:~3,2%%TIME:~6,2%

set T=%TIME:~0,2%%TIME:~3,2%
set T=%T: =_%
set DIR=C:\Backups\Database\
set BASE=%DIR%mag-test_
set LAST=%BASE%last-test.sql
set FILE=%BASE%%D%_%T%.sql
set FILEG=%BASE%%D%.sql

if EXIST %FILE% copy %FILE% %FILE%.1
"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysqldump.exe" -u mag_test -pm4gtest mag_test > %LAST%
copy %LAST% %FILE%
if not exist %FILEG% goto GIORNALIERO 

goto DONE 

:GIORNALIERO
copy %FILE% %FILEG%

del %BASE%*_*.sql

:DONE

 