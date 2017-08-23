package lynxz.org.kotlinapplication

/**
 * Created by lynxz on 03/08/2017.
 * 高阶函数demo,可以将函数作为参数传入函数中
 */
object HighOrderFunDemo {

    val printMsg = { str: String ->
        println(str)
    }

    val log = { str: String, printLog: (String) -> Unit ->
        printLog(str)
    }

    @JvmStatic fun main(args: Array<String>) {
        println("hello")
        log("world", printMsg)

        repeat(3) {
            log("kotlin$it") {
                println("hello $it")
            }
        }

        println("kotlin".run { toUpperCase() })

        val map = mapOf("a" to 1, "b" to 2)
        for (k in map.keys) {
            println("$k ${map[k]}")
        }

    }
}