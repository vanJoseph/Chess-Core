class Board {

    var squareInit=  { _:Int ->Square(Coord(0, 0), null)}

    var board=Array(8){Array<Square>(8,squareInit) }

}