package mmcs.assignment2
fun main()
{
        val m:Matrix<Int> = createMatrix(5,3,10)
        val m1:Matrix<Int> = createMatrix(5,3,10)
        val m2:Matrix<Int> = createMatrix(5,3,20)
        println("Test plus passed:" + (m.plus(m1) == m2).toString());
        val m3:Matrix<Int> = createMatrix(5,3,-20)
        println("Test unaryMinus passed:" + (m2.unaryMinus() == m3).toString());
        val m31:Matrix<Int> = createMatrix(3,2,3)
        val m32:Matrix<Int> = createMatrix(2,4,3)
        val m33:Matrix<Int> = createMatrix(3,4,18)
        println("Test times passed:" + (m31.times(m32) == m33).toString());
        val m41:Matrix<Int> = createMatrix(5,4,0)
        m41[0,0] = 1
        m41[0,2] = 1
        m41[1,2] = 1
        m41[2,0] = 1
        m41[3,2] = 1
        val m42 = Holes(rows = listOf(4), columns = listOf(1,3))
        println("Test findHoles passed:" + (findHoles(m41)==m42).toString());
        val m51:Matrix<Int> = createMatrix(3,3,1)
        m51[0,1] = 0
        m51[1,0] = 0
        m51[1,2] = 0
        val m52:Matrix<Int> = createMatrix(2,2,1)
        m52[0,1] = 0
        m52[1,0] = 0
        println("Test canOpenLock passed:" + (canOpenLock(m52,m51) == Triple(true,0,1)).toString());


}
