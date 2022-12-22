package project.app.space.team7cafeserver.Model

class Coupon {
    var coupon_id: String? = null
    var user_id: String? = null
    var name:String?=null
    var is_actual:Boolean?=null

    constructor()
    constructor(coupon_id: String?, user_id: String?, name: String?, is_actual: Boolean?) {
        this.coupon_id = coupon_id
        this.user_id = user_id
        this.name = name
        this.is_actual = is_actual
    }


}