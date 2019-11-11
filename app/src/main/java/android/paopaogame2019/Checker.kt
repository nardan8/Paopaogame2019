package android.paopaogame2019

class Checker {

    // check from up to down
    fun piFromUpToDown( firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int> ): Boolean {

        val difference = firstIndex - secondIndex
        var verticalEmptySpaces = 0

        for (i in (secondIndex + 16)..(secondIndex % 16 + 8 * 16) step 16)  {

            var horizontalEmptySpaces = 1

            if (mapBtnIdWithImgIndexes[i] != 0 || mapBtnIdWithImgIndexes[i + difference] != 0) return false

            for (j in (i + 1)..(i + difference - 1)){
                if (mapBtnIdWithImgIndexes[j] != 0) break
                ++horizontalEmptySpaces
            }

            if (horizontalEmptySpaces == difference) return true

            verticalEmptySpaces += 16
        }

        if (verticalEmptySpaces == secondIndex % 16 + 8 * 16 - secondIndex) return true

        return false
    }

    // check from down to up
    fun piFromDownToUp( firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int> ) : Boolean {

        val difference = firstIndex - secondIndex
        var verticalEmptySpaces = 0

        for (i in (secondIndex - 16) downTo (secondIndex % 16) step 16) {

            if (mapBtnIdWithImgIndexes[i] != 0 || mapBtnIdWithImgIndexes[i + difference] != 0) return false

            var horizontalEmptySpaces = 1

            for (j in (i + 1)..(i + difference - 1)){

                if (mapBtnIdWithImgIndexes[j] != 0) break
                ++horizontalEmptySpaces
            }

            if (horizontalEmptySpaces == difference) return true

            verticalEmptySpaces += 16
        }

        if (verticalEmptySpaces == secondIndex - secondIndex % 16) return true

        return false
    }

    // check horizontally from left to right
    fun piFromLeftToRight( firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int> ) : Boolean {

        val difference = firstIndex - secondIndex
        var horizontalEmptySpaces = 0

        for (i in (secondIndex + 1)..(secondIndex - secondIndex % 16 + 15)) {

            if (mapBtnIdWithImgIndexes[i] != 0 || mapBtnIdWithImgIndexes[i + difference] != 0) return false

            var verticalEmptySpaces = 1

            for ( j in ( i + 16 )..( i + difference - 16 ) step 16 ){

                if (mapBtnIdWithImgIndexes[j] != 0) break
                ++verticalEmptySpaces
            }

            if (verticalEmptySpaces == difference / 16) return true

            ++horizontalEmptySpaces
        }

        if (horizontalEmptySpaces == 15 - secondIndex % 16) return true

        return false
    }

    // check horizontally from right to left
    fun piFromRightToLeft( firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int> ) : Boolean {

        val difference = firstIndex - secondIndex
        var horizontalEmptySpaces = 0

        for (i in (secondIndex - 1) downTo (secondIndex - secondIndex % 16)) {

            if (mapBtnIdWithImgIndexes[i] != 0 || mapBtnIdWithImgIndexes[i + difference] != 0) return false

            var verticalEmptySpaces = 1

            for (j in (i + 16)..(i + difference - 16) step 16){

                if (mapBtnIdWithImgIndexes[j] != 0) break

                ++verticalEmptySpaces
            }

            if (verticalEmptySpaces == difference / 16) return true

            ++horizontalEmptySpaces
        }

        if (horizontalEmptySpaces == secondIndex % 16) return true

        return false
    }

