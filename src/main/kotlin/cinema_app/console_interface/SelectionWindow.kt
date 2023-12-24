class SelectionWindow(

    private var windowInformation: String,

    var mapWindow: MutableMap<String, Window>,

    override var parent: Window?

) : Window {

    override fun stepIn(): Window? {

        val input = readln().trim()

        return mapWindow[input] ?: parent
    }

    override fun print() {

        println(windowInformation)

    }
}