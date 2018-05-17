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

const avgNumberList = (list) => list && list.length ? list.reduce((t, v) => t + v, 0) / list.length : 0

document.querySelectorAll('input:not([name])', 'select:not([name])').forEach(input => {
  input.name = input.id
})

M.AutoInit()
