package android.paopaogame2019

import android.util.Log
import java.util.*

class Game {

    //region Class Variables

    private val checker: Checker

    private val gameLevelMove: GameLevel

    // Current level hided cards counter
    var hidedCardsCount = 0

    // possible next first btn index
    var possibleNextFirstBtnIndex: Int = -1

    // possible next second btn index
    var possibleNextSecondBtnIndex: Int = -1

    // map where keys are cards indexes and values are image indexes
    var cardIdImgId: HashMap<Int, Int>

    // is search click uses to prevent user multiple clicks to search icon
    var isSearchClicked: Boolean = false

    // current level counter
    var currentLevel = 1

    // endregion

    init {
        cardIdImgId = HashMap()

        checker = Checker()

        gameLevelMove = GameLevel()
    }

    // Check 2 clicked btnCards on field to identity
    fun checkIdentity(first: Int, second: Int): Boolean {

        // Get 2 button_style's indexes in Gridlayout to compare
        var prevBtnIndex = first
        var nextBtnIndex = second

        // check if there images of two buttons are the same
        if(cardIdImgId[prevBtnIndex] == cardIdImgId[nextBtnIndex]){

            // swap if left btn
            if (prevBtnIndex < nextBtnIndex) {
                prevBtnIndex = nextBtnIndex.also { nextBtnIndex = prevBtnIndex }
            }

            /*
            if two btn cards located next to each other horizontally or vertically,
            or on the first row, or on the last row, or on the first column, or on the last column
            */
            if ((prevBtnIndex - nextBtnIndex == 1 && prevBtnIndex / 16  - nextBtnIndex / 16 == 0) || prevBtnIndex - nextBtnIndex == 16 || (prevBtnIndex % 16 == 0 && nextBtnIndex % 16 == 0) ||
                (prevBtnIndex % 16 == 15 && nextBtnIndex % 16 == 15) || (prevBtnIndex <= 15 && nextBtnIndex <= 15) || (prevBtnIndex >= 128 && nextBtnIndex >= 128)){

                return true
            }

            // if two btn cards located in one column other than both - first and last columns
            else if(prevBtnIndex % 16 in 1..14 &&  (prevBtnIndex % 16 == nextBtnIndex % 16 )){

                var isResult: Boolean

                isResult = checker.verticalStraight(prevBtnIndex, nextBtnIndex, cardIdImgId)

                if(!isResult) isResult = checker.piFromLeftToRight(prevBtnIndex, nextBtnIndex, cardIdImgId)

                if (!isResult) isResult = checker.piFromRightToLeft(prevBtnIndex, nextBtnIndex, cardIdImgId)

                return isResult
            }

            // if two btn cards located in one row other than both first and last rows
            else if(prevBtnIndex in 16..127 &&  (prevBtnIndex / 16 == nextBtnIndex / 16 )) {

                var isResult: Boolean

                isResult = checker.horizontalStraight(prevBtnIndex, nextBtnIndex, cardIdImgId)

                if(!isResult) isResult = checker.piFromDownToUp(prevBtnIndex, nextBtnIndex, cardIdImgId)

                if (!isResult) isResult = checker.piFromUpToDown(prevBtnIndex, nextBtnIndex, cardIdImgId)

                return isResult
            }

            // if secondBtn position is up-left, and firstBtn position is down-right
            else if (prevBtnIndex % 16 > nextBtnIndex % 16 && prevBtnIndex > nextBtnIndex) {

                var isResult: Boolean

                Log.i("mycheck", "Ok we are in Z!!!")
                Log.i("mycheck", "prevBtnIndex: $prevBtnIndex, nextBtnIndex: $nextBtnIndex")

                // check horizontal z
                isResult = checker.horizontalZ(prevBtnIndex, nextBtnIndex, cardIdImgId)
                Log.i("mycheck", "horizontal Z, isresult = $isResult")

                // if isResult equal to false then check vertical check
                if (!isResult) isResult = checker.verticalZ(prevBtnIndex, nextBtnIndex, cardIdImgId)
                Log.i("mycheck", "vertical Z, isresult = $isResult")

                // If isResult equal to false then Check Are there all spaces empty from Top_Btn row to Bottom_Btn
                if (!isResult && checker.isColEmptyFromFirstBtnRowToSecondBtnRow(prevBtnIndex, nextBtnIndex, cardIdImgId)) {

                    // if Smaller_Btn on the First Row
                    if (nextBtnIndex < 16) {
                        isResult = true
                    }

                    // if Smaller_Btn on the any row other than first row
                    else {
                        isResult = checker.piFromDownToUp(nextBtnIndex + prevBtnIndex % 16 - nextBtnIndex % 16, nextBtnIndex, cardIdImgId)
                    }
                }
                Log.i("mycheck", "isColEmptyFromFirstBtnRowToSecondBtnRow, isresult = $isResult")

                // If isResult false then Check is Column Empty from Top Btn Row To Bottom Button Row
                if (!isResult && checker.isColEmptyFromSecondBtnRowToFirstRow(prevBtnIndex,
                        nextBtnIndex, cardIdImgId)
                ) {

                    // If Bottom Btn is on the  Last Row
                    if (prevBtnIndex > 127) {
                        isResult = true
                    }

                    // if Bottom btn on the any row other than Last Row
                    else {
                        isResult = checker.piFromUpToDown(prevBtnIndex, prevBtnIndex - prevBtnIndex % 16 + nextBtnIndex % 16, cardIdImgId)
                    }
                }
                Log.i("mycheck", "isColEmptyFromSecondBtnRowToFirstRow, isresult = $isResult")

                // If isResult false then Check is Row empty from Bottom Btn Col to Top Btn Col
                if (!isResult && checker.isRowEmptyFromFirstBtnColToSecondBtnCol(prevBtnIndex,
                        nextBtnIndex, cardIdImgId)
                ) {

                    // if Top Btn located at First Column
                    if (nextBtnIndex % 16 == 0) isResult = true

                    // if Top Btn located at any other column except first
                    else {
                        isResult = checker.piFromRightToLeft(prevBtnIndex - prevBtnIndex % 16 + nextBtnIndex % 16, nextBtnIndex, cardIdImgId)
                    }
                }
                Log.i("mycheck", "isRowEmptyFromFirstBtnColToSecondBtnCol, isresult = $isResult")

                // If isResult false, then Check is Row empty from Top Btn Col to Bottom Btn Col
                if (!isResult && checker.isRowEmptyFromSecondBtnColToFirstBtnCol(prevBtnIndex, nextBtnIndex, cardIdImgId)) {

                    // if Bottom Btn located at Last Column
                    if (prevBtnIndex % 16 == 15) isResult = true

                    // if Bottom Btn located at any other column except last
                    else {
                        isResult = checker.piFromLeftToRight(prevBtnIndex, nextBtnIndex - nextBtnIndex % 16 + prevBtnIndex % 16, cardIdImgId)
                    }
                }
                Log.i("mycheck", "isRowEmptyFromSecondBtnColToFirstBtnCol, isresult = $isResult")

                return isResult
            }

            // if secondBtn position is up-right, and firstBtn position is down-left
            else if(prevBtnIndex % 16 < nextBtnIndex % 16 && prevBtnIndex > nextBtnIndex) {

                var isResult: Boolean

                Log.i("mycheck", "Ok we are in S-manner!!!")
                Log.i("mycheck", "prevBtnIndex: $prevBtnIndex, nextBtnIndex: $nextBtnIndex")

                isResult = checker.horizontalS(prevBtnIndex, nextBtnIndex, cardIdImgId)
                Log.i("mycheck", "horizontal S, isresult = $isResult")

                if (!isResult) isResult = checker.verticalS(prevBtnIndex, nextBtnIndex, cardIdImgId)
                Log.i("mycheck", "vertical S, isresult = $isResult")

                if (!isResult && checker.isColEmptyFromFirstBtnRowToSecondBtnRow(prevBtnIndex, nextBtnIndex, cardIdImgId)) {

                    Log.i("mycheck", "isColEmptyFromFBRtoSecBtnRow = ${checker.isColEmptyFromFirstBtnRowToSecondBtnRow(prevBtnIndex, nextBtnIndex, cardIdImgId)}")
                    // if Smaller_Btn on the First Row
                    if (nextBtnIndex < 16)isResult = true

                    // if Smaller_Btn on the any row other than first row
                    else isResult = checker.piFromDownToUp(nextBtnIndex, nextBtnIndex + prevBtnIndex % 16 - nextBtnIndex % 16, cardIdImgId)
                }

                if (!isResult && checker.isColEmptyFromSecondBtnRowToFirstRow(prevBtnIndex, nextBtnIndex, cardIdImgId)) {

                    // If Bottom Btn is on the  Last Row
                    if (prevBtnIndex > 127) isResult = true

                    // if Bottom btn on the any row other than Last Row
                    else isResult = checker.piFromUpToDown(prevBtnIndex - prevBtnIndex % 16 + nextBtnIndex % 16, prevBtnIndex, cardIdImgId)
                }

                Log.i("mycheck", "Checker result: ${checker.isRowEmptyFromSecondBtnColToFirstBtnCol(prevBtnIndex, nextBtnIndex, cardIdImgId)}")

                if (!isResult && checker.isRowEmptyFromFirstBtnColToSecondBtnCol(prevBtnIndex, nextBtnIndex, cardIdImgId)) {

                    // if Top Btn located at First Column
                    if (nextBtnIndex % 16 == 15) isResult = true

                    // if Top Btn located at any other column except Last
                    else {

                        isResult = checker.piFromLeftToRight(prevBtnIndex - prevBtnIndex % 16 + nextBtnIndex % 16, nextBtnIndex, cardIdImgId)
                    }
                }

                // If isResult false, then Check "Is Row empty from Top Btn Col to Bottom Btn Col?"
                if (!isResult && checker.isRowEmptyFromSecondBtnColToFirstBtnCol(prevBtnIndex, nextBtnIndex, cardIdImgId)) {

                    // if Bottom Btn located at the First Column
                    if (prevBtnIndex % 16 == 0) isResult = true

                    // if Bottom Btn located at any other column except last
                    else {
                        isResult = checker.piFromRightToLeft(prevBtnIndex, nextBtnIndex - nextBtnIndex % 16 + prevBtnIndex % 16, cardIdImgId)
                    }
                }

                return isResult
            }

            else return false
        }

        else return false
    }

