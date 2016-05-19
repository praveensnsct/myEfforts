/**
 * Created by pthiru on 2/8/2016.
 */

var req = require('request');
var fs = require('fs');

/*req('http://www.google.com',function(err,res,body){
    if(!err && res.statusCode === 200){
        //console.log(body);
        var writeStream = fs.createWriteStream('outputOfGoogle.html');
        writeStream.write(body);
    }
});*/

//same logic using event emitters

var res = req('http://www.google.com');
/*res.on('data',function(chunk){
    console.log('>>>> Data >>>>>>'+chunk);
});*/

//to write on to the file

var writer = fs.createWriteStream('TextResponse.html');
res.pipe(writer);