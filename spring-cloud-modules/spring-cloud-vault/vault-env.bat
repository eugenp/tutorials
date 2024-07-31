@echo off
echo Setting environment variables to access local vault..
set VAULT_ADDR=https://localhost:8200
set VAULT_CACERT=%~dp0%/src/test/vault-config/localhost.cert
rem set VAULT_TLS_SERVER_NAME=localhost
