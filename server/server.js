const ws = require('nodejs-websocket');
const PORT = 9090;

const server = ws.createServer(function (conn) {
    console.log("New connection")
    conn.on("text", function (str) {
        console.log("Received " + str);
        // data是前端发的报文
        let data = JSON.parse(str);
        // 按操作分支，后续可用router修改
        console.log(typeof data);
        if (typeof data == "number"){
            broadcast(server, JSON.stringify({
                data: str,
            }));
        }else if (!('opt' in data)){
            broadcast(JSON.stringify({
                data: str,
            }));
        }else{
            switch (data.opt) {
                case OptType.draw :
                    broadcast(server,JSON.stringify({
                        version: data.version++,
                        isReadOnly: 1,
                    }))
                    break;
                case OptType.change :
                    broadcast(server,JSON.stringify({
                        version: data.version++,
                        isReadOnly: 1,
                    }))
                    break;
                case OptType.delete:
                    broadcast(server,JSON.stringify({
                        version: data.version++,
                        isReadOnly: 1,
                    }))
                    break;
                default:
                    broadcast(server,JSON.stringify({
                        data: str,
                    }))
            }
        }
    })
    conn.on("close", function (str) {
        let data = JSON.parse(str);
        console.log(str.id + "Connection closed" + "  data:"+data);
    })
}).listen(PORT, () => {
    console.log(`listen on ${PORT}`);
});

function broadcast(server, msg) {
    server.connections.forEach(function (conn) {
        conn.sendText(msg)
    })
}