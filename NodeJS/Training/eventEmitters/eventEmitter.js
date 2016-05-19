/**
 * Created by pthiru on 2/8/2016.
 */

var eventEmitter = require('events').EventEmitter;

var getResource = function (cnt) {
    //logic that returns the object of event emitter
    var e = new eventEmitter();
    //some event logic
    // we can use process.nextTick instead of setTimeout
    process.nextTick(function () {
        var count = 0;
        e.emit('start');

        var timer = setInterval(function () {
            e.emit('data', ++count);

            if(count == 3){
                e.emit('error','Error occured');
                clearInterval(timer);
            }

            if (count === cnt) {
                e.emit('end', cnt);
                clearInterval(timer);
            }
        }, 1000);
    });

    return e;
};

var emt = getResource(5);

emt.on('data', function (currData) {
    console.log(currData);
});

emt.on('start', function () {
    console.log('I am an event start');
});

emt.on('end', function (endData) {
    console.log('I am an event end and the final count is : ', endData);
});

emt.on('error', function (err) {
    console.log('I am an event error', err);
});