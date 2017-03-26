# Vault Server with dynamic MySQL credential backend

In this scenario we'll see how dynamic backends will work by showing *Vaults* integration to *MySQL*.

1. Start the scenario with `./start.sh`. This will
  3. Start the docker containers in the right order and linked to each other so vault can access *MySQL*
  4. Sleep some time to give mysql the time to spin up correctly
  5. Mount the MySQL dynamic backend under `mysql/`
  6. Configure the MySQL connection and lease times
  7. Create a readonly MySQL role that gets a global `SELECT` grant.
1. Set the Vault address and Token
   
   ```
   $ export VAULT_ADDR=http://127.0.0.1:8200
   $ export VAULT_TOKEN=00000000-0000-0000-0000-000000000000

   ```
   
1. Now you can retrieve credentials by starting and see that *Vault* is returning valid crendentials:

   ``` 
$ vault read mysql/creds/readonly
Key            	Value
---            	-----
lease_id       	mysql/creds/readonly/aa88354b-a2dd-c72c-83c5-d9afd71a8164
lease_duration 	5m0s
lease_renewable	true
password       	d6ca28a7-527a-5c5f-12fc-76b86148f309
username       	read-toke-b8d249

   ```

1. Be sure to try this out with a MySQL client like the Workbench or the local mysql client. If you retrieve multiple credentials you'll also notice that after some time *Vault* will clean up the created users after the expiry of the `lease_duration` automatically.
2. Cleanup by calling `./stop.sh`