    // Check: Are there any pair of cards?
    fun isTherePair(): Boolean{

        val keys = cardIdImgId.keys.filter { cardIdImgId[it]?.compareTo(0) == 1 } as ArrayList<Int>

        for (i in 0..(keys.size - 2)){

            for (j in (i + 1)..(keys.size - 1)){

                if (cardIdImgId[keys[i]] == cardIdImgId[keys[j]]){

                    Log.i("isTherePair", "Before calling checkIdentity")
                    if (checkIdentity(keys[i], keys[j])){

                        possibleNextFirstBtnIndex = keys[i]
                        possibleNextSecondBtnIndex = keys[j]

                        Log.i("isTherePair", "possibleFirstBtn: $possibleNextFirstBtnIndex, possibleSecondBtn: $possibleNextSecondBtnIndex")

                        return true
                    }
                }
            }
        }

        return false
    }

    // move cards on the field
    fun moveCards(gameLevel: Int, card1: Int, card2: Int){

        when (gameLevel){

            2 -> cardIdImgId = gameLevelMove.two(cardIdImgId, card1, card2)

            3 -> cardIdImgId = gameLevelMove.three(cardIdImgId, card1, card2)

            4 -> cardIdImgId = gameLevelMove.four(cardIdImgId, card1, card2)

            5 -> cardIdImgId = gameLevelMove.five(cardIdImgId, card1, card2)

            6 -> cardIdImgId = gameLevelMove.six(cardIdImgId)

            7 -> cardIdImgId = gameLevelMove.seven(cardIdImgId, card1, card2)

            8 -> cardIdImgId = gameLevelMove.eight(cardIdImgId, card1, card2)

            9 -> cardIdImgId = gameLevelMove.nine(cardIdImgId, card1, card2)

            10 -> cardIdImgId = gameLevelMove.ten(cardIdImgId, card1, card2)

            11 -> cardIdImgId = gameLevelMove.eleven(cardIdImgId)

            12 -> cardIdImgId = gameLevelMove.twelve(cardIdImgId, card1, card2)

            13 -> cardIdImgId = gameLevelMove.thirteen(cardIdImgId, card1, card2)

            14 -> cardIdImgId = gameLevelMove.fourteen(cardIdImgId)

            15 -> cardIdImgId = gameLevelMove.fifteen(cardIdImgId)

            16 -> cardIdImgId = gameLevelMove.sixteen(cardIdImgId)

            17 -> cardIdImgId = gameLevelMove.seventeen(cardIdImgId)

            18 -> cardIdImgId = gameLevelMove.eighteen(cardIdImgId, card1, card2)

            19 -> cardIdImgId = gameLevelMove.nineteen(cardIdImgId)

            20 -> cardIdImgId = gameLevelMove.twenty(cardIdImgId)

            21 -> cardIdImgId = gameLevelMove.twentyOne(cardIdImgId, card1, card2)

            22 -> cardIdImgId = gameLevelMove.twentyTwo(cardIdImgId, card1, card2)

            23 -> cardIdImgId = gameLevelMove.twentyThree(cardIdImgId, card1, card2)

            24 -> cardIdImgId = gameLevelMove.twentyFour(cardIdImgId)

            25 -> cardIdImgId = gameLevelMove.twentyFive(cardIdImgId)

            26 -> cardIdImgId = gameLevelMove.twentySix(cardIdImgId)

            27 -> cardIdImgId = gameLevelMove.twentySeven(cardIdImgId)

            28 -> cardIdImgId = gameLevelMove.twentyEight(cardIdImgId)

            29 -> cardIdImgId = gameLevelMove.twentyNine(cardIdImgId)

            30 -> cardIdImgId = gameLevelMove.thirty(cardIdImgId)
        }
    }

