(() => {
	const div = document.getElementById('breadcrumbs-paths')
	const url = window.location.pathname
	const urls = url.split('/').filter(v => v)
	const paths = urls.map(v => {
		let newValue = v
		newValue = newValue.split('_')
		newValue = newValue.join(' ')
		newValue = newValue.replace(new RegExp('^'+newValue[0]+''), newValue[0].toUpperCase())
		return newValue
	})
	paths.forEach((p,i) => {
		let newNode = document.createElement('a')
		newNode.href = '/'+urls[i]
		newNode.className = "breadcrumb"
		newNode.innerHTML = p
		div.appendChild(newNode)
	})
})()

M.AutoInit()

engine()
