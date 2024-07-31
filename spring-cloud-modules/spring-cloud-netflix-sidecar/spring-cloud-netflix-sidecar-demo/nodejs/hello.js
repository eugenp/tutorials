const express = require('express')
const app = express()
const port = 3000

app.get('/', (req, res) => {
    res.send('Hello World!')
})

app.get('/health', (req, res) => {
    res.send({ "status":"UP"})
})

app.get('/hello/:me', (req, res) => {
    res.send('Hello ' + req.params.me + '!')
})

app.listen(port, () => {
    console.log(`Hello app listening on port ${port}`)
})