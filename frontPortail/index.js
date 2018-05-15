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

app.get('/form', (req, res) => {
  res.render('form')
})

app.get('/search', (req, res) => {
  res.render('search', {results: []})
})

app.post('/search', (req, res) => {
  const results = [
	{
		title: "Premier résultat",
		description: "Ceci est le premier résultat",
		rating: 3
	},
	{
		title: "Deuxième résultat",
		description: "Ceci est le premier résultat",
		rating: 2
	},
	{
		title: " résultat",
		description: " premier résultat",
		rating: 4
	},
	{
		title: "Premier résultat",
		description: "Ceci  le premier résultat",
		rating: 2
	},
	{
		title: "Premier résultat",
		description: "Ceci  résultat",
		rating: 5
	},
  ]
  res.render('search', {results})
})

app.listen(3000)
