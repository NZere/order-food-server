package project.app.space.team7cafeserver.Model

open class Order {
    var ID: Int? = null
    var productId: String? = null
    var productName: String? = null
    var quantity: String? = null
    var price: String? = null
    var discount: String? = null
    var time:String?="0"

    constructor() {}
    constructor(
        productId: String?,
        productName: String?,
        quantity: String?,
        price: String?,
        discount: String?
    ) {
        this.productId = productId
        this.productName = productName
        this.quantity = quantity
        this.price = price
        this.discount = discount
        this.time="0"
    }
    constructor(
        productId: String?,
        productName: String?,
        quantity: String?,
        price: String?,
        discount: String?,
        time:String?
    ) {
        this.productId = productId
        this.productName = productName
        this.quantity = quantity
        this.price = price
        this.discount = discount
        this.time=time
    }
    constructor(
        ID:Int?,
        productId: String?,
        productName: String?,
        quantity: String?,
        price: String?,
        discount: String?
    ) {
        this.ID=ID
        this.productId = productId
        this.productName = productName
        this.quantity = quantity
        this.price = price
        this.discount = discount
        this.time="0"
    }
    constructor(
        ID:Int?,
        productId: String?,
        productName: String?,
        quantity: String?,
        price: String?,
        discount: String?,
        time:String?
    ) {
        this.ID=ID
        this.productId = productId
        this.productName = productName
        this.quantity = quantity
        this.price = price
        this.discount = discount
        this.time=time
    }
}