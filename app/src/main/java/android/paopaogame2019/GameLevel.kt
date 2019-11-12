package android.paopaogame2019

import android.util.Log
import java.util.*

class GameLevel {

    private var switchMove = true
    private var changeMoveDirect = 4

    // Pull vertically columns from top to bottom
    fun two(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (lessIndex > 15) {

            for (i in (lessIndex - 16) downTo (lessIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (greaterIndex > 15) {

            for (i in (greaterIndex - 16) downTo (greaterIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // Pull vertically columns from bottom to top
    fun three(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex < 128) {

            for (i in (greaterIndex + 16)..(128 + greaterIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (lessIndex < 128) {

            for (i in (lessIndex + 16)..(128 + lessIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // Pull horizontally rows from right to left
    fun four( cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (lessIndex % 16 > 0) {

            for (i in (lessIndex - 1) downTo (lessIndex - lessIndex % 16)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (greaterIndex % 16 > 0) {

            for (i in (greaterIndex - 1) downTo (greaterIndex - greaterIndex % 16)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // Pull horizontally rows from left to right
    fun five(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex % 16 < 15) {

            for (i in (greaterIndex + 1)..(greaterIndex - greaterIndex % 16 + 15)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (lessIndex % 16 < 15) {

            for (i in (lessIndex + 1)..(lessIndex - lessIndex % 16 + 15)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // move first row to last row
    fun six(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        for (i in 0..112 step 16)

            for (j in i..i + 15)

                cardIdImgId[j] = cardIdImgId[j + 16].also { cardIdImgId[j + 16] = cardIdImgId[j]!! }!!

        return cardIdImgId
    }

    // Pull horizontally from center to hided cards position
    fun seven(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex % 16 < 7) {

            for (i in (greaterIndex + 1)..(greaterIndex - greaterIndex % 16 + 7)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (lessIndex % 16 < 7) {

            for (i in (lessIndex + 1)..(lessIndex - lessIndex % 16 + 7)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (lessIndex % 16 > 8) {

            for (i in (lessIndex - 1) downTo (lessIndex - lessIndex % 16 + 8)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (greaterIndex % 16 > 8) {

            for (i in (greaterIndex - 1) downTo (greaterIndex - greaterIndex % 16 + 8)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // Pull diagonally from left-top to hided cards position
    fun eight(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (lessIndex > 15 && lessIndex % 16 != 0) {

            if (lessIndex / 16 <= lessIndex % 16) {

                for (i in (lessIndex - 17) downTo (lessIndex % 16 - lessIndex / 16) step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }
                }
            }

            else {
                for (i in (lessIndex - 17) downTo (lessIndex / 16 - lessIndex % 16) * 16 step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }
                }
            }

        }

        if (greaterIndex > 15 && greaterIndex % 16 != 0) {

            if (greaterIndex / 16 <= greaterIndex % 16) {

                for (i in (greaterIndex - 17) downTo (greaterIndex % 16 - greaterIndex / 16) step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }
                }
            }

            else {
                for (i in (greaterIndex - 17) downTo (greaterIndex / 16 - greaterIndex % 16) * 16 step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }
                }
            }
        }

        return cardIdImgId
    }

    // Pull diagonally from right-bottom to hided cards position
    fun nine(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex < 128 && greaterIndex % 16 < 15) {

            if (greaterIndex % 16 - greaterIndex / 16 >= 7) {

                for (i in (greaterIndex + 17)..(greaterIndex + (15 - greaterIndex % 16) * 17) step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

            else {

                for (i in (greaterIndex + 17)..(greaterIndex + (8 - greaterIndex / 16) * 17) step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }
        }

        if (lessIndex < 128 && lessIndex % 16 < 15) {

            if (lessIndex % 16 - lessIndex / 16 >= 7) {

                for (i in (lessIndex + 17)..(lessIndex + (15 - lessIndex % 16) * 17) step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

            else {

                for (i in (lessIndex + 17)..(lessIndex + (8 - lessIndex / 16) * 17) step 17) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 17] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }
        }

        return cardIdImgId
    }

    // Move Odd row to right. Move Even row to left.
    fun ten(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex / 16 % 2 == 0) {

            if (greaterIndex % 16 < 15) {

                for (i in greaterIndex + 1..greaterIndex + (15 - greaterIndex % 16)) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i] = cardIdImgId[i - 1].also { cardIdImgId[i - 1] = cardIdImgId[i]!! }!!
                    }
                }
            }
        }

        if (lessIndex / 16 % 2 == 0) {

            if (lessIndex % 16 < 15) {

                for (i in lessIndex + 1..lessIndex + (15 - lessIndex % 16)) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i] = cardIdImgId[i - 1].also { cardIdImgId[i - 1] = cardIdImgId[i]!! }!!
                    }
                }
            }
        }

        if (lessIndex / 16 % 2 == 1) {

            if (lessIndex % 16 > 0) {

                for (i in lessIndex - 1 downTo lessIndex - lessIndex % 16) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i] = cardIdImgId[i + 1].also { cardIdImgId[i + 1] = cardIdImgId[i]!! }!!
                    }
                }
            }
        }

        if (greaterIndex / 16 % 2 == 1) {

            if (greaterIndex % 16 > 0) {

                for (i in greaterIndex - 1 downTo greaterIndex - greaterIndex % 16) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i] = cardIdImgId[i + 1].also { cardIdImgId[i + 1] = cardIdImgId[i]!! }!!
                    }
                }
            }
        }

        return cardIdImgId
    }

    // Moves last row to top
    fun eleven(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        for (i in 128 downTo 16 step 16) {

            for (j in i..i + 15) {

                cardIdImgId[j] = cardIdImgId[j - 16].also { cardIdImgId[j - 16] = cardIdImgId[j]!! }!!
            }
        }

        return cardIdImgId
    }

    // Pull right and left to center.
    fun twelve(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (lessIndex % 16 in 1..7) {

            for (i in (lessIndex - 1) downTo (lessIndex - lessIndex % 16)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        if (greaterIndex % 16 in 1..7) {

            for (i in (greaterIndex - 1) downTo (greaterIndex - greaterIndex % 16)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        if (greaterIndex % 16 in 8..14) {

            for (i in (greaterIndex + 1)..(greaterIndex - greaterIndex % 16 + 15)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        if (lessIndex % 16 in 8..14) {

            for (i in (lessIndex + 1)..(lessIndex - lessIndex % 16 + 15)) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 1] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        return cardIdImgId
    }

    // Pull columns 4 lines of bottom and top - to center.
    fun thirteen(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (lessIndex in 16..63) {

            for (i in (lessIndex - 16) downTo lessIndex % 16 step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        if (greaterIndex in 16..63) {

            for (i in (greaterIndex - 16) downTo greaterIndex % 16 step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        if (greaterIndex in 80..127) {

            for (i in (greaterIndex + 16)..(128 + greaterIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        if (lessIndex in 80..127) {

            for (i in (lessIndex + 16)..(128 + lessIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                   cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }

                else break
            }
        }

        return cardIdImgId
    }

    // Move diagonal from top-left to right-bottom and vice versa. Bottom-left and Top-right triangles move up and down.
    fun fourteen(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (switchMove) {

            for (i in 0..7) {

                for (j in i..i % 16 + 136 step 17) {

                    if (cardIdImgId[j] == 0 && j < i % 16 + 136) {

                        for (k in j + 17..i % 16 + 136 step 17) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16..118 step 17) {

                for (j in i..128 + i % 16) {

                    if (cardIdImgId[j] == 0 && j < 128 + i % 16) {

                        for (k in j + 16..128 + i % 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }

            for (i in 25..127 step 17) {

                for (j in i downTo i % 16 + 16 step 16) {

                    if (cardIdImgId[j] == 0 && j > i % 16) {

                        for (k in j - 16 downTo i % 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        } else {

            for (i in 136..143) {

                for (j in i downTo i % 16 - 136 / 17 step 17) {

                    if (cardIdImgId[j] == 0 && j > i % 16 - i / 17) {

                        for (k in j - 17 downTo i % 16 - i / 17 step 17) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }

            for (i in 128..134) {

                for (j in i downTo 16 + (i - 128) * 17 + 16 step 16) {

                    if (cardIdImgId[j] == 0) {

                        for (k in j - 16 downTo 16 + (i - 128) * 17 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }

            for (i in 9..15) {

                for (j in i..25 + (i - 9) * 17 - 16 step 16) {

                    if (cardIdImgId[j] == 0) {

                        for (k in j + 16..25 + (i - 9) * 17) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        }

        switchMove = !switchMove

        return cardIdImgId
    }

    // Groups all columns to right vice versa
    fun fifteen(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (switchMove) {

            for (i in 0..48 step 16) {

                for (j in i..i + 15) {

                    if (cardIdImgId[j] == 0 && j < i + 15) {

                        for (k in j + 1..i + 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }

            for (i in 95..143 step 16) {

                for (j in i downTo i - 15) {

                    if (cardIdImgId[j] == 0 && j > i - 15) {

                        for (k in j - 1 downTo i - 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        } else {

            for (i in 15..63 step 16) {

                for (j in i downTo i - 15) {

                    if (cardIdImgId[j] == 0 && j > i - 15) {

                        for (k in j - 1 downTo i - 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }

            for (i in 80..128 step 16) {

                for (j in i..i + 15) {

                    if (cardIdImgId[j] == 0 && j < i + 15) {

                        for (k in j + 1..i + 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        }

        switchMove = !switchMove

        return cardIdImgId
    }

    // move top 4 rows and bottom 4 row up and down.
    fun sixteen(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (switchMove) {

            for (i in 48..63) {

                for (j in i downTo i % 16 step 16) {

                    if (cardIdImgId[j] == 0 && j > j % 16) {

                        for (k in j - 16 downTo j % 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        } else {

            for (i in 0..15) {

                for (j in i..48 + i % 16 step 16) {

                    if (cardIdImgId[j] == 0 && j < 48 + j % 16) {

                        for (k in j + 16..48 + j % 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        }

        if (!switchMove) {

            for (i in 128..143) {

                for (j in i downTo 80 + i % 16 step 16) {

                    if (cardIdImgId[j] == 0 && j > 80 + j % 16) {

                        for (k in j - 16 downTo 80 + j % 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        } else {

            for (i in 80..95) {

                for (j in i..128 + i % 16 step 16) {

                    if (cardIdImgId[j] == 0 && j < 128 + j % 16) {

                        for (k in j + 16..128 + j % 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k]!!
                                cardIdImgId[k] = 0

                                break
                            }
                        }
                    }
                }
            }
        }

        switchMove = !switchMove

        return cardIdImgId
    }

    // second line moves round against clockwise. Fourth line moves clockwise
    fun seventeen(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        var tmpIndex = cardIdImgId[17]

        for (i in 17..29) {

            cardIdImgId[i] = cardIdImgId[i + 1]!!
        }

        for (i in 30..110 step 16) {

            cardIdImgId[i] = cardIdImgId[i + 16]!!
        }

        for (i in 126 downTo 114) {

            cardIdImgId[i] = cardIdImgId[i - 1]!!
        }

        for (i in 113 downTo 49 step 16) {

            cardIdImgId[i] = cardIdImgId[i - 16]!!
        }

        cardIdImgId[33] = tmpIndex!!


        tmpIndex = cardIdImgId[51]

        for (i in 52..60) {

            cardIdImgId[i] = tmpIndex!!.also { tmpIndex = cardIdImgId[i] }
        }

        for (i in 60..92 step 16) {

            cardIdImgId[i] = tmpIndex!!.also { tmpIndex = cardIdImgId[i] }
        }

        for (i in 91 downTo 83) {

            cardIdImgId[i] = tmpIndex!!.also { tmpIndex = cardIdImgId[i] }
        }

        for (i in 67 downTo 51 step 16) {

            cardIdImgId[i] = tmpIndex!!.also { tmpIndex = cardIdImgId[i] }
        }

        return cardIdImgId
    }

    // Pull vertically columns from center to top and bottom
    fun eighteen(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex < 64) {

            for (i in (greaterIndex + 16)..(64 + greaterIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (lessIndex < 64) {

            for (i in (lessIndex + 16)..(64 + lessIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (lessIndex >= 96) {

            for (i in (lessIndex - 16) downTo (80 + lessIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if (greaterIndex >= 96) {

            for (i in (greaterIndex - 16) downTo (80 + greaterIndex % 16) step 16) {

                if (cardIdImgId[i] != 0) {

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // move columns from center to out
    fun nineteen(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        for (i in 0..6) {

            for (j in i..i + 16 * 8 step 16) {

                cardIdImgId[j] = cardIdImgId[j + 1].also { cardIdImgId[j + 1] = cardIdImgId[j]!! }!!
            }
        }

        for (i in 15 downTo 9) {

            for (j in i..i + 16 * 8 step 16) {

                cardIdImgId[j] = cardIdImgId[j - 1].also { cardIdImgId[j - 1] = cardIdImgId[j]!! }!!
            }
        }

        return cardIdImgId
    }

    // moves extreme three rows in a circle
    fun twenty(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {


        var tmpIndex = cardIdImgId[0]

        for (i in 1..15) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 31..143 step 16) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 142 downTo 128) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 112 downTo 0 step 16) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!


        tmpIndex = cardIdImgId[17]

        for (i in 18..30) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 46..126 step 16) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 125 downTo 113) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 97 downTo 17 step 16) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!


        tmpIndex = cardIdImgId[34]

        for (i in 35..45) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 61..109 step 16) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 108 downTo 98) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        for (i in 82 downTo 34 step 16) cardIdImgId[i] = tmpIndex.also { tmpIndex = cardIdImgId[i] }!!

        return cardIdImgId
    }

    // Pull diagonally from left-bottom to hided cards position
    fun twentyOne(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (greaterIndex < 128 && greaterIndex % 16 > 0) {

            if (greaterIndex % 16 + greaterIndex / 16 <= 8) {

                for (i in (greaterIndex + 15)..(greaterIndex + greaterIndex % 16 * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

            else {

                for (i in (greaterIndex + 15)..(greaterIndex + (8 - greaterIndex / 16) * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }
        }

        if (lessIndex < 128 && lessIndex % 16 > 0) {

            if (lessIndex % 16 + lessIndex / 16 <= 8) {

                for (i in (lessIndex + 15)..(lessIndex + lessIndex % 16 * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

            else {

                for (i in (lessIndex + 15)..(lessIndex + (8 - lessIndex / 16) * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i - 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }
        }

        return cardIdImgId
    }

    // Group diagonally from right-top to the left-bottom
    fun twentyTwo(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if (lessIndex > 15 && lessIndex % 16 < 15) {

            if (lessIndex / 16 + lessIndex % 16 >= 15) {

                for (i in (lessIndex - 15) downTo (lessIndex - (15 - lessIndex % 16) * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

            else {

                for (i in (lessIndex - 15) downTo (lessIndex - lessIndex / 16 * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

        }

        if (greaterIndex > 15 && greaterIndex % 16 < 15) {

            if (greaterIndex / 16 + greaterIndex % 16 >= 15) {

                for (i in (greaterIndex - 15) downTo (greaterIndex - (15 - greaterIndex % 16) * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }

            else {

                for (i in (greaterIndex - 15) downTo (greaterIndex - greaterIndex / 16 * 15) step 15) {

                    if (cardIdImgId[i] != 0) {

                        cardIdImgId[i + 15] = cardIdImgId[i]!!
                        cardIdImgId[i] = 0
                    }

                    else break
                }
            }
        }

        return cardIdImgId
    }

    // Move Odd column to top. Move Even column to bottom.
    fun twentyThree(cardIdImgId: HashMap<Int, Int>, firstIndex: Int, secondIndex: Int ): HashMap<Int, Int> {

        var lessIndex = firstIndex
        var greaterIndex = secondIndex

        // swap operation
        if (lessIndex > greaterIndex) lessIndex = greaterIndex.also { greaterIndex = lessIndex }

        if ((lessIndex % 16) % 2 == 0) {

            if (lessIndex > 15) {

                for (i in lessIndex - 16 downTo lessIndex % 16 step 16) {

                    if (cardIdImgId[i] == 0) break

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if ((greaterIndex % 16) % 2 == 0) {

            if (greaterIndex > 15) {

                for (i in greaterIndex - 16 downTo greaterIndex % 16 step 16) {

                    if (cardIdImgId[i] == 0) break

                    cardIdImgId[i + 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if ((greaterIndex % 16) % 2 == 1) {

            if (greaterIndex < 128) {

                for (i in greaterIndex + 16..128 + greaterIndex % 16 step 16) {

                    if (cardIdImgId[i] == 0) break

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        if ((lessIndex % 16) % 2 == 1) {

            if (lessIndex < 128) {

                for (i in lessIndex + 16..128 + lessIndex % 16 step 16) {

                    if (cardIdImgId[i] == 0) break

                    cardIdImgId[i - 16] = cardIdImgId[i]!!
                    cardIdImgId[i] = 0
                }
            }
        }

        return cardIdImgId
    }

    // Snake move
    fun twentyFour(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        var tmpIndex = cardIdImgId[0]

        for (i in 0 .. 12 step 6){

            for (j in i + 16 .. i % 16 + 16 * 8 step 16){

                cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
            }

            for (j in 16 * 8 + i % 16 + 1 .. 16 * 8 + i % 16 + 3){

                cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
            }

            for (j in 16 * 8 + i % 16 + 3 - 16 downTo i % 16 + 3 step 16){

                cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
            }

            if (i < 12) {

                for (j in i % 16 + 4 .. i % 16 + 6){

                    cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
                }
            }
        }

        cardIdImgId[0] = tmpIndex!!

        return cardIdImgId
    }

    // Odd and Even Columns moves in the opposite direction
    fun twentyFive(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (switchMove){

            for (i in 0 .. 14 step 2){

                for (j in i .. i % 16 + 8 * 16 - 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 16 .. i % 16 + 8 * 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 129 .. 143 step 2){

                for (j in i downTo i % 16 + 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 16 downTo i % 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else {

            for (i in 128 .. 142 step 2){

                for (j in i downTo i % 16 + 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 16 downTo i % 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 1 .. 15 step 2){

                for (j in i .. i % 16 + 8 * 16 - 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 16 .. i % 16 + 8 * 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        switchMove = !switchMove

        return cardIdImgId
    }

    // Odd and Even Rows moves in the opposite direction
    fun twentySix(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (switchMove){

            for (i in 0 .. 128 step 32){

                for (j in i .. i + 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 1 .. i + 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 31 .. 127 step 32){

                for (j in i downTo i - 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 1 downTo i - 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else {

            for (i in 15 .. 143 step 32){

                for (j in i downTo i - 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 1 downTo i - 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16 .. 112 step 32){

                for (j in i .. i + 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 1 .. i + 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        switchMove = !switchMove

        return cardIdImgId
    }

    // Grouping: 1) Bottom -> Top, Left -> Right, Top -> Bottom, Right -> Left
    fun twentySeven(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (changeMoveDirect == 4){

            for (i in 15 .. 143 step 16){

                for (j in i downTo i - i % 16 + 1){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 1 downTo i - i % 16){

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            --changeMoveDirect
        }

        else if (changeMoveDirect == 3){

            for (i in 128 .. 143){

                for (j in i downTo i % 16 + 16 step 16){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 16 downTo i % 16 step 16){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            --changeMoveDirect
        }

        else if (changeMoveDirect == 2) {

            for (i in 0 .. 128 step 16){

                for (j in i .. i + 14){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 1 .. i + 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            --changeMoveDirect
        }

        else if (changeMoveDirect == 1){

            for (i in 0 .. 15){

                for (j in i .. 112 + i % 16 step 16){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 16 .. 128 + i % 16 step 16) {

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            changeMoveDirect = 4
        }

        return cardIdImgId
    }

    // Snake move. Starts from left-top corner, ends on the right-bottom corner
    fun twentyEight(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        var tmpIndex = cardIdImgId[0]

        for (i in 0 .. 128 step 32){

            for (j in i .. i + 15){

                if (j == 0) continue

                cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
            }

            if (i < 128){

                for (j in i + 15 + 16 downTo i + 16){

                    cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
                }
            }
        }

        cardIdImgId[0] = tmpIndex!!

        return cardIdImgId
    }

    // Grouping diagonally: RB to LT, LT to RB, LB to RT, RT to LB.
    fun twentyNine(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        if (changeMoveDirect == 4){

            for (i in 0 .. 7){

                for (j in i .. 8 * 17 + i % 16 - 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 17 .. 8 * 17 + i % 16 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 8 .. 14){

                for (j in i .. i + (15 - i) * 17 - 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 17 .. i + (15 - i % 16) * 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16 .. 112 step 16){

                for (j in i .. (8 - i / 16) * 17 + i - 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 17 .. (8 - i / 16) * 17 + i step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            --changeMoveDirect
        }

        else if (changeMoveDirect == 3){

            for (i in 136 .. 143){

                for (j in i downTo i % 17 + 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 17 downTo i % 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 31 .. 127 step 16){

                for (j in i downTo i % 17 + 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 17 downTo i % 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 129 .. 135){

                for (j in i downTo i - (i % 16) * 17 + 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 17 downTo i - (i % 16) * 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            --changeMoveDirect
        }

        else if (changeMoveDirect == 2){

            for (i in 8 .. 15){

                for (j in i .. 9 * 15 - (15 - i % 16) - 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 15 .. 9 * 15 - (15 - i % 16) step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 1 .. 7){

                for (j in i .. i * 16 - 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 15 .. i * 16 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 31 .. 127 step 16){

                for (j in i .. (8 - i / 16) * 15 + i - 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 15 .. (8 - i / 16) * 15 + i step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            --changeMoveDirect
        }

        else if (changeMoveDirect == 1){

            for (i in 128 .. 135){

                for (j in i downTo i % 15 + 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 15 downTo i % 15 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16 .. 112 step 16){

                for (j in i downTo i % 15 + 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 15 downTo i % 15 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 136 .. 142){

                for (j in i downTo i - (15 - i % 16) * 15 + 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 15 downTo i - (15 - i % 16) * 15 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            changeMoveDirect = 4
        }

        return cardIdImgId
    }

    // Mix of different moves. Last Level.
    fun thirty(cardIdImgId: HashMap<Int, Int>): HashMap<Int, Int> {

        val finalLevelMoveSwitcher = Random().nextInt(8)

        if (finalLevelMoveSwitcher == 0){

            for (i in 0 .. 14 step 2){

                for (j in i .. i % 16 + 8 * 16 - 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 16 .. i % 16 + 8 * 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 129 .. 143 step 2){

                for (j in i downTo i % 16 + 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 16 downTo i % 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 1){

            for (i in 128 .. 142 step 2){

                for (j in i downTo i % 16 + 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 16 downTo i % 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 1 .. 15 step 2){

                for (j in i .. i % 16 + 8 * 16 - 16 step 16){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 16 .. i % 16 + 8 * 16 step 16) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 2){

            for (i in 0 .. 128 step 32){

                for (j in i .. i + 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 1 .. i + 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 31 .. 127 step 32){

                for (j in i downTo i - 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 1 downTo i - 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 3) {

            for (i in 15 .. 143 step 32){

                for (j in i downTo i - 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j - 1 downTo i - 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16 .. 112 step 32){

                for (j in i .. i + 14){

                    if( cardIdImgId[j] == 0) {

                        for (k in j + 1 .. i + 15) {

                            if (cardIdImgId[k] != 0) {

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 4){

            var tmpIndex = cardIdImgId[0]

            for (i in 0 .. 12 step 6){

                for (j in i + 16 .. i % 16 + 16 * 8 step 16){

                    cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
                }

                for (j in 16 * 8 + i % 16 + 1 .. 16 * 8 + i % 16 + 3){

                    cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
                }

                for (j in 16 * 8 + i % 16 + 3 - 16 downTo i % 16 + 3 step 16){

                    cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
                }

                if (i < 12) {

                    for (j in i % 16 + 4 .. i % 16 + 6){

                        cardIdImgId[j] = tmpIndex.also { tmpIndex = cardIdImgId[j] }!!
                    }
                }
            }

            cardIdImgId[0] = tmpIndex!!
        }

        else if (finalLevelMoveSwitcher == 5){

            for (i in 0 .. 7){

                for (j in i .. 8 * 17 + i % 16 - 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 17 .. 8 * 17 + i % 16 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 8 .. 14){

                for (j in i .. i + (15 - i) * 17 - 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 17 .. i + (15 - i % 16) * 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16 .. 112 step 16){

                for (j in i .. (8 - i / 16) * 17 + i - 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 17 .. (8 - i / 16) * 17 + i step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 6){

            for (i in 136 .. 143){

                for (j in i downTo i % 17 + 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 17 downTo i % 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 31 .. 127 step 16){

                for (j in i downTo i % 17 + 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 17 downTo i % 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 129 .. 135){

                for (j in i downTo i - (i % 16) * 17 + 17 step 17){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 17 downTo i - (i % 16) * 17 step 17){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 7){

            for (i in 8 .. 15){

                for (j in i .. 9 * 15 - (15 - i % 16) - 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 15 .. 9 * 15 - (15 - i % 16) step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 1 .. 7){

                for (j in i .. i * 16 - 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 15 .. i * 16 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 31 .. 127 step 16){

                for (j in i .. (8 - i / 16) * 15 + i - 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j + 15 .. (8 - i / 16) * 15 + i step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        else if (finalLevelMoveSwitcher == 8){

            for (i in 128 .. 135){

                for (j in i downTo i % 15 + 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 15 downTo i % 15 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 16 .. 112 step 16){

                for (j in i downTo i % 15 + 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 15 downTo i % 15 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }

            for (i in 136 .. 142){

                for (j in i downTo i - (15 - i % 16) * 15 + 15 step 15){

                    if (cardIdImgId[j] == 0){

                        for (k in j - 15 downTo i - (15 - i % 16) * 15 step 15){

                            if (cardIdImgId[k] != 0){

                                cardIdImgId[j] = cardIdImgId[k].also { cardIdImgId[k] = cardIdImgId[j]!! }!!

                                break
                            }
                        }
                    }
                }
            }
        }

        return cardIdImgId
    }
}