package wildercoding.chess

enum class GameState {
    STALEMATE,
    THREEOLD_REPETITION,
    FIFTY_MOVE_RULE,
    IN_DEAD_POSTION,
    AGREED_DRAW,
    PLAYABLE,
    NEW,
    CHECK,
    CHECKMATE
}