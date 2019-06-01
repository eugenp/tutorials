/*
 * Sample configuration file for tests
 */
 
// Enable UI
ui = true

// Filesystem storage
storage "file" {
  path    = "./vault-data"
}

// TCP Listener using a self-signed certificate
listener "tcp" {
  address     = "127.0.0.1:8200"
  tls_cert_file = "./src/test/vault-config/localhost.cert"
  tls_key_file = "./src/test/vault-config/localhost.key"
}


