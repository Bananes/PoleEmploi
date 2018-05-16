const toogleHelper = () => {
  const div = document.getElementById('helper')
  const classes = {
    'large': 'small',
    'small': 'large'
  }
  div.classList.forEach(c => {
    if (Object.keys(classes).includes(c)) {
      div.classList.replace(c, classes[c])
    }
  })
}

const showHelp = () => {
  const div = document.getElementById('helper-container')
  div.classList.add('open')
}
