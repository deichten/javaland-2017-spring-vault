## Initialize Vault securely with keybase.io

1. Start the environment by calling `./start.sh`
1. Set the Vault address `export VAULT_ADDR=http://localhost:8200`
1. Check if vault is answering by `vault status`. You should see:

   ```
   Error checking seal status: Error making API request.

   URL: GET http://localhost:8200/v1/sys/seal-status
   Code: 400. Errors:

   * server is not yet initialized
   ```
1. Initialize the vault server with keybase.io support by passing in the additional parameters, especially the `-pgp-keys` part.

   ```
   $ vault init -key-shares=1 -key-threshold=1 -pgp-keys=keybase:deichten
   Unseal Key 1: wcBMA2tH981VJURsAQgAhRINImppXbCMSc7bUyeHWlEGdhIFq12l+x30DgxYkrzzMaG9sNUFb0+9zA/wwjjoGucX8zP8vdHSJSbGFGPlV159yI8aivOdvjHH1L0XyZhE5bsO+K1E0PFchHTLH3U9dCxOUU1VPD7/l5qcBF31A/j8fLqL2WCSoj49ws3au2/rwvG9eNfZ7rLNEIo93HNTC053gkias5/lXL1mCpVgvMXGmDkPZjE6d9xWLgUziFasVl3lxcWuUeTrelnfU2u/eXMTR06UW/lTrztkVFs+v7VfSEZh0pWkqWOXy7WvrwKRAmkKdXJbiAqrEeed8Dq8buzlvRzVooUo/vxuCFCndNLgAeQMlj8V8ywu1PvbfWum2tEm4Wm04ILgtuGMruA94kh4vM/g7Obxe16XRtsZV5CSppLKear976C/58vy5gxFT/qe65F5B2aINlYGPOA0kGuPh4HnXv8+HBeB865jWtIQpfkxKwyb4PPkSMvM5apwCkXzf4wK81y7tuIBKMqQ4ei4AA==
   Initial Root Token: c001413c-6893-3c02-f077-7f64978ed012

   Vault initialized with 1 keys and a key threshold of 1. Please
securely distribute the above keys. When the Vault is re-sealed,
restarted, or stopped, you must provide at least 1 of these keys
to unseal it again.

   Vault does not store the master key. Without at least 1 keys,
your Vault will remain permanently sealed.
   ```
   
   **Note:** we had to reduce the `keys-threshold` here since *Vault* is smart enough to check how many different PGP keys/references you passed in. Since I picked just one here I also had to reduce the amount of keys needed for unsealing.
   
1. Since we now got the key in an encrypted form which easily and openly can be shared with all key holders – they'll need their private keys to get the correct unseal token.

   ```
   $ echo "wcBMA2tH981VJURsAQg…" | base64 -D | keybase pgp decrpyt
6c75310b71f7059c6af2a…
   ```

1. You can now use the decrypted key to unseal vault

   ```
   $ vault unseal 6c75310b71f7059c6af2a…
   Sealed: false
   Key Shares: 1
   Key Threshold: 1
   Unseal Progress: 0
   Unseal Nonce: 
   ```

1. Set your root token from above initalization process
   
   ```
   $ export VAULT_TOKEN=c001413c-6893-3c02-f077-7f64978ed012
   ```
   
1. Check vault by asking for status or write and read to secret backend

   ```
   $ vault status
   Sealed: false
   Key Shares: 1
   Key Threshold: 1
   Unseal Progress: 0
   Unseal Nonce: 
   Version: 0.7.0
   Cluster Name: vault-cluster-dfb03a29
   Cluster ID: 64d28d21-07f3-a1b2-dffc-3c77ff56be13

   High-Availability Enabled: false
   $ vault write secret/myapp foo=bar ttl=24h
   Success! Data written to: secret/myapp
   $ vault read secret/myapp 
   Key             	Value
   ---             	-----
   refresh_interval	24h0m0s
   foo             	bar
   ttl             	24h

   ```
1. Don't forget to clean up after you're done with `./stop.sh`
