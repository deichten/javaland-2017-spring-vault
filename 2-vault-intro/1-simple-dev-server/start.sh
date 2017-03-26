#!/bin/sh
docker run -d --name=vault-dev --cap-add=IPC_LOCK -p 8200:8200 vault
