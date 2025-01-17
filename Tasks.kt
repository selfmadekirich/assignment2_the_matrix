@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

import mmcs.assignment2.Matrix
import mmcs.assignment2.createMatrix

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

fun <E> rotate(matrix: Matrix<E>): Matrix<E> = transpose(matrix)

/**
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if(this.width != other.width || other.height != other.height)
        throw IllegalArgumentException("Invalid params")
    val nw = createMatrix(this.height,this.width,0)
    for (i in 0..<this.height)
        for(j in 0..<this.width)
            nw[i,j] =  this[i, j] + other[i, j]

    return  nw
}

/**
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int>{

    val nw = createMatrix(this.height,this.width,0)
    for (i in 0..<this.height)
        for(j in 0..<this.width)
            nw[i,j] =  -1 * this[i, j]
    return  nw
}

/**
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {

    if(this.width != other.height)
        throw IllegalArgumentException("Invalid params")

    val nw = createMatrix(this.height,other.width,0)
    for (i in 0..<this.height)
        for(j in 0..<other.width)
            for(k in 0..<this.width)
                nw[i,j] += this[i,k]*other[k,j]
    return  nw
}


/**
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes{
   val r = MutableList<Int>(0){0}
   val c = MutableList<Int>(0){0}

   val rr = MutableList<Int>(matrix.height){0}
    val cc = MutableList<Int>(matrix.width){0}

    for (i in 0..<matrix.height)
        for(j in 0..<matrix.width){
            rr[i] += matrix[i,j]
            cc[j] += matrix[i,j]
        }

    for (i in 0..<matrix.height)
        if(rr[i] == 0)
            r.add(i)

    for(j in 0..<matrix.width)
        if(cc[j] == 0)
            c.add(j)

    return Holes(rows = r, columns = c)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (i in 0..lock.width-key.width){
        for(j in 0..lock.height-key.height){
            var sum = 0
            for (ii in 0..<key.width){
                for(jj in 0..<key.height){
                    sum += key[ii,jj].xor(lock[i+ii,j+jj])
                }
            }
            if(sum == key.height * key.width)
                    return Triple(true,i,j)
        }
    }
    return Triple(false,-1,-1)
}