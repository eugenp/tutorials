const  Example = artifacts.require("./Example.sol"),
  ConvertLib = artifacts.require("./ConvertLib.sol");

module.exports = deployer => {
  deployer.deploy(ConvertLib);
  deployer.link(ConvertLib, Example);
  deployer.deploy(Example);
};
