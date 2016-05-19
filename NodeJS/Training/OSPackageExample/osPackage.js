/**
 * Created by pthiru on 2/8/2016.
 */

var os = require('os');

var toMB = function(bytes){
    return (Math.round((bytes/1024/1024)*100)/100);
};

console.log('Host name : ',os.hostname());
console.log(toMB(os.freemem()) + " of " + toMB(os.totalmem()) + " MB free");