    // add score
    fun addScore(curScore: Int): String{

        val tmpScore = curScore + 50

        var scoreStr = "%d".format(tmpScore)

        when {

            tmpScore < 100 -> scoreStr = "0000%d".format(tmpScore)

            tmpScore in 100..999 -> scoreStr = "000%d".format(tmpScore)

            tmpScore in 1000..9999 -> scoreStr = "00%d".format(tmpScore)

            tmpScore in 10000..99999 -> scoreStr = "0%d".format(tmpScore)
        }

        return scoreStr
    }

    fun isLevelComplete(): Boolean{
        return hidedCardsCount == 144
    }

    // reset game vars
    fun resetGameVars(){

        hidedCardsCount = 0

        possibleNextFirstBtnIndex = -1

        possibleNextSecondBtnIndex = -1

        cardIdImgId.values.clear()

        isSearchClicked = false
    }

    fun fillFull(){

        var imgIndex = 1

        val arrList = ArrayList<Int>()

        for (i in 0 .. 143){

            arrList.add(imgIndex++)

            if (imgIndex == 37) imgIndex = 1
        }

        arrList.shuffle()

        for (i in 0 .. 143){

            cardIdImgId[i] = arrList[i]
        }
    }

    fun getDefImgIds(): HashMap<Int, Int> {
        return hashMapOf(
            1 to R.drawable.i1,
            2 to R.drawable.i2,
            3 to R.drawable.i3,
            4 to R.drawable.i4,
            5 to R.drawable.i5,
            6 to R.drawable.i6,
            7 to R.drawable.i7,
            8 to R.drawable.i8,
            9 to R.drawable.i9,
            10 to R.drawable.i10,
            11 to R.drawable.i11,
            12 to R.drawable.i12,
            13 to R.drawable.i13,
            14 to R.drawable.i14,
            15 to R.drawable.i15,
            16 to R.drawable.i16,
            17 to R.drawable.i17,
            18 to R.drawable.i18,
            19 to R.drawable.i19,
            20 to R.drawable.i20,
            21 to R.drawable.i21,
            22 to R.drawable.i22,
            23 to R.drawable.i23,
            24 to R.drawable.i24,
            25 to R.drawable.i25,
            26 to R.drawable.i26,
            27 to R.drawable.i27,
            28 to R.drawable.i28,
            29 to R.drawable.i29,
            30 to R.drawable.i30,
            31 to R.drawable.i31,
            32 to R.drawable.i32,
            33 to R.drawable.i33,
            34 to R.drawable.i34,
            35 to R.drawable.i35,
            36 to R.drawable.i36 )
    }

