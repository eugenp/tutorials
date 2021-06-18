import javafx.scene.Parent
import tornadofx.*

class HeaderFooterView: View() {
    // Explicitly retrieve HeaderView
    private val headerView = find(HeaderView::class)
    // Create a lazy delegate
    private val footerView: FooterView by inject()

    override val root = borderpane {
        top = headerView.root
        bottom = footerView.root
    }
}
class HeaderView(override val root: Parent) : View()
class FooterView(override val root: Parent) : View()