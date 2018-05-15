const toogleHelper = (sender) => {
	const div = sender.parentNode.parentNode
	const classes = {
		'large': 'small',
		'small': 'large'
	}
	div.classList.forEach(c => {
		if(Object.keys(classes).includes(c)){
			div.classList.replace(c, classes[c])
		}
	})
}