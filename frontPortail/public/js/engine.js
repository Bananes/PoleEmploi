(() => {

/*
===================
	CONFIG
===================
*/

const posteId = Cookies.get('poste')
if(!posteId){
	throw "No poste configuration saved"
}

/*
===================
	ADD DOM ELEMENTS
===================
*/
	
const html = `
<div id="helper" class="small">
	<div class="close">
		<button onclick="toogleHelper(this)">X</button>
	</div>
	<div class="small flex-center">
		<button onclick="toogleHelper(this)">?</button>
	</div>
	<div class="large flex-column">
		<div><h2>Besoin d'aide ?</h2></div>
		<div><h4><a href="#" onclick="showHelp()">Cliquez ici</a></h4></div>
	</div>
</div>

<div id="helper-container">
	<div id="helper-bg"></div>
	<div id="helper-content">
		HELP
	</div>
</div>
`
const div = document.createElement('div')
div.innerHTML = html
while(div.firstChild) {
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
	const speed = Math.sqrt(movementX**2 + movementY**2) * modelTime / (newMoveTime - lastMoveTime)
	if(newMoveTime - lastAverageTime > modelAvgTime && averageElements.length !== 0){
		lastAverageTime = newMoveTime
		const avg = Math.round(avgNumberList(averageElements))
		averageElements = []
		averageList = [...averageList.reverse().filter((v,i) => i < modelAvgList - 1).reverse(), avg]
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
	if(newClickTime - lastClickLoopTime > modelClickTime * 1000){
		clickElements = [...clickElements.reverse().filter((v,i) => i < modelClickList - 1).reverse(), 0]
		lastClickLoopTime = newClickTime
		console.debug('click',  clickElements)
	}
})

/*
	FORM ERRORS
*/

let errorsList = {}
document.querySelectorAll('form input:not([type=submit]), form select').forEach(i => i.addEventListener('invalid', (event) => {
	const form = event.target.parentElement.tagName === 'FORM' && event.target.parentElement
	const idForm = form.id + "_" + (form.method || 'GET') + '_' + form.action
	const idElement = event.target.id|| event.target.name
	if(idElement){
		if(errorsList[idForm] === undefined){
			errorsList[idForm] = {}
		}
		if(errorsList[idForm][idElement] === undefined){
			errorsList[idForm][idElement] = 0
		}
		
		errorsList[idForm][idElement] = errorsList[idForm][idElement] +1
		console.debug('errors', errorsList)
	}
	else{
		throw "No id or name on a field"
	}
}))

/*

	EXIT PAGE

*/

const pageLoadedTime = getTime()
document.querySelectorAll('a:not([href^="#"]), input[type="submit"], button[type="submit"]').forEach(e => e.addEventListener('click', (event) => {
	const executionTime = getTime() - pageLoadedTime
	console.debug('page_exit', executionTime)
}))

/*
===================
	LOGC & SENDING DATA
===================
*/

const sendData = (infos) => {
	if(infos && infos.length !== 0){
		const idUser = posteId
		const page = window.location.href
		
		const data = {
			id: idUser,
			page: page,
			infos: infos
		}
		
		console.debug('sended', data.infos)
		axios.post(BACK_ENDPOINT, data).catch((ex) => console.error(ex))
	}
}

const checkingInterval = 10 // s

const modelAvgResetTime = 10 // s
const modelClickResetTime = 10 //s

const modelMouseSend = 500 //px
const modelClickSend = 10 // clics

setInterval(() => {
	const actualTime = getTime()
	if(actualTime - lastAverageTime > modelAvgResetTime * 1000){
		averageElements = []
		averageList = []
	}
	if(actualTime - lastClickLoopTime > modelClickResetTime * 1000){
		clickElements = []
	}

	
	mouseMovementAverage = avgNumberList(averageList)
	clickAverage = avgNumberList(clickElements)
	
	console.debug("================================")
	console.debug(mouseMovementAverage+"px / "+modelTime+"ms")
	console.debug(clickAverage+" click(s) / "+modelClickTime+"s")
	console.debug(((getTime() - pageLoadedTime)/1000)+"s")
	console.debug("================================")
	
	let infos = []
	if(mouseMovementAverage > modelMouseSend){
		infos = [...infos, {
			id: "MOUSE_AVG",
			value: mouseMovementAverage
		}]
	}
	if(clickAverage > modelClickSend){
		infos = [...infos, {
			id: "CLICK_AVG",
			value: clickAverage
		}]
	}
	sendData(infos)
}, checkingInterval * 1000)

console.log("engine loaded")
})()
