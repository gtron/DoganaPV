for /F "tokens=1-3 delims=./-" %%f in ("date /T") do (
	set _TODAY=%%f
	echo %_TODAY%
)

echo %DATE:~4,2%