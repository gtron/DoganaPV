@"C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe" -u %1 -p%2 magazzini < %5\%6 

@rem "C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql_config_editor.exe" -v print --all

@rem NON FUNZIONA "C:\Program Files\MySQL\MySQL Server 5.7\bin\mysql.exe" --login-path=local magazzini < %5\%6 