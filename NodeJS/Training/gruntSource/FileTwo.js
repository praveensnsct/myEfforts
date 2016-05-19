/**
 * Created by pthiru on 2/9/2016.
 */

var fs = require('fs');

var data = "";
var i = 0;

var rdbStream = fs.createReadStream('Data.txt');

rdbStream.on('data', function(chunk) {
    i++;
    data += chunk;
    console.log('%d bytes',chunk.length);
});

rdbStream.on('end',function(){
    console.log(data);
});