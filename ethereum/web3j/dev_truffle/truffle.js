module.exports = {
  contracts_build_directory: "./build/contracts",
  networks: {
    live: {
      network_id: 1, // Ethereum public network
      host: 'localhost',
      port: 8545
    },
    testnet: {
      network_id: 3, // Official Ethereum test network (Ropsten)
      host: 'localhost',
      port: 8545
    },
    development: {
      host: 'localhost',
      port: 8545,
      network_id: '*'
    }
  }
}
