@echo off
set container_name=bank-service-db
set /A db_start_delay=15

docker-compose up -d %container_name%

timeout /t %db_start_delay%

docker cp ./initial-scripts/init-database.sql %container_name%:opt/init-database.sql
docker exec %container_name% //opt/mssql-tools/bin/sqlcmd -S 127.0.0.1 -U sa -P bank-service-password -d master -i //opt/init-database.sql

echo "Created databases in the MSSQL"

call start.bat

echo "Local development ENV is ready"