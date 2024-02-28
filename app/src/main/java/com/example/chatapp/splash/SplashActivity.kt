package com.example.chatapp.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.R
import com.example.chatapp.home.HomeActivity
import com.example.chatapp.login.LoginActivity
import com.example.chatapp.ui.theme.ChatAppTheme

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppTheme {
                Handler(Looper.getMainLooper()).postDelayed({
                }, 2000)
                SplashScreen(navigator = this)
            }
        }
    }

    override fun navigateToLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun navigateToHome() {
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel()
    ,navigator: Navigator) {
    viewModel .navigator = navigator
    viewModel.navigate()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (logo, signature) = createRefs()
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = "logo",
            modifier = Modifier
                .fillMaxWidth(0.4F)
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        Image(painter = painterResource(id = R.drawable.signature),
            contentDescription = "signature",
            modifier = Modifier
                .fillMaxWidth(0.45F)
                .padding(8.dp)
                .constrainAs(signature) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
//    SplashScreen()
}
