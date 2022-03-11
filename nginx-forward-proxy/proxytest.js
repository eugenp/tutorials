var request = require('request');

request({
    'url':'http://www.google.com/',
    'method': "GET",
    'proxy':'http://192.168.100.40:8888'
},function (error, response, body) {
    if (!error && response.statusCode == 200) {
        console.log(body);
    }
})
