var conn = new WebSocket('ws://localhost:9090');

conn.onopen = function () {
    console.log("Connection to Signalling server successful");
    initialize();
};

conn.onmessage = function (msg) {
    console.log("Message received: ", msg.data);
    var content = JSON.parse(msg.data);
    var data = content.data;
    switch (content.event) {
        case "offer":
            handleOffer(data);
            break;
        case "answer":
            handleAnswer(data);
            break;
        case "candidate":
            handleCandidate(data);
            break;
        default:
            break;
    }
};

function send(message) {
    conn.send(JSON.stringify(message));
}
var peerConnection;
var dataChannel;
function initialize() {
    
    var configuration = null;
    peerConnection = new RTCPeerConnection(configuration, { optional: [{ RtpDataChannels: true }] });

    // Setup ice handling 
    peerConnection.onicecandidate = function (event) {
        if (event.candidate) {
            send({ event: "candidate", data: event.candidate });
        }
    };

    //creating data channel 
    dataChannel = peerConnection.createDataChannel("dataChannel", { reliable: true });

    dataChannel.onerror = function (error) {
        console.log("Error:", error);
    };

    //when we receive a message from the other peer, print it to the console 
    dataChannel.onmessage = function (event) {
        console.log("Message:", event.data);
    };

    dataChannel.onclose = function () {
        console.log("Data channel is closed");
    };
}

function createOffer() {
    peerConnection.createOffer(function (offer) {
        send({
            event: "offer",
            data: offer
        });
        peerConnection.setLocalDescription(offer);
    }, function (error) {
        alert("Unable to create an offer:"+error);
    });
}

function handleOffer(offer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(offer));

    //create an answer to an offer 
    peerConnection.createAnswer(function (answer) {
        peerConnection.setLocalDescription(answer);
        send({
            event: "answer",
            data: answer
        });
    }, function (error) {
        alert("Unable to create an answer"+error);
    });

};

function handleCandidate(candidate) {
    peerConnection.addIceCandidate(new RTCIceCandidate(candidate));
};

function handleAnswer(answer) {
    peerConnection.setRemoteDescription(new RTCSessionDescription(answer));
};

function sendMessage() {
    dataChannel.send(document.getElementById("messageInput").value);
}
