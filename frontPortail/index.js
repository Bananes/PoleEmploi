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
      title: 'Premier résultat',
      description: 'Ceci est le premier résultat',
      rating: 3
    },
    {
      title: 'Deuxième résultat',
      description: 'Ceci est le premier résultat',
      rating: 2
    },
    {
      title: ' résultat',
      description: ' premier résultat',
      rating: 4
    },
    {
      title: 'Premier résultat',
      description: 'Ceci  le premier résultat',
      rating: 2
    },
    {
      title: 'Premier résultat',
      description: 'Ceci  résultat',
      rating: 5
    },
    {
      title: 'Premier résultat',
      description: "Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l'imprimerie depuis les années 1500, quand un peintre anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n'a pas fait que survivre cinq siècles, mais s'est aussi adapté à la bureautique informatique, sans que son contenu n'en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.",
      rating: 6
    }
  ]
  res.render('search', {results})
})

app.listen(3000)
