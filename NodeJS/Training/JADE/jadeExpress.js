/**
 * Created by pthiru on 2/9/2016.
 */

var express = require('express');
var path = require('path');

var app = express();

var routes = require('./routes/index.js');

app.set('views',path.join(__dirname,'views'));
app.set('view engine','jade');

app.use('/',routes);

app.listen(9090, function() {
    console.log('Server started');
});