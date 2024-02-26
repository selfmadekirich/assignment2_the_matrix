@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if(height <= 0 || width <= 0)
        throw IllegalArgumentException("Invalid params")
    return MatrixImpl<E>(height,width,e)
}

/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>( h: Int, w: Int, e : E) : Matrix<E> {

    private val lst  = MutableList<MutableList<E>>(h){MutableList<E>(w){e} }

    override val height:Int = h

    override val width: Int = w

    private fun checkParams(row:Int,column: Int):Boolean{
      //  println(row.toString()+","+column.toString())
        return !(row < 0 || row >= lst.size || column < 0 || column >= lst[0].size)
    }

    private fun checkParams(c:Cell):Boolean{
        return checkParams(c.row,c.column)
    }

    override fun get(row: Int, column: Int): E {
        if(checkParams(row,column) && lst[row][column] != null)
          return lst[row][column]
        throw IllegalArgumentException("Invalid params")
    }

    override fun get(cell: Cell): E {
        return get(cell.row,cell.column)
    }

    override fun set(row: Int, column: Int, value: E) {
        if(checkParams(row,column))
            lst[row][column] = value
        else
            throw IllegalArgumentException("Invalid params")
    }

    override fun set(cell: Cell, value: E) {
        set(cell.row,cell.column,value)
    }

    override fun equals(other: Any?):Boolean{
        if(other !is MatrixImpl<*>)
            throw IllegalArgumentException("type mismatch")
        //if(other.height != this.height || other.width != this.width)
         //   return false
        return other.lst == this.lst
    }

    override fun toString(): String {
        var str:String = ""
        for (i in 0..<width)
            for(j in 0..<height)
                str += lst[j][i].toString() + ","
            str += "\n"
        return  str
    }
}
