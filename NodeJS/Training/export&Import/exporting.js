/**
 * Created by pthiru on 2/8/2016.
 */

var add = function(x,y){
    return x+y;
};

var count = 10;
var privateMemVar = true; //not available to the outside world as it is not exported.

module.exports.addition = add;
module.exports.Count = count;