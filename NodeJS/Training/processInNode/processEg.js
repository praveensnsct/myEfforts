/**
 * Created by pthiru on 2/9/2016.
 */

var fs = require('fs');

process.on('uncaughtException', function() {
   console.log('Swallows the error silently !');
});

fs.readFile('AnyFile.txt', function (err, data) {
    if (err)throw  err; // throw will internally emit
});