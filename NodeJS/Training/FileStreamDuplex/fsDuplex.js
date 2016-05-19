/**
 * Created by pthiru on 2/8/2016.
 */

//In one cmd prompt run this file
//Open another cmd prompt and type telnet localhost 8788.
// start typing msgs and u can see it is saved in our file.

var net = require('net'); //tcp
var fs = require('fs');
var server = net.createServer(function(connect) {
    var log = fs.createWriteStream('LogMsgs.txt');
    console.log('Connection established !');
    connect.on('end',function(){
        console.log('Connection terminated');
    });
    connect.pipe(connect).pipe(log);
});

server.listen(8788, function() {
    console.log('Server listening to port 8788');
});