    fun getSearcImgIds(): HashMap<Int, Int> {
        return hashMapOf(
            1 to R.drawable.o1,
            2 to R.drawable.o2,
            3 to R.drawable.o3,
            4 to R.drawable.o4,
            5 to R.drawable.o5,
            6 to R.drawable.o6,
            7 to R.drawable.o7,
            8 to R.drawable.o8,
            9 to R.drawable.o9,
            10 to R.drawable.o10,
            11 to R.drawable.o11,
            12 to R.drawable.o12,
            13 to R.drawable.o13,
            14 to R.drawable.o14,
            15 to R.drawable.o15,
            16 to R.drawable.o16,
            17 to R.drawable.o17,
            18 to R.drawable.o18,
            19 to R.drawable.o19,
            20 to R.drawable.o20,
            21 to R.drawable.o21,
            22 to R.drawable.o22,
            23 to R.drawable.o23,
            24 to R.drawable.o24,
            25 to R.drawable.o25,
            26 to R.drawable.o26,
            27 to R.drawable.o27,
            28 to R.drawable.o28,
            29 to R.drawable.o29,
            30 to R.drawable.o30,
            31 to R.drawable.o31,
            32 to R.drawable.o32,
            33 to R.drawable.o33,
            34 to R.drawable.o34,
            35 to R.drawable.o35,
            36 to R.drawable.o36
        )
    }

