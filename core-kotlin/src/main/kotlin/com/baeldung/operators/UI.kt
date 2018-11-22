interface View
class TextView(val value: String) : View
class ImageView(val url: String) : View

class UI {
    private val views = mutableListOf<View>()

    operator fun String.unaryPlus() {
        views.add(TextView(this))
    }

    operator fun View.unaryPlus() {
        views.add(this)
    }
}

fun ui(init: UI.() -> Unit): UI {
    val ui = UI()
    ui.init()

    return ui
}

fun main(args: Array<String>) {
    val header = ui {
        +ImageView("http://logo.com")
        +"Brand name"
        +"Brand description"
    }
}