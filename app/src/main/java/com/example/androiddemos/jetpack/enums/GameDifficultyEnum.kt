package com.example.androiddemos.jetpack.enums

import com.example.androiddemos.R

enum class GameDifficultyEnum(
    val position:Int,
    val string: Int,
    val time:Int
) {
    GAME_EASY(0, R.string.game_easy,800),
    GAME_MEDIUM(1, R.string.game_medium,500),
    GAME_HARD(2, R.string.game_hard,350);

    companion object {
        fun findTimeByPosition(i: Int): Int {
            for (e in values()) {
                if (e.position == i) {
                    return e.time
                }
            }
            return GAME_EASY.time
        }
    }
}