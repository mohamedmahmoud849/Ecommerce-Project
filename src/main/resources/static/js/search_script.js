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

fetch("http://localhost:8090/products/fetch")
    .then(res => res.json())
    .then(data => {
        productsList = data.map(pro => {
            const product = productTemplate.content.cloneNode(true).children[0]

            const header = product.querySelector("[data-header]")
            const body =  product.querySelector("[data-body]")
            const image = product.querySelector("[data-image]")
            const button = product.querySelector("[data-button]");

            image.src = 'data:image/jpeg;base64,' + pro.image
            button.href = '/products/' + pro.id

            header.textContent = pro.name
            body.textContent = 'Price: $' + pro.price

            if (pro.quantity === 0) {
                    const buyButton = product.querySelector("[data-button]");
                    buyButton.classList.add("disabled");
                    button.removeAttribute("href");
                    const outOfStockMessage = product.querySelector("[data-stock-message]");
                    outOfStockMessage.style.display = "";
                  }
            productContainer.append(product)
            return { name: pro.name, price: pro.price, image: image, button: button, element: product}
        })
    })