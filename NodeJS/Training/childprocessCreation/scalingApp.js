/**
 * Created by pthiru on 2/9/2016.
 */

var exec = require('child_process').exec;

exec('node -v', function(err,stdout,stdin) {
    console.log('stdout: '+stdout);
    console.log('Error: '+err);
});