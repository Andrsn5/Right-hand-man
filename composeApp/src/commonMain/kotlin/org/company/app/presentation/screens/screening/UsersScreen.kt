package org.company.app.presentation.screens.screening

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.seiko.imageloader.rememberImagePainter
import kotlinx.coroutines.launch
import org.company.app.domain.model.UserProfile
import org.koin.compose.koinInject
import kotlin.math.roundToInt

class UsersScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: UsersViewModel = koinInject()
        val state by viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.onEvent(UsersEvent.LoadUsers)
        }

        MaterialTheme {
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    state.isLoading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    state.error != null -> {
                        Text(
                            "Error: ${state.error}",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    else -> {
                        SwipeableCardStack(users = state.users)
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeableCardStack(users: List<UserProfile>) {
    var currentIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentIndex >= users.size) {
            Text(
                "No more users",
                style = MaterialTheme.typography.headlineMedium
            )
        } else {
            UserCard(
                user = users[currentIndex],
                onSwipeLeft = { currentIndex++ },
                onSwipeRight = { currentIndex++ }
            )
        }
    }
}

@Composable
fun UserCard(
    user: UserProfile,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    
    val offsetX = remember { Animatable(0f) }
    val velocityTracker = remember { VelocityTracker() }
    
    val swipeThreshold = with(density) { 150.dp.toPx() }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        val velocity = velocityTracker.calculateVelocity().x
                        val totalDrag = offsetX.value + velocity * 0.1f
                        
                        coroutineScope.launch {
                            when {
                                totalDrag > swipeThreshold -> {
                                    offsetX.animateTo(
                                        targetValue = 1000f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                                    onSwipeRight()
                                    offsetX.snapTo(0f)
                                }
                                totalDrag < -swipeThreshold -> {
                                    offsetX.animateTo(
                                        targetValue = -1000f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    )
                                    onSwipeLeft()
                                    offsetX.snapTo(0f)
                                }
                                else -> {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )
                                }
                            }
                        }
                        velocityTracker.resetTracking()
                    },
                    onDragCancel = {
                        velocityTracker.resetTracking()
                        coroutineScope.launch {
                            offsetX.animateTo(0f)
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        coroutineScope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    }
                )
            }
            .offset {
                IntOffset(
                    offsetX.value.roundToInt(),
                    0
                )
            }
            .graphicsLayer {
                rotationZ = offsetX.value / 30f
            }
    ) {
        CardContent(user, offsetX.value, swipeThreshold)
    }
}

@Composable
fun CardContent(user: UserProfile, dragOffset: Float, swipeThreshold: Float) {
    val likeOpacity = (dragOffset / swipeThreshold).coerceIn(0f, 1f)
    val nopeOpacity = (-dragOffset / swipeThreshold).coerceIn(0f, 1f)
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Image(
            painter = rememberImagePainter(user.imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
        )
        
        if (likeOpacity > 0.01f) {
            Text(
                text = "LIKE",
                color = Color.Green,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(24.dp)
                    .graphicsLayer { alpha = likeOpacity }
            )
        }
        
        if (nopeOpacity > 0.01f) {
            Text(
                text = "NOPE",
                color = Color.Red,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .graphicsLayer { alpha = nopeOpacity }
            )
        }
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(24.dp)
        ) {
            Text(
                text = user.name,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "${user.age}",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = user.description,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}