# Simple Vault Development Server

In this demo you'll spin up a simple development server that already comes initialized and unsealed and will allow you to immediately get started.

1. Start the docker container by calling `./start.sh` 
1. This will start the vault server inside of a docker container in the background. You should look into the output of the container:

   ```
   $ docker logs vault-dev
   ==> Vault server configuration:

                     Cgo: disabled
         Cluster Address: https://0.0.0.0:8201
              Listener 1: tcp (addr: "0.0.0.0:8200", cluster address: "0.0.0.0:8201", tls: "disabled")
               Log Level: info
                   Mlock: supported: true, enabled: false
        Redirect Address: http://0.0.0.0:8200
                 Storage: inmem
                 Version: Vault v0.7.0
             Version Sha: 614deacfca3f3b7162bbf30a36d6fc7362cd47f0

   2017/04/03 20:59:23.660777 [INFO ] core: security barrier not initialized
   2017/04/03 20:59:23.660992 [INFO ] core: security barrier initialized: shares=1 threshold=1
   2017/04/03 20:59:23.661116 [INFO ] core: post-unseal setup starting
   2017/04/03 20:59:23.674100 [INFO ] core: loaded wrapping token key
   2017/04/03 20:59:23.676244 [INFO ] core: successfully mounted backend: type=generic path=secret/
   ==> WARNING: Dev mode is enabled!

   In this mode, Vault is completely in-memory and unsealed.
   Vault is configured to only have a single unseal key. The root
   token has already been authenticated with the CLI, so you can
   immediately begin using the Vault CLI.

   The only step you need to take is to set the following
   environment variables:

      export VAULT_ADDR='http://0.0.0.0:8200'

   The unseal key and root token are reproduced below in case you
   want to seal/unseal the Vault or play with authentication.

   Unseal Key: OggCPZqkjh3C2qnlWtZ9UpqeRvUvfa5v2qbdFYvZjts=
   Root Token: 843c15e9-c3bc-f7d2-7a03-edca88b05c57

     ==> Vault server started! Log data will stream in below:

   2017/04/03 20:59:23.676259 [INFO ] core: successfully mounted backend: type=cubbyhole path=cubbyhole/
   2017/04/03 20:59:23.676333 [INFO ] core: successfully mounted backend: type=system path=sys/
   2017/04/03 20:59:23.676507 [INFO ] rollback: starting rollback manager
   …
   ```

1. set the vault address and token by 

   ```
   $ export VAULT_ADDR=http://127.0.0.1:8200
   $ export VAULT_TOKEN=843c15e9-c3bc-f7d2-7a03-edca88b05c57
   ```

   **Note**: you shouldn't use the given address of `0.0.0.0` cause it will not work. The root token is being created by the vault server during startup in development mode. Therefore you'll have too check the docker logs.

1. Try writing to and reading from vault

   ```
   $ vault write secret/myapp foo=bar
   Success! Data written to: secret/myapp
   $ vault read secret/myapp
   Key             	Value
   ---             	-----
   refresh_interval	768h0m0s
   foo             	bar

   ```
   
   You'll see that vault is responing to your read request not only with the key you set but also with a default `refresh_interval` that is about a month.
   
1. Try setting some other values

   ```
   $ vault write secret/myapp foo=baz refresh_interval=1h
   Success! Data written to: secret/myapp
   $ vault read secret/myapp
   Key             	Value
   ---             	-----
   refresh_interval	768h0m0s
   foo             	baz
   refresh_interval	1h

   ```
   As you can see the value for `foo` was overwritten but the `refresh_interval` was just taking as an additional attribute rather than taking this into account. To change this value we'll have to set a different value
   
   ```
   $ vault write secret/myapp foo=bay ttl=1h
   Success! Data written to: secret/myapp
   $ vault read secret/myapp
   Key             	Value
   ---             	-----
   refresh_interval	1h0m0s
   foo             	bay
   ttl             	1h
   ```

   Now the `refresh_interval` was adapted to the `ttl` which was given for this secret. You should in any case check the [official documentation](https://www.vaultproject.io/docs/index.html) to see other option.
   
1. Be sure to clean up after you by calling `./stop.sh`
