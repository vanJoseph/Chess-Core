package wildercoding.chess

data class MoveInfo(val success:Boolean){
    constructor( success: Boolean,reason: String):this(success) {
        this.reason =reason
    }
    var reason:String =""
}