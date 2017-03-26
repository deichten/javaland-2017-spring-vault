# Vault server with file backend and audit logging

In this example we'll see how *Vault* can be configured  to store secrets on the disk as well as how it is writing audit information

1. Start the environment by calling `./start.sh`
1. Set the Vault address `export VAULT_ADDR=http://localhost:8200`
1. Check if vault is answering by `vault status`. You should see:

   ```
Error checking seal status: Error making API request.

   URL: GET http://localhost:8200/v1/sys/seal-status
Code: 400. Errors:

   * server is not yet initialized
```
   Since we're not not using the development mode *Vault* is starting as it would in a production way – which means we have to initialize and unseal it first.

1. Initialize the vault server using like this:

   ```
$ vault init
Unseal Key 1: nB0RbOlMYiSwRwaH+5CNj+9YAajc4WpOEmuWOET/at0B
Unseal Key 2: vcO8ZG/dMRkEza+JYszBa5e75gJBsoCYY+HDEBdZ8b8C
Unseal Key 3: ktDmEn8dk5GmhFGhkIeSh/nV5IKezA3+y2viPglKvwsD
Unseal Key 4: 9A6pmCArtxMBzKAguccycqhoe7g2rKq4bd2HaR/nqnwE
Unseal Key 5: 2x3z7jDrFZujhV4IS4xhnsYGeTjp0ifexVemRwH05MgF
Initial Root Token: 2b890511-ee0d-caba-ff6d-147c86b5788e

   Vault initialized with 5 keys and a key threshold of 3. Please
securely distribute the above keys. When the Vault is re-sealed,
restarted, or stopped, you must provide at least 3 of these keys
to unseal it again.

   Vault does not store the master key. Without at least 3 keys,
your Vault will remain permanently sealed.
```
   Per default it generates 5 unseal keys and asks for 3 keys to unseal whenever it was manually sealed or the server gets restarted. You may change this behavior by passing in different values for `key-shares` and `key-threshold`.
   
1. Unseal your vault system by passing in 3 different keys `vault unseal <key>`

   ```
$ vault unseal nB0RbOlMYiSwRwaH+5CNj+9YAajc4WpOEmuWOET/at0B
Sealed: true
Key Shares: 5
Key Threshold: 3
Unseal Progress: 1
Unseal Nonce: 41173e83-dae4-8248-167f-acd98f0e3b41
$ vault unseal vcO8ZG/dMRkEza+JYszBa5e75gJBsoCYY+HDEBdZ8b8C
Sealed: true
Key Shares: 5
Key Threshold: 3
Unseal Progress: 2
Unseal Nonce: 41173e83-dae4-8248-167f-acd98f0e3b41
$ vault unseal 2x3z7jDrFZujhV4IS4xhnsYGeTjp0ifexVemRwH05MgF
Sealed: false
Key Shares: 5
Key Threshold: 3
Unseal Progress: 0
Unseal Nonce: 
$ vault status
Sealed: false
Key Shares: 5
Key Threshold: 3
Unseal Progress: 0
Unseal Nonce: 
Version: 0.6.5
Cluster Name: vault-cluster-ac0e3ce7
Cluster ID: 8b5b35d0-f635-b4bd-8c1b-883e13475e4e

   High-Availability Enabled: false
```
1. Set your root token 
   ```
   $ export VAULT_TOKEN=<token copied from init command>
   ```
2. Enable audit logging

   ```
$ vault audit-enable file file_path=/vault/audit/audit.log
Successfully enabled audit backend 'file' with path 'file'!
```
1. Afterwards issue some commands like reading and writing from vault.
2. Check the file `audit/audit.log`. It should contain JSON information
 
   ```
   $ cat audit/audit.log 
{"time":"2017-04-03T21:22:59Z","type":"response","auth":{"client_token":"","accessor":"","display_name":"root","policies":["root"],"metadata":null},"request":{"id":"6cc59fff-a8ca-3e9f-acd8-19354643e5e1","operation":"update","client_token":"hmac-sha256:7a98ed68ffa5b542d5d30802a8620e78afabf0d3577bb68b472c01efda1c1397","client_token_accessor":"hmac-sha256:df2ae5e28615e7acf6fd72b6db66c2ae9540d5cbe68ed0a9f915cae1c6b8c1cf","path":"sys/audit/file","data":{"description":"hmac-sha256:566138e7bd418d0e84edadc473856d4a9f563105c574a255ef99afc53a4c38b1","local":false,"options":{"file_path":"hmac-sha256:c9b6c76153beaf0d8164f3dfa3c7d6ac6bead2c270476b72bd0fb392411d6e55"},"type":"hmac-sha256:35fe129d5bf56a8bbb9d1409dff63daecf29ce3bfd4f467ec2664d88c1ab73bc"},"remote_address":"172.17.0.1","wrap_ttl":0,"headers":{}},"response":{},"error":""}

   ```

**NOTE** Don't forget to clean up after you're done with `./stop.sh`

