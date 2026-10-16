package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs

enum class PageTurnDirection {
    FORWARD, BACKWARD, NONE
}

@Composable
fun PageTurnContainer(
    currentPage: Int,
    totalPages: Int,
    reducedMotion: Boolean,
    onNavigateNext: () -> Unit,
    onNavigatePrev: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (page: Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val density = LocalDensity.current

    // Displayed page state and outgoing page for transition
    var displayPage by remember { mutableStateOf(currentPage) }
    var animatingDirection by remember { mutableStateOf(PageTurnDirection.NONE) }
    
    // Animation progress 0f -> 1f
    val turnProgress = remember { Animatable(0f) }
    var dragAccumulator by remember { mutableFloatStateOf(0f) }
    var isDragging by remember { mutableStateOf(false) }

    LaunchedEffect(currentPage) {
        if (currentPage != displayPage) {
            val direction = if (currentPage > displayPage) PageTurnDirection.FORWARD else PageTurnDirection.BACKWARD
            animatingDirection = direction
            
            if (reducedMotion) {
                displayPage = currentPage
                animatingDirection = PageTurnDirection.NONE
            } else {
                turnProgress.snapTo(0f)
                turnProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 380, easing = FastOutSlowInEasing)
                )
                displayPage = currentPage
                turnProgress.snapTo(0f)
                animatingDirection = PageTurnDirection.NONE
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(currentPage, totalPages, reducedMotion) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        dragAccumulator = 0f
                        isDragging = true
                    },
                    onDragEnd = {
                        isDragging = false
                        val swipeThreshold = with(density) { 60.dp.toPx() }
                        if (dragAccumulator < -swipeThreshold && currentPage < totalPages) {
                            onNavigateNext()
                        } else if (dragAccumulator > swipeThreshold && currentPage > 1) {
                            onNavigatePrev()
                        }
                        dragAccumulator = 0f
                    },
                    onDragCancel = {
                        isDragging = false
                        dragAccumulator = 0f
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()
                        dragAccumulator += dragAmount
                    }
                )
            }
    ) {
        val progress = turnProgress.value
        val isAnimating = animatingDirection != PageTurnDirection.NONE

        if (!isAnimating) {
            // Static active page with subtle depth edge
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        // Subtle book spine shadow on the left edge
                        drawRect(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.12f),
                                    Color.Transparent
                                ),
                                startX = 0f,
                                endX = 24.dp.toPx()
                            )
                        )
                    }
            ) {
                content(currentPage)
            }
        } else {
            // 3D Curl / Flip transition between displayPage and currentPage
            val angle = if (animatingDirection == PageTurnDirection.FORWARD) {
                -progress * 90f
            } else {
                (1f - progress) * 90f
            }

            val shadowAlpha = (abs(angle) / 90f) * 0.35f
            val origin = if (animatingDirection == PageTurnDirection.FORWARD) {
                TransformOrigin(0f, 0.5f) // hinge on left spine
            } else {
                TransformOrigin(1f, 0.5f) // hinge on right
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = angle
                        cameraDistance = 14f * density.density
                        transformOrigin = origin
                    }
            ) {
                content(if (progress < 0.5f) displayPage else currentPage)

                // Depth shadow layer simulating page curl lighting
                if (shadowAlpha > 0.01f) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = if (animatingDirection == PageTurnDirection.FORWARD) {
                                        listOf(
                                            Color.Black.copy(alpha = shadowAlpha * 1.5f),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = shadowAlpha)
                                        )
                                    } else {
                                        listOf(
                                            Color.Black.copy(alpha = shadowAlpha),
                                            Color.Transparent,
                                            Color.Black.copy(alpha = shadowAlpha * 1.5f)
                                        )
                                    }
                                )
                            )
                    )
                }
            }
        }
    }
}
