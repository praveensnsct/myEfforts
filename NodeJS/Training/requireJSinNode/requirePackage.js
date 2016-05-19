/**
 * Created by pthiru on 2/9/2016.
 */

var requireJS = require('requirejs');

requireJS(['log', 'print'], function (log, print) {
    log();
    print();
});