module.exports = deployer => {
  deployer.deploy(artifacts.require("./Migrations.sol"));
};
