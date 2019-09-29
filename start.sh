#!/bin/bash

# Bash script to run when starting Docker image


# Start the postgres server
/usr/lib/postgresql/11/bin/postgres -D /var/lib/postgresql/11/main -c config_file=/etc/postgresql/11/main/postgresql.conf &

echo "fdsa"

# Wait for database to start
sleep 5
# Restore database backup
psql docker  < database_backup.sql

# Keep docker running
tail -f /dev/null