pragma solidity ^0.4.0;

contract Greeting {
    address creator;
    string message;

    function Greeting(string _message) {
        message = _message;
        creator = msg.sender;
    }

    function greet() constant returns (string) {
        return message;
    }

    function setGreeting(string _message) {
        message = _message;
    }
}
