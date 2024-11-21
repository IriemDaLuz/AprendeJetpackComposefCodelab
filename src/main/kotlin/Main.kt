import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    MaterialTheme {
        if (shouldShowOnBoarding) {
            Inicio(onChangeValue = { shouldShowOnBoarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
fun Inicio(modifier: Modifier = Modifier, onChangeValue: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido al mejor módulo de la FP")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onChangeValue) {
            Text("Continuar")
        }
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(100) { "Alumno $it" } // Reduce la lista para pruebas.
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Cajas(name = name)
        }
    }
}

@Composable
fun Cajas(name: String) {
    var expanded by remember { mutableStateOf(false) }

    // Animar el padding vertical del Row según el estado 'expanded'
    val animatedPadding by animateDpAsState(
        targetValue = if (expanded) 48.dp else 8.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Row(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(vertical = animatedPadding, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Hola, $name!",
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                expanded = !expanded
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(if (expanded) "Show less" else "Show more")
        }
    }
    Divider()
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
