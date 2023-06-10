package com.polariss.blur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.polariss.blur.ui.theme.BlurTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlurTheme {
// A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CenteredImage()

                }
            }
        }
    }
}

@Composable
fun CenteredImage() {
// 创建一个可变状态，用来控制动画的开始和结束
    val isExpanded = remember { mutableStateOf(true) }
// 创建一个动画的dp值，用来表示图片的大小
    val size by animateDpAsState(
        targetValue = if (isExpanded.value) 300.dp else 1000.dp, // 根据状态切换目标值
        animationSpec = spring( // 使用弹性插值器
            dampingRatio = Spring.DampingRatioLowBouncy, // 调低阻尼比，增加弹性效果
            stiffness = Spring.StiffnessVeryLow // 调低刚度，减少回弹次数

        )
    )
// 创建一个动画的float值，用来表示图片的模糊半径 (已弃用)
    val blur by animateFloatAsState(
        targetValue = if (isExpanded.value) 0f else 800f, // 根据状态切换目标值
        animationSpec = tween( // 使用缓动插值器
            durationMillis = 600, // 增加持续时间，减少变化速率
            easing = CubicBezierEasing(0.6F, 0.21F, 0.36F, 0.96F)
        )
    )
// 创建一个动画的dp值，用来表示图片的圆角半径
    val corner by animateDpAsState(
        targetValue = if (isExpanded.value) 150.dp else 0.dp, // 根据状态切换目标值
        animationSpec = tween( // 使用线性插值器
            durationMillis = 400,
            easing = CubicBezierEasing(0.6F, 0.21F, 0.36F, 0.96F)
            //easing = FastOutSlowInEasing
        )
    )
    Box(modifier = Modifier.fillMaxSize ()) {
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = "Image",
            modifier = Modifier
                .blur(radiusX = blur.dp, radiusY = blur.dp) // 设置图片模糊为动画值(已弃用) */
                .size(size) // 设置图片大小为动画值
                .align(Alignment.Center) // 设置图片在Box中居中对齐
                .clip(RoundedCornerShape(corner)) // 设置图片圆角为动画值
                .clickable { // 设置图片可点击，切换动画状态
                    isExpanded.value = !isExpanded.value
                },
            contentScale = ContentScale.Crop // 设置图片的缩放和裁剪方式为裁切
        )
    }
}