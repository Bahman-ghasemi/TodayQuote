package ir.bahmanghasemi.todayquote.common.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.bahmanghasemi.todayquote.R

val fontFamilyPtSerifCaption = FontFamily(
    Font(R.font.pt_serif_caption)
)

val fontFamilySupraRounded = FontFamily(
    Font(R.font.supra_demibold_rounded)
)

val fontFamilyMyriad = FontFamily(
    Font(R.font.myriad)
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = fontFamilyPtSerifCaption,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamilyPtSerifCaption,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamilyPtSerifCaption,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamilyMyriad,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
)


val LocalFontFamilyPtSerifCaption = compositionLocalOf { fontFamilyPtSerifCaption }
val LocalFontFamilySupraRounded = compositionLocalOf { fontFamilySupraRounded }
val LocalFontFamilyMyriad = compositionLocalOf { fontFamilyMyriad }

@Composable
fun ProvideFontFamilies(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalFontFamilyPtSerifCaption provides fontFamilyPtSerifCaption,
        LocalFontFamilySupraRounded provides fontFamilySupraRounded,
        LocalFontFamilyMyriad provides fontFamilyMyriad,
        content = content
    )
}