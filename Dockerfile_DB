FROM mysql:oraclelinux9                                                                                                                                       
COPY db.sql /docker-entrypoint-initdb.d/                                      
ENV MYSQL_ROOT_PASSWORD="hello"                                                                                                                               
ENV MYSQL_DATABASE="insurance_db" 
