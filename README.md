# pivotal-hawq-jdbc
Acess Pivotal HAWQ using JDBC 

An example on how you can query CSV files on Hadoop using HAWQ. 
This does require HAWQ to be installed on the cluster.

#Step 1
git clone git@github.com:amithn/pivotal-hawq-jdbc.git

#Step 2 
Create a file called scores.txt which has an id and score and looks like below

`1,10
2,20
9,25`

#Step 3
Copy it onto HDFS like:
`hadoop fs -copyFromLocal scores.txt /user/{some dir on HDFS}/`

#Step 4
Run `HAWQJDBCApp` from an IDE 

That would create an external table using the following DDL:
--

`CREATE EXTERNAL TABLE tablename (id text, score int) 
LOCATION('pxf://NAMENODE-URI:50070/user/{some dir on HDFS}//scores.txt?profile=HdfsTextSimple')
FORMAT 'CSV'  (DELIMITER = ',')`

#Step 5
Finally the app queries the data in the table it just created. 




