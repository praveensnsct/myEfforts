/**
 * Created by pthiru on 2/8/2016.
 */

var Resources = require('./Resource.js');

var emt = new Resources(5);

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