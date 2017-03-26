#!/bin/sh

# start docker container
docker run -d --name="vault-spring-demo-1" --cap-add=IPC_LOCK -p "8200:8200" -e VAULT_DEV_ROOT_TOKEN_ID=00000000-0000-0000-0000-000000000000 vault server -dev

# sleep 3 seconds to give Vault some time to settle
sleep 3

# write api values
export VAULT_ADDR="http://localhost:8200"
export VAULT_TOKEN="00000000-0000-0000-0000-000000000000"
vault write secret/openweather/api key=${OPENWEATHER_API_KEY} ttl=1h
