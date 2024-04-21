package com.example.sharemarket.presentation.company_info

import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharemarket.domain.model.IntradayInfo
import kotlin.math.round
import kotlin.math.roundToInt


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StockChart(
    infos: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green.copy(alpha = 0.5f)
) {
    val spacing = 100f
    val transparentGraphColor = remember {
        graphColor
    }
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0

    }
    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.close }?.toInt() ?: 0
    }

    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier) {

        val spaceParHour = (size.width - spacing)/infos.size
        (0 until infos.size -1 step 2).forEach {i->
            val info = infos[i]
            val hour = info.date.hour
            drawContext.canvas.nativeCanvas.apply {
                drawText(hour.toString(),spaceParHour+i+spaceParHour,size.height-5,textPaint)
            }
        }
        val priceStep = (upperValue - lowerValue)/5f
        (1..5).forEach { i->
            drawContext.canvas.nativeCanvas.apply {
                drawText(round(lowerValue + priceStep*i).toString(),30f,size.height - spacing-1*size.height/5f,textPaint)
            }
        }
        var lastX = 0F
        val strokePaht = Path().apply {
            val height = size.height
            for(i in infos.indices)
            {
                val info = infos[i]
                val nextInfo = infos.getOrNull(i+1) ?: infos.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue)/(upperValue - lowerValue)
               val x1 = spacing+i*spaceParHour
                val y1 = height - spacing - (leftRatio*height).toFloat()
                val x2 = height - spacing -(rightRatio*height).toFloat()
                val y2 = height - spacing - (rightRatio*height).toFloat()
                if(i==0)
                {
                    moveTo(x1,y1)
                }
               lastX = (x1+x2)/2f
                quadraticBezierTo(x1,y1,lastX,(y1+y2)/2f)
            }
        }

        val fillPath = android.graphics.Path((strokePaht.asAndroidPath()))
            .asComposePath().apply {
                lineTo(lastX,size.height-spacing)
                lineTo(spacing,size.height-spacing)
                close()
            }
        drawPath(path = fillPath, brush = Brush.verticalGradient(colors = listOf(transparentGraphColor,Color.Transparent),endY = size.height -spacing))
        drawPath(path = strokePaht,color = graphColor, style = Stroke(width = 3.dp.toPx(),cap = StrokeCap.Round))
    }
}