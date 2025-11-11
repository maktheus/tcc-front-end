import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import ui.AppRoot

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow("TCC Frontend") {
        AppRoot()
    }
}
