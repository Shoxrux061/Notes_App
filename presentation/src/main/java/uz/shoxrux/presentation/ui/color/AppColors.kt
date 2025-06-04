package uz.shoxrux.presentation.ui.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val stroke: Color,
    val border: Color,
    val titleText: Color,
    val contentText: Color,
    val hint: Color,
    val transparent: Color,
    val semiTransparent: Color,
    val gray: Color
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No AppColor provided")
}