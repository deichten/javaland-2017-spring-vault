#!/bin/sh
PWD=`pwd`
docker run -d --name=vault-file --cap-add=IPC_LOCK \
   -v ${PWD}/config:/vault/config \
   -v ${PWD}/audit:/vault/audit:rw \
   -p 8200:8200 \
   vault server
