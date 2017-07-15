#!/bin/bash

read -s -p "mysql root 패스워드  : " rootpass
mysqldump --no-data -u root -p$rootpass --skip-dump-date dogpigdb > DUMP/structure_only.sql
mysqldump -u root -p$rootpass --skip-dump-date dogpigdb | sed 's$VALUES ($VALUES\
($g' | sed 's$),($),\
($g' > DUMP/dump_db.sql
mysqldump --no-create-info -u root -p$rootpass  --skip-dump-date dogpigdb | sed 's$VALUES ($VALUES\
($g' | sed 's$),($),\
($g' > DUMP/data_only.sql
