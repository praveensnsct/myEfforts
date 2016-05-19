/**
 * Created by pthiru on 2/9/2016.
 */

var http = require('http');

var socket = require('socket.io');
var fs = require('fs');

var handler = function (req, res) {
    fs.readFile(__dirname + '/Client.html', function (err, data) {
        if (err) {
            res.writeHead(500);
            return res.end('Error occured loading Client.html');
        } else {
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end(data);
        }
    })
};

var app = http.createServer(handler);

var io = socket.listen(app);
io.sockets.on('connection', function (skt) {
    setInterval(function () {
        var msgs = new Date();
        console.log('Emitted : ', msgs);
        skt.emit('message', msgs);
    }, 2000);

    skt.on('submit', function (data) {
        console.log('Data from Client: ', data);
    });
});


app.listen(8888, 'localhost');
console.log('Server running at 8888 port');