#!/bin/sh

# start docker containers in the right order using compose
docker-compose up -d

# sleep to wait for mysql
echo "sleeping 30s to give MySQL some time to sping up"
sleep 30

# configure mysql backend
export VAULT_ADDR="http://localhost:8200"
export VAULT_TOKEN="c2fbf07b-d1e7-64f4-8a74-08473d29c1cd"
vault mount mysql
vault write mysql/config/connection connection_url="root:secret@tcp(mysql:3306)/"
vault write mysql/config/lease lease=5m lease_max=15m
vault write mysql/roles/readonly sql="CREATE USER '{{name}}'@'%' IDENTIFIED BY '{{password}}'; GRANT SELECT ON *.* TO '{{name}}'@'%';"
