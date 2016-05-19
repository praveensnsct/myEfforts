/**
 * Created by pthiru on 2/9/2016.
 */

// use node-debug "filename" to debug node scripts
var http = require('http');

var PORT = 9898;

var server = http.createServer(function (req, res) {
    res.writeHead(200, {'Content-type': 'text/plain'});
    res.end('This is a debugging example');
});

server.listen(PORT);
console.log('Running..');