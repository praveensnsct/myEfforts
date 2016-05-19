/**
 * Created by pthiru on 2/9/2016.
 */

//To avoid callback hell

var bluebird = require('bluebird');
var fs_with_promise = bluebird.promisifyAll(require('fs'));

var promiseObj = fs_with_promise.readFileAsync('LogMsgs.txt');
promiseObj.then(function (fileData) {
    return fs_with_promise.mkdirAsync('NewDirectory');
}, function () {
    console.log('Something went wrong!');
});
