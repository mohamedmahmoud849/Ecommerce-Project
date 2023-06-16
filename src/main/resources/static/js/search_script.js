const productTemplate = document.querySelector("[data-user-template]")
const productContainer = document.querySelector("[data-products-container]")
const searchInput = document.querySelector("[data-search]")

let productsList = []
//
searchInput.addEventListener("input", (e) => {
    const value = e.target.value.toLowerCase()
    console.log(e)
    productsList.forEach(pro => {
        const isVisible = pro.name.toLowerCase().includes(value)
        pro.element.classList.toggle("hide", !isVisible)

    })
})

fetch("http://localhost:8090/products")
    .then(res => res.json())
    .then(data => {
        productsList = data.map(pro => {
            const product = productTemplate.content.cloneNode(true).children[0]
            //console.log(pro)
            const header = product.querySelector("[data-header]")
            const body =  product.querySelector("[data-body]")
            const image = product.querySelector("[data-image]")
            const button = product.querySelector("[data-button]");

            image.src = 'data:image/jpeg;base64,' + pro.image
            button.href = '/items/' + pro.id

            header.textContent = pro.name
            body.textContent = 'Price: $' + pro.price
            //image.appendChild("img")
            //console.log(header)
            //console.log(body)
            //console.log(product)
            productContainer.append(product)
            return { name: pro.name, price: pro.price, image: image, button: button, element: product}
        })
    })