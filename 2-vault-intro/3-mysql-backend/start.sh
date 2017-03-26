#!/bin/sh

# start the docker containers
docker-compose up -d

# sleep some time to give mysql the time to spin up
echo "sleeping 30s to give mysql time to settle after being started."
sleep 30

# configure mysql backend
export VAULT_ADDR="http://localhost:8200"
export VAULT_TOKEN="00000000-0000-0000-0000-000000000000"
vault mount mysql
vault write mysql/config/connection connection_url="root:secret@tcp(mysql:3306)/"
vault write mysql/config/lease lease=5m lease_max=15m
vault write mysql/roles/readonly sql="CREATE USER '{{name}}'@'%' IDENTIFIED BY '{{password}}'; GRANT SELECT ON *.* TO '{{name}}'@'%';"
