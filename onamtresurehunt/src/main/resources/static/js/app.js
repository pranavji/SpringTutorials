
var pushSocket = new WebSocket("ws://127.0.0.1:6005/push")

pushSocket.onmessage = function (event) {
  alert(event.data);
  //do ui update here
}

pushSocket.onopen = function (event) {
    //send empty message to initialize socket connnection
    pushSocket.send("connected");
};

pushSocket.onclose = function (event) {
    //send empty message to initialize socket connnection
    alert("Socket Closed by Server");
};