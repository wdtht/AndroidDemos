package com.example.androiddemos.jetpack.enums

import com.example.androiddemos.R

enum class GameDifficultyEnum(
    val position:Int,
    val string: Int,
) {
    GAME_EASY(0, R.string.game_easy),
    GAME_MEDIUM(1, R.string.game_medium),
    GAME_HARD(2, R.string.game_hard);

    companion object {
        fun findByPosition(i: Int): GameDifficultyEnum {
            for (e in values()) {
                if (e.position == i) {
                    return e
                }
            }
            return GAME_EASY
        }
    }
}