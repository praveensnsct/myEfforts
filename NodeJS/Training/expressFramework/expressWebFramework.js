/**
 * Created by pthiru on 2/9/2016.
 */

var express = require('express');
var bodyParser = require('body-parser');

var app = express();

var router = express.Router();

router.route('/Books').get(function (req, res) {
    var responseJSON = {hello: 'This is JSON Data'};
    res.json(responseJSON);
});

app.use('/api', router);
app.use(bodyParser.urlencoded({extended: false})); //do not read from the URL (QueryString) but read from the payload

app.get('/', function (req, res) {
    //res.send('Welcome to Express Framework !');
    res.sendFile('home.html', {root: __dirname});
});

app.post('/login', function (req, res) {
    var uname = req.body.username;
    var pwd = req.body.password;
    console.log('Data from Client: ' + uname + " " + pwd);
    res.end("success");
});

var port = 5656;
app.listen(port, function () {
    console.log('Server running at port : ', port);
});