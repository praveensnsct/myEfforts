/**
 * Created by pthiru on 2/12/2016.
 */
var domain = require('domain');
var fs = require('fs');

var d1 = domain.create();

d1.on('error',function(){
    console.log('Inside error handler of domain !');
});
d1.run(function () {
    fs.readFile('AnyFile.txt', function (err, data) {
        if (err)throw  err; // throw will internally emit
    })
});

console.log('More Statements..');