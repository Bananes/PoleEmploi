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
		const avg = Math.round(averageElements.reduce((t,c) => t + c) / averageElements.length)
		averageElements = []
		averageList = [...averageList.reverse().filter((v,i) => i < modelAvgList - 1).reverse(), avg]
		console.debug('speed', averageList)
	}
	averageElements = [...averageElements, speed]
	lastMoveTime = newMoveTime
})

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
	}
	console.debug('click',  clickElements)
})

let errorsList = {}
document.querySelectorAll('form input:not([type=submit])', 'form select').forEach(i => i.addEventListener('invalid', (event) => {
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
