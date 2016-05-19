/**
 * Created by pthiru on 2/8/2016.
 */

var util = require('util');
var emtr = require('events').EventEmitter;

function Resource(cnt){
    var self = this;
    process.nextTick(function () {
        var count = 0;
        self.emit('start');

        var timer = setInterval(function () {
            self.emit('data', ++count);
            if (count === cnt) {
                self.emit('end', cnt);
                clearInterval(timer);
            }
        }, 1000);
    });
}

util.inherits(Resource,emtr);
module.exports = Resource;