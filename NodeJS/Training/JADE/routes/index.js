/**
 * Created by pthiru on 2/9/2016.
 */

var express = require('express');
var router = express.Router();

router.get('/', function (req, res) {
    res.render('home', {title: 'Jade Custom Page!'});
});

module.exports = router;