package uz.shoxrux.notesapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import uz.shoxrux.presentation.ui.color.AppColors
import uz.shoxrux.presentation.ui.color.LocalAppColors

private val DarkColorScheme = AppColors(
    background = Color(0xFF0C0B09),
    primary = Color(0xFFFF9800),
    secondary = Color(0xFFFFBE6C),
    stroke = Color(0xFF868686),
    border = Color(0xFFADADAD),
    titleText = Color(0xFFFFFADA),
    contentText = Color(0xFFFFF3A9),
    hint = Color(0xFF939393),
    gray = Color(0xFF3D3A31),
    transparent = Color(0x00FFFFFF),
    semiTransparent = Color(0x9C898989),
    textButton = Color(0xFF3F51B5)
)

private val LightColorScheme = AppColors(
    background = Color(0xFFFFF9F2),
    primary = Color(0xFFFF9800),
    secondary = Color(0xFFFFBE6C),
    stroke = Color(0xFF535353),
    border = Color(0xFF656565),
    titleText = Color(0xFF3B3832),
    contentText = Color(0xFF232019),
    hint = Color(0xFF5C5C5C),
    gray = Color(0xFF8B846E),
    transparent = Color(0x00FFFFFF),
    semiTransparent = Color(0x9C898989),
    textButton = Color(0xFF3F51B5)
)

@Composable
fun NotesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val materialColorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    val appColors = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(
        LocalAppColors provides appColors,
    ) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = Typography(),
            content = content
        )
    }
}