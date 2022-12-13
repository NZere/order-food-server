package project.app.space.team7cafeserver.Model

class OrderRequest {
    var user_id: String? = null
    var table_id: String? = null
    var total: String? = null
    var coupon: String? = null
    var status: String? = null
    var foods: List<Order>? = null
    var time:String?=null

    constructor() {}
    constructor(
        user_id: String?,
        table_id: String?,
        total: String?,
        coupon: String?,
        status:String?,
        foods: List<Order>?
    ) {
        this.user_id = user_id
        this.table_id = table_id
        this.total = total
        this.coupon = coupon
        this.status="1" //  1- in process 2- gave it to waiter 3- done
        this.foods = foods

    }
    constructor(
        user_id: String?,
        table_id: String?,
        total: String?,
        coupon: String?,
        status:String?,
        foods: List<Order>?,
        time:String?
    ) {
        this.user_id = user_id
        this.table_id = table_id
        this.total = total
        this.coupon = coupon
        this.status="1" // 1- in process 2- gave it to waiter 3- done
        this.foods = foods
        this.time=time

    }



}