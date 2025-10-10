import java.math.BigDecimal
import java.math.RoundingMode

fun formatCount(count: Int): String {
    val value = BigDecimal(count)
    return when {
        count < 1_000 -> value.toPlainString()
        count < 10_000 -> value.divide(BigDecimal(1000)).setScale(1, RoundingMode.DOWN).toPlainString() + "K"
        count < 1_000_000 -> value.divide(BigDecimal(1000)).setScale(0, RoundingMode.DOWN).toPlainString() + "K"
        else -> value.divide(BigDecimal(1_000_000)).setScale(1, RoundingMode.DOWN).toPlainString() + "M"
    }
}