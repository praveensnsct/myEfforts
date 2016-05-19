/**
 * Created by pthiru on 2/8/2016.
 */

const http = require('http');

const hostname = 'localhost';
const port = 8081;

http.createServer(function (req, res) {
    res.writeHead(200, {'Content-Type': 'text/plain'});
    res.end('Hello World\n');
}).listen(port, hostname, function () {
    console.log('Server running at http://',hostname+':'+port+'/');
});