    fun getPressImgIds(): HashMap<Int, Int> {
        return hashMapOf(
            1 to R.drawable.b1,
            2 to R.drawable.b2,
            3 to R.drawable.b3,
            4 to R.drawable.b4,
            5 to R.drawable.b5,
            6 to R.drawable.b6,
            7 to R.drawable.b7,
            8 to R.drawable.b8,
            9 to R.drawable.b9,
            10 to R.drawable.b10,
            11 to R.drawable.b11,
            12 to R.drawable.b12,
            13 to R.drawable.b13,
            14 to R.drawable.b14,
            15 to R.drawable.b15,
            16 to R.drawable.b16,
            17 to R.drawable.b17,
            18 to R.drawable.b18,
            19 to R.drawable.b19,
            20 to R.drawable.b20,
            21 to R.drawable.b21,
            22 to R.drawable.b22,
            23 to R.drawable.b23,
            24 to R.drawable.b24,
            25 to R.drawable.b25,
            26 to R.drawable.b26,
            27 to R.drawable.b27,
            28 to R.drawable.b28,
            29 to R.drawable.b29,
            30 to R.drawable.b30,
            31 to R.drawable.b31,
            32 to R.drawable.b32,
            33 to R.drawable.b33,
            34 to R.drawable.b34,
            35 to R.drawable.b35,
            36 to R.drawable.b36
        )
    }
}