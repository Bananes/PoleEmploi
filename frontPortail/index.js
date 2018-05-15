const express = require('express')
const exphbs = require('express-handlebars')
var bodyParser = require('body-parser')

const app = express()
app.use(bodyParser.json({ type: 'application/*+json' }))
app.use(bodyParser.text({ type: 'text/html' }))

app.engine('handlebars', exphbs({defaultLayout: 'main'}))
app.set('view engine', 'handlebars')

app.use(express.static('public'))

app.get('/', function (req, res) {
  res.render('home')
})

app.get('/form', function (req, res) {
  res.render('form')
})

app.listen(3000)
