const express = require('express');
const mongodb = require('mongodb');
const app = express();
const port = 8080;


const MongoClient = mongodb.MongoClient;

app.listen(port, () => {
    console.log(`Example app listening on port ${port}`);
})


app.get('/', function (req, res) {
    res.send('Hello World!')
})
  
  
app.post('/', function (req, res) {
    res.send('Got a POST request')
})

app.get('/user', function (req, res) {
    res.send('Got a GET request at /user')
})

app.put('/user', function (req, res) {
    res.send('Got a PUT request at /user')
})

app.delete('/user', function (req, res) {
    res.send('Got a DELETE request at /user')
})