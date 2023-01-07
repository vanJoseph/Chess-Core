package com.wildercoding.chess.data

data class MoveInfo(val success:Boolean, val moveType: MoveType, val reason: String){
    constructor( success: Boolean,reason: String):this(success, MoveType.MOVE,reason)
    constructor(success: Boolean, moveType: MoveType): this(success,moveType,"")
    constructor(success: Boolean):this(success, MoveType.MOVE,"")
}