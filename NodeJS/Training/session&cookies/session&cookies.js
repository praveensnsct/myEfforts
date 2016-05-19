/**
 * Created by pthiru on 2/9/2016.
 */

var express = require('express');
var session = require('express-session');
var cookie = require('cookie-parser');

var app = express();

app.use(cookie());
app.use(session({
    secret: 'AnyStringData',
    saveUninitialized: true,
    resave: true
}));

app.use('/', function (req, res) {
    res.send('Our first cookie and Session Management');
    console.log(req.cookies);
    console.log('---------------');
    console.log(req.session);

    var sessionData = req.session;
    sessionData.someVal = "Actual Value!"; //session data
});

app.listen(8787, function () {
    console.log('Server Listening at 8787');
});