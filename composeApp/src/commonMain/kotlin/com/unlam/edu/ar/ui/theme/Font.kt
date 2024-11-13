package com.unlam.edu.ar.ui.theme


import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

import comicsmarvelapp.composeapp.generated.resources.Res
import comicsmarvelapp.composeapp.generated.resources.poppins_bold
import comicsmarvelapp.composeapp.generated.resources.poppins_light
import comicsmarvelapp.composeapp.generated.resources.poppins_regular
import comicsmarvelapp.composeapp.generated.resources.poppins_semibold
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PoppinsFontFamily() = FontFamily(
    Font(Res.font.poppins_light, FontWeight.Light),
    Font(Res.font.poppins_regular, FontWeight.Normal),
    Font(Res.font.poppins_semibold, FontWeight.SemiBold),
    Font(Res.font.poppins_bold, FontWeight.Bold)
)

@Composable
fun PoppinsTypography() = Typography.run {

    val fontFamily = PoppinsFontFamily()
}