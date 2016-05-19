/**
 * Created by pthiru on 2/8/2016.
 */

var fs = require('fs');

if(fs.existsSync('temp')){
    console.log('Directory exists');
    if(fs.existsSync('temp/newText.txt')){
        fs.unlinkSync('temp/newText.txt');
    }
    fs.rmdirSync('temp');
}

fs.mkdir('temp',function(err){
    fs.exists('temp', function(exists) {
        if(exists){
            process.chdir('temp');
            fs.writeFile('test.txt', 'This is some test data for the file', function() {
                fs.rename('test.txt','newText.txt', function() {
                    fs.stat('newText.txt', function(err,stats) {
                        console.log('File size is :' + stats.size + 'bytes');
                        fs.readFile('newText.txt',function(err,data){
                            console.log('File contents:'+data);
                        })
                    })
                });
            });
        }
    });
});