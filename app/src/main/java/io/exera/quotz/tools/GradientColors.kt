package io.exera.quotz.tools

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import java.util.*

/**
 * list of gradients
 */
private val gradientColors: Array<Pair<String, String>> = arrayOf(
        Pair("#1A2980", "#26D0CE"),
        Pair("#AA076B", "#61045F"),
        Pair("#FF512F", "#DD2476"),
        Pair("#403B4A", "#E7E9BB"),
        Pair("#3CA55C", "#B5AC49"),
        Pair("#348F50", "#56B4D3"),
        Pair("#DA22FF", "#9733EE"),
        Pair("#02AAB0", "#00CDAC"),
        Pair("#EDE574", "#E1F5C4"),
        Pair("#D31027", "#EA384D"),
        Pair("#16A085", "#F4D03F"),
        Pair("#603813", "#b29f94"),
        Pair("#e52d27", "#b31217"),
        Pair("#314755", "#26a0da"),
        Pair("#2b5876", "#4e4376"),
        Pair("#e65c00", "#F9D423"),
        Pair("#2193b0", "#6dd5ed"),
        Pair("#cc2b5e", "#753a88"),
        Pair("#1488CC", "#2B32B2"),
        Pair("#00467F", "#A5CC82"),
        Pair("#536976", "#292E49"),
        Pair("#FFE000", "#799F0C"),
        Pair("#ffe259", "#ffa751"),
        Pair("#799F0C", "#ACBB78"),
        Pair("#5433FF", "#20BDFF"),
        Pair("#00416A", "#799F0C"),
        Pair("#799F0C", "#FFE000"),
        Pair("#00416A", "#FFE000"),
        Pair("#FFE000", "#799F0C"),
        Pair("#0F2027", "#2C5364"),
        Pair("#373B44", "#4286f4"),
        Pair("#2980B9", "#6DD5FA"),
        Pair("#FF0099", "#493240"),
        Pair("#8E2DE2", "#4A00E0"),
        Pair("#1f4037", "#99f2c8"),
        Pair("#c31432", "#240b36"),
        Pair("#f12711", "#f5af19"),
        Pair("#8360c3", "#2ebf91"),
        Pair("#FF416C", "#FF4B2B"),
        Pair("#8A2387", "#E94057"),
        Pair("#8A2387", "#E94057"),
        Pair("#a8ff78", "#78ffd6"),
        Pair("#ED213A", "#93291E"),
        Pair("#FDC830", "#F37335"),
        Pair("#00B4DB", "#0083B0"),
        Pair("#005AA7", "#FFFDE4"),
        Pair("#DA4453", "#89216B"),
        Pair("#ad5389", "#3c1053"),
        Pair("#a8c0ff", "#3f2b96"),
        Pair("#4e54c8", "#8f94fb"),
        Pair("#11998e", "#38ef7d"),
        Pair("#c94b4b", "#4b134f"),
        Pair("#00b09b", "#96c93d"),
        Pair("#00F260", "#0575E6")
)

/**
 * returns a random gradient drawable
 */
fun getGradient(): GradientDrawable {
    val random = Random()
    val number = random.nextInt(gradientColors.size - 1)
    val colorPair = gradientColors[number]

    val colors: IntArray = intArrayOf(
            Color.parseColor(colorPair.first),
            Color.parseColor(colorPair.second)
    )

    return GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT, colors)
}