    // check straight vertical, when two buttons on same row
    fun verticalStraight( firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {
        for (i in (secondIndex + 16)..(firstIndex - 16) step 16){
            if (mapBtnIdWithImgIndexes[i] != 0) return false
        }
        return true
    }

    // check straight horizontal, when two buttons on the same row
    fun horizontalStraight( firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {
        for (i in (secondIndex + 1)..(firstIndex - 1)){
            if (mapBtnIdWithImgIndexes[i] != 0) return false
        }
        return true
    }

    // check in horizontal z-style manner
    fun horizontalZ(firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        for (i in secondIndex..(secondIndex - secondIndex % 16 + firstIndex % 16)){

            if(i != secondIndex && mapBtnIdWithImgIndexes[i] != 0) return false

            var horEmptySpaces = 0

            for (j in firstIndex downTo (firstIndex - firstIndex % 16 + i % 16 )){

                if (j != firstIndex && mapBtnIdWithImgIndexes[j] != 0) break

                if (j != firstIndex) ++horEmptySpaces
            }

            if (horEmptySpaces == firstIndex % 16 - i % 16) {

                if (firstIndex / 16 - secondIndex / 16 == 1) return true

                var vertEmptySpaces = 16

                for (v in (i + 16)..(firstIndex - firstIndex % 16 + i % 16 - 16) step 16){

                    if (mapBtnIdWithImgIndexes[v] != 0) break
                    vertEmptySpaces += 16
                }

                if (vertEmptySpaces == firstIndex - firstIndex% 16 + secondIndex % 16 - secondIndex) return true
            }
        }

        return false
    }

    // check in vertical z-style manner
    fun verticalZ(firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        for (i in secondIndex + 16..(firstIndex - 16 - firstIndex % 16 + secondIndex % 16) step 16){

//            Log.i("Z", "(i, value, range): ${i}, ${mapBtnIdWithImgIndexes[i]}, (${secondIndex + 16}, ${firstIndex - 16 - firstIndex % 16 + secondIndex % 16})")
            if (mapBtnIdWithImgIndexes[i] != 0) return false

            var verEmptySpaces = 0

            for (j in (firstIndex - 16) downTo (i - i % 16 + firstIndex % 16) step 16){

//                Log.i("Z", "(j, value, range): ${j}, ${mapBtnIdWithImgIndexes[j]}, (${firstIndex - 16}, ${i - i % 16 + firstIndex % 16})")
                if (mapBtnIdWithImgIndexes[j] != 0) break

                verEmptySpaces += 16
//                Log.i("Z", "verEmptySpaces: ${verEmptySpaces}")
            }

//            Log.i("Z", "verEmptySpaces outside for loop: ${verEmptySpaces}")
//            Log.i("Z", "verEmptySpaces == firstIndex - firstIndex % 16 + secondIndex % 16 - i: ${verEmptySpaces} == ${firstIndex - firstIndex % 16 + secondIndex % 16 - i}")
            if (verEmptySpaces == firstIndex - firstIndex % 16 + secondIndex % 16 - i){

                if (firstIndex % 16 - secondIndex % 16 == 1) return true

                var horizontalEmptySpaces = 1

                for (h in (i + 1)..(i - secondIndex % 16 + firstIndex % 16 - 1)) {

//                    Log.i("Z", "(h, value, range): ${h}, ${mapBtnIdWithImgIndexes[h]}, (${i + 1}, ${i - secondIndex % 16 + firstIndex % 16 - 1})")
                    if (mapBtnIdWithImgIndexes[h] != 0) break

                    ++horizontalEmptySpaces
//                    Log.i("Z", "horizontalEmptySpaces: ${horizontalEmptySpaces}")
                }

//                Log.i("Z", "horizontalEmptySpaces outside for loop: ${horizontalEmptySpaces}")
//                Log.i("Z", "horizontalEmptySpaces == firstIndex % 16 - secondIndex % 16: ${horizontalEmptySpaces} == ${firstIndex % 16 - secondIndex % 16}")
                if (horizontalEmptySpaces == firstIndex % 16 - secondIndex % 16) {
                    return true
                }
            }
        }

        return false
    }

    // check is column empty from bottom btn row to top btn row
    fun isColEmptyFromFirstBtnRowToSecondBtnRow (firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        for (i in (firstIndex - 16) downTo (secondIndex + firstIndex % 16 - secondIndex % 16) step 16) {

            if (mapBtnIdWithImgIndexes[i] != 0) return false
        }

        return true
    }

    // check is column empty from top btn row to bottom btn row
    fun isColEmptyFromSecondBtnRowToFirstRow(firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        for (i in (secondIndex + 16)..(firstIndex - firstIndex % 16 + secondIndex % 16) step 16) {
            if (mapBtnIdWithImgIndexes[i] != 0) return false
        }
        return true
    }

    // check is row empty from the bottom btn column to the top btn column
    fun isRowEmptyFromFirstBtnColToSecondBtnCol (firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        // if top btn is located on the left side of bottom btn
        if (firstIndex % 16 > secondIndex % 16) {

            for (i in (firstIndex - 1)downTo (firstIndex - firstIndex % 16 + secondIndex % 16)){

                if (mapBtnIdWithImgIndexes[i] != 0) return false
            }
        }

        // if top btn is located on the right side of the bottom btn
        if (firstIndex % 16 < secondIndex % 16) {

            for (i in (firstIndex + 1)..(firstIndex - firstIndex % 16 + secondIndex % 16)) {

                if (mapBtnIdWithImgIndexes[i] != 0) return false
            }
        }

        return true
    }

    // check is row empty from top btn column to bottom btn column?
    fun isRowEmptyFromSecondBtnColToFirstBtnCol (firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        if (firstIndex % 16 > secondIndex % 16) {

            for (i in (secondIndex + 1)..(secondIndex - secondIndex % 16 + firstIndex % 16)){

                if (mapBtnIdWithImgIndexes[i] != 0) return false
            }
        }

        else {

            for (i in (secondIndex - 1) downTo (secondIndex - secondIndex % 16 + firstIndex % 16)) {

                if (mapBtnIdWithImgIndexes[i] != 0) return false
            }
        }

        return true
    }

    // S и Г образная проверка
    fun horizontalS(firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        for (i in secondIndex downTo (secondIndex - secondIndex % 16 + firstIndex % 16)){

            if (i != secondIndex && mapBtnIdWithImgIndexes[i] != 0) return false

            var horizontalEmptySpaces = 0

            for (j in firstIndex..(firstIndex - firstIndex % 16 + i % 16)){

                if (j != firstIndex && mapBtnIdWithImgIndexes[j] != 0) break

                ++horizontalEmptySpaces
            }

            if (horizontalEmptySpaces == i % 16 - firstIndex % 16 + 1) {

                if (firstIndex / 16 - secondIndex / 16 == 1) return true

                var verticalEmptySpaces = 16

                for (v in (i + 16)..(firstIndex - firstIndex % 16 + i % 16 - 16) step 16){

                    if (mapBtnIdWithImgIndexes[v] != 0) break

                    verticalEmptySpaces += 16
                }

                if (verticalEmptySpaces == firstIndex - firstIndex % 16 + secondIndex % 16 - secondIndex) return true
            }
        }

        return false
    }

    // check vertical s-style manner
    fun verticalS(firstIndex: Int, secondIndex: Int, mapBtnIdWithImgIndexes: HashMap<Int, Int>): Boolean {

        for (i in (secondIndex + 16)..(firstIndex - 16 - firstIndex % 16 + secondIndex % 16) step 16){

            if (mapBtnIdWithImgIndexes[i] != 0) return false

            var verEmptyCount = 0

            for (j in (firstIndex - 16) downTo (i - i % 16 + firstIndex % 16) step 16){

                if (mapBtnIdWithImgIndexes[j] != 0) break

                verEmptyCount += 16
            }

            if (verEmptyCount == firstIndex - (i - secondIndex % 16 + firstIndex % 16) ) {

                if (secondIndex % 16 - firstIndex % 16 == 1) return true

                var horizontalEmptySpaces = 1

                for (h in (i - secondIndex % 16 + firstIndex % 16 + 1)..(i - 1)) {

                    if (mapBtnIdWithImgIndexes[h] != 0) break

                    ++horizontalEmptySpaces
                }

                if (horizontalEmptySpaces == secondIndex % 16 - firstIndex % 16) return true
            }
        }

        return false
    }
}