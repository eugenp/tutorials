# Using Truffle

Basic Truffle walkthrough readme.

See also: http://truffleframework.com/docs/

# Bash

Truffle install

```bash
	$ npm install truffle -g
	$ truffle version 
```

Truffle commands:

```bash
	$ truffle init
	$ truffle compile
	$ truffle migrate
	$ truffle test
```

# Directory Structure

The command `$ truffle init` will create a directory structure in the specified root according to the following:

```
root -|
      |-build               (Compiled Solc JSON Output Dir)  
      |-contracts           (Solc Contracts to be Compiled and Migrated)  
      |-migrations          (Specify the Order and Dependencies of Contracts to Be Deployed)  
      |-test                (Truffle Tests)  
```
      
# Compile

Truffle will compile the smart contracts and expose their **JSON ABI's for use with Web3.js** or **Binary ABI's for use with Web3J**.

# Migration

Truffle will let you deploy your Solc contracts directly to a blockchin of your choice.