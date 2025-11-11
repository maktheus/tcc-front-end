import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.AppRoot

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "TCC Frontend") {
        AppRoot()
    }
}
