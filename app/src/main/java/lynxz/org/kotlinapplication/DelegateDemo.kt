package lynxz.org.kotlinapplication

import kotlin.properties.Delegates

/**
 * Created by lynxz on 03/08/2017.
 */
object DelegateDemo {

    // 当指定变量值有变化时,会回调通知
    var status: Int by Delegates.observable(0) { _, o, n ->
        println("oldValue = $o , newValue = $n")
    }

    @JvmStatic fun main(args: Array<String>) {
        println("$status")
        status = 2
    }
}