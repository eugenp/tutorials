//require our websocket library 
var WebSocketServer = require('ws').Server;

//creating a websocket server at port 9090 
var wss = new WebSocketServer({ port: 9090 });

function getUniqueID() {
   return new Date().getTime().toString() + (Math.random() * 9999999 + Math.random() * 2);
}

//when a user connects to our sever 
wss.on('connection', handleConnection);

function handleConnection(connection) {
   console.log("user connected");
   connection.id = getUniqueID();
   
   connection.on('message', (message) => handleMessage(message, connection));

   connection.on("close", function () { });
}

function handleMessage(message, connection) {
   console.log("received: " + message);
   var data;
   
   try {
      data = JSON.parse(message);
   } catch (e) {
      console.log("Invalid JSON");
      data = {};
   }
   wss.clients.forEach(function each(client) {
      console.log('Client.ID: ' + client.id);
      if (connection.id != client.id) {
         client.send(data);
      }
   });
}