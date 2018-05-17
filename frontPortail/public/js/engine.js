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

const toogleHelp = () => {
  const div = document.getElementById('helper-container')
  if(div.className.indexOf('open') !== -1){
	  div.classList.remove('open')
  }
  else{
	div.classList.add('open')
	}
}

const avgNumberList = (list) => list && list.length ? list.reduce((t, v) => t + v, 0) / list.length : 0

const engine = () => {
/*
===================
	CONFIG
===================
*/
  const posteId = Cookies.get('poste')
  if (!posteId) {
    throw 'No poste configuration saved'
  }

	document.querySelectorAll('input:not([name])', 'select:not([name])').forEach(input => {
	  input.name = input.id
	})

  /*
===================
	ADD DOM ELEMENTS
===================
*/

  const html = `
<div id="helper" class="small">
	<div class="close">
		<button class="btn" onclick="toogleHelper(this)">X</button>
	</div>
	<div class="small flex-center">
		<button class="btn" onclick="toogleHelper(this)">?</button>
	</div>
	<div class="large flex-column">
		<div><h2>Besoin d'aide ?</h2></div>
		<div><h4><a href="#" onclick="toogleHelp()">Cliquez ici</a></h4></div>
	</div>
</div>

<div id="helper-container">
	<div id="helper-bg"></div>
	<div id="helper-content" class="container">
		<div class="container">
			<div class="row">
				<div class="col s12 flex-column">
					<div>
						<h3>Aide</h3>
					</div>
					<div>
						<p>
							La page sur laquelle vous vous sitiez est un formulaire où vous pouvez remplir les champs avec les informations qui sont nécessaires.
						</p>
						<p>
							Par exemple, un champ avec "Prénom", vous demandera de remplir le champ avec votre prénom.<br/>
							Un champ avec une date de naissance vous demandera de sélectionner votre date de naissance, etc.
						</p>
						<p>
							Une étoile "*" indique qu'un champ est obligatoire, vous serez donc obligé de remplir ce champ pour valider  le formulaire.
						</p>
						<p>
							Si certaines informations viennent à vous manquer, n'hésitez pas à aller chercher cette information et à revenir remplir le formulaire ultérieurement.
						</p>
						<p>
							Les boutons ci-dessous indiquent que vous avez compris et/ou que vous souhaitez obtenir une assistance plus approfondie concernant l'utilisation de cette page.
						</p>
					</div>
					<div>
						<button class="btn" onclick="toogleHelp(); toogleHelper()">J'ai compris</button>
					</div>
					<div>
						<button class="btn" id="needAssistance">J'ai besoin d'une assistance supplémentaire</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
`
  const div = document.createElement('div')
  div.innerHTML = html
  while (div.firstChild) {
    document.getElementById('main').appendChild(div.firstChild)
  }

  /*
===================
	STATISTICS
===================
*/

  /*
	MOUSE SPEED
*/

  const modelTime = 250 // ms
  const modelAvgTime = 2000 // ms
  const modelAvgList = 10 // elements => list of (modelAvgList * modelAvgTime) ms of speed records
  const actualTime = () => new Date().getTime()

  let lastMoveTime = actualTime()
  let lastAverageTime = actualTime()
  let averageElements = []
  let averageList = []

  document.addEventListener('mousemove', (event) => {
    const {movementX, movementY} = event
    const newMoveTime = actualTime()
    
	const speed = Math.sqrt(movementX ** 2 + movementY ** 2) * modelTime / (newMoveTime - lastMoveTime)

	if (newMoveTime - lastAverageTime > modelAvgTime && averageElements.length !== 0) {
      lastAverageTime = newMoveTime
      const avg = Math.round(avgNumberList(averageElements))
      averageElements = []
      averageList = [...averageList.reverse().filter((v, i) => i < modelAvgList - 1).reverse(), avg]
      console.debug('speed', averageList)
    }
    averageElements = [...averageElements, speed]
    lastMoveTime = newMoveTime
  })

  /*
	CLICK
*/

  const modelClickTime = 2 // s
  const modelClickList = 10 // elements
  let lastClickLoopTime = actualTime()
  let clickElements = [0]

  document.addEventListener('click', (event) => {
    clickElements[clickElements.length - 1] = clickElements[clickElements.length - 1] + 1
    const newClickTime = actualTime()
    if (newClickTime - lastClickLoopTime > modelClickTime * 1000) {
      clickElements = [...clickElements.reverse().filter((v, i) => i < modelClickList - 1).reverse(), 0]
      lastClickLoopTime = newClickTime
      console.debug('click', clickElements)
    }
  })

  /*
	FORM ERRORS
*/

  let errorsList = {}
  document.querySelectorAll('form input:not([type=submit]), form select').forEach(i => i.addEventListener('invalid', (event) => {
    const form = event.target.parentElement.tagName === 'FORM' && event.target.parentElement
    const idForm = form.id + '_' + (form.method || 'GET') + '_' + form.action
    const idElement = event.target.id || event.target.name
    if (idElement) {
      if (errorsList[idForm] === undefined) {
        errorsList[idForm] = {}
      }
      if (errorsList[idForm][idElement] === undefined) {
        errorsList[idForm][idElement] = 0
      }

      errorsList[idForm][idElement] = errorsList[idForm][idElement] + 1
      console.debug('errors', errorsList)
    } else {
      throw 'No id or name on a field'
    }
  }))

/*
	EXIT PAGE
*/

  const pageLoadedTime = getTime()
  document.querySelectorAll('a:not([href^="#"]), input[type="submit"], button[type="submit"]').forEach(e => e.addEventListener('click', (event) => sendData('EXITING')))

  /*
	ASSISTANCE REQUIRED
*/
document.getElementById('needAssistance').addEventListener('click', (event) => {
	sendData('ASSISTANCE')
	toogleHelp()
	toogleHelper()
	alert("Assistance required")
})

/*
===================
	LOGC & SENDING DATA
===================
*/

  const checkingInterval = 10 // s

  const modelAvgResetTime = 10 // s
  const modelClickResetTime = 10 // s

  const modelMouseSend = 400 // px
  const modelClickSend = 7 // clics

	const sendData = (reason) => {
	const mouseMovementAverage = avgNumberList(averageList)
    const clickAverage = avgNumberList(clickElements)
	const executionTime = getTime() - pageLoadedTime

    console.debug('================================')
    console.debug(mouseMovementAverage + 'px / ' + modelTime + 'ms')
    console.debug(clickAverage + ' click(s) / ' + modelClickTime + 's')
    console.debug(((getTime() - pageLoadedTime) / 1000) + 's')
    console.debug('================================')

    let infos = []
	if(reason === 'EXITING'){
		infos = [...infos, {
			name: 'Time-Elapsed',
			value: executionTime
		}]
	}
	else if(reason === 'ASSISTANCE'){
		infos = [...infos, {
			name: 'Help',
			value: 666
		}]
	}
	else{
		if (mouseMovementAverage > modelMouseSend) {
		  infos = [...infos, {
			name: 'Mouse-Speed',
			value: mouseMovementAverage
		  }]
		}
		if (clickAverage > modelClickSend) {
		  infos = [...infos, {
			name: 'Amount-Click',
			value: clickAverage
		  }]
		}
	}
	
    if (infos && infos.length !== 0) {
      const idUser = posteId
      const page = window.location.pathname

      const data = {
        id: idUser,
        page: page,
        infos: infos
      }

      console.debug('sended', data.infos)
      axios.post(BACK_ENDPOINT, data).catch((ex) => console.debug(ex))
    }
  }

  setInterval(() => {
    const actualTime = getTime()
    if (actualTime - lastAverageTime > modelAvgResetTime * 1000) {
      averageElements = []
      averageList = []
    }
    if (actualTime - lastClickLoopTime > modelClickResetTime * 1000) {
      clickElements = []
    }
	
	sendData()

  }, checkingInterval * 1000)

  console.log('engine loaded')
}
