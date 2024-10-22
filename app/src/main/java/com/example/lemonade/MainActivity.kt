package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                LemonApp()
            }
        }
    }
}

@Composable
fun LemonApp(){
    var currentStep by rememberSaveable { mutableStateOf(1) }
    var squeezeCount by rememberSaveable { mutableStateOf(0) }

    Scaffold (
        topBar = { LemonTopAppBar() }
    ){ innerPadding ->
        Surface (
            modifier = Modifier.padding(innerPadding)
        ){
            when(currentStep){
                1 -> {
                    LemonTextAndImage(R.drawable.lemon_tree, R.string.lemon_select, onImageClick = {
                        currentStep++
                        squeezeCount = (2..4).random()
                    })
                }
                2 -> {
                    LemonTextAndImage(R.drawable.lemon_squeeze, R.string.lemon_squeeze, onImageClick = {
                        squeezeCount--
                        if(squeezeCount == 0){
                            currentStep++
                        }
                    })
                }
                3 -> LemonTextAndImage(R.drawable.lemon_drink, R.string.lemon_drink, {currentStep++})
                else -> LemonTextAndImage(R.drawable.lemon_restart, R.string.lemon_empty_glass, {currentStep = 1})
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Lemonade",
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.lemon)
        )
    )
}

@Composable
fun LemonTextAndImage(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Button(
            onClick = onImageClick,
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.button_color))
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null
            )
        }
        Spacer(
            modifier = modifier.height(16.dp)
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonTopAppBarPreview() {
    LemonadeTheme {
        LemonTopAppBar()
    }
}

@Preview(showBackground = true)
@Composable
fun LemonTextAndImagePreview() {
    LemonadeTheme {
        LemonTextAndImage(R.drawable.lemon_tree, R.string.lemon_select, {})
    }
}