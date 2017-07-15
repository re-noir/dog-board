#!/bin/bash

read -s -p "mysql root 패스워드  : " rootpass

username='dogpig'
password='dogpig'

# drop database
mysql -u root -p$rootpass -e "DROP DATABASE IF EXISTS dogpigdb;"
mysql -u root -p$rootpass -e "DROP DATABASE IF EXISTS dogpigdb_test;"

# create database
mysql -u root -p$rootpass -e "CREATE DATABASE dogpigdb;"
mysql -u root -p$rootpass -e "CREATE DATABASE dogpigdb_test;"

#re create user and grant
mysql -u root -p$rootpass -e "DROP USER $username;"
mysql -u root -p$rootpass -e "CREATE USER '$username'@'%' IDENTIFIED BY '$password'";

mysql -u root -p$rootpass -e "GRANT ALL ON  dogpigdb.* to $username;"
mysql -u root -p$rootpass -e "GRANT ALL ON  dogpigdb_test.* to $username; FLUSH PRIVILEGES;"



# restoring database structures and data.
mysql -u root -p$rootpass dogpigdb < DUMP/dump_db.sql
mysql -u root -p$rootpass dogpigdb_test < DUMP/structure_only.sql

exitStatus=$?
if [ $exitStatus -ne 0 ]; then
    exit $exitStatus
fi

exit 0


