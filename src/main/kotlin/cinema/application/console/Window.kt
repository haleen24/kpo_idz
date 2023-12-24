interface Window {

    fun print()

    var parent: Window?

    fun stepIn(): Window?
}