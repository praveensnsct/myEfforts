/**
 * Created by pthiru on 1/24/2016.
 */

var express    = require("express");
var mysql      = require('mysql');
var bodyParser = require('body-parser');

var connection = mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    database : 'mydatabase'
});

var app = express();
app.use(express.static(__dirname));
//app.engine('html', require('ejs').renderFile);
app.use( bodyParser.json() );       // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));

connection.connect(function(err){
    if(!err) {
        console.log("Database is connected ... nn");
    } else {
        console.log("Error connecting database ... nn",err);
    }
});

/*app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});*/

app.get("/cart/getItems",function(req,res){
    connection.query('SELECT * from products LIMIT 6', function(err, rows, fields) {
        if (!err)
            //console.log('The solution is: ',);
        res.send(rows);
        else
            console.log('Error while performing Query.',err);
        //connection.end();
    });
});

app.post("/user/login",function(req,res){
    var queryString = 'SELECT * from users WHERE userName=' + connection.escape(req.body.userName);
    connection.query(queryString,function(err,rows,fields){
        if(!err && rows[0]){
            if(rows[0].password === req.body.password){
                res.send({userName:rows[0].userName,password:rows[0].password});
            }
        }else{
            res.send({err:'UserID Does not exist'});
        }
    })
});

app.listen(8080);