// server.js

const express = require('express');
const https = require('https');
const fs = require('fs');

const app = express();
app.get('/hello', (req, res) => {
    res.setHeader('Content-Type', 'application/json');
    res.status(200).end(JSON.stringify({ hello: 'Hello My Fen', }));
});

const options = {
    key: fs.readFileSync(__dirname + '/server1.key'), // replace it with your key path
    cert: fs.readFileSync(__dirname + '/server1.crt'), // replace it with your certificate path
}

console.log("Current directory:", __dirname);
https.createServer(options, app).listen(443);
