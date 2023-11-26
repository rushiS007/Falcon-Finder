package com.example.falcon.views

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.falcon.R
import com.example.falcon.SharedPreferences
import com.example.falcon.data.repo.FalconRepo
import com.example.falcon.ui.theme.FalconTheme

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferencesManager: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferences(applicationContext)

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(falconRepo = FalconRepo(), sharedPreferences = SharedPreferences(applicationContext)))[MainViewModel::class.java]

        setContent {
            FalconTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if(isInternetAvailable(this))
                        MyApp(mainViewModel)
                    else{
                        Text(text = "Check your network connection", color = Color.Red, modifier = Modifier.padding(100.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun radioButtons(mainViewModel: MainViewModel, navController: NavHostController) {

    val rocketImageList = listOf<Int>(
        R.drawable.rocket_1,
        R.drawable.rocket_2,
        R.drawable.rocket_3,
        R.drawable.rocket_4
    )
    val planetImageList = listOf<Int>(
        R.drawable.planet_1,
        R.drawable.planet_2,
        R.drawable.planet_3,
        R.drawable.planet_4,
        R.drawable.planet_5,
        R.drawable.planet_6
    )
    var selectedList = remember {
        mainViewModel.selectedList
    }

    LaunchedEffect(key1 = Unit, block = {
        mainViewModel.getRockets()
    } )

    LaunchedEffect(key1 = Unit, block = {
        mainViewModel.getPlanets()
    } )


    var selectedRocket by remember { mutableStateOf(0) }
    var selectedPlanet by remember { mutableStateOf(0) }

    return Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

        Text(text = "Falcone Finder", fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(20.dp), fontSize = TextUnit(30F, TextUnitType.Sp), color = Color.Red)

        Row {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                mainViewModel.rockets.forEach { rocket ->
                    Row() {
                        RadioButton(
                            selected = (rocket.name == mainViewModel.rockets[selectedRocket].name),
                            onClick = { selectedRocket = mainViewModel.rockets.indexOf(rocket) }
                        )
                        Text(
                            text = rocket.name.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 14.dp)
                        )
                    }
                }
            }
            var mExpanded by remember { mutableStateOf(false) }
            var mSelectedText by remember { mutableStateOf("") }

            Column {

                val icon = if (mExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown

                Column(Modifier.padding(16.dp)) {

                    OutlinedTextField(
                        readOnly = true,
                        value = mSelectedText,
                        onValueChange = { mSelectedText = it },
                        modifier = Modifier
                            .fillMaxWidth(),
                        label = { Text("Planet") },
                        trailingIcon = {
                            Icon(icon, "contentDescription",
                                Modifier.clickable { mExpanded = !mExpanded })
                        }
                    )

                    DropdownMenu(
                        expanded = mExpanded,
                        onDismissRequest = { mExpanded = false }
                    ) {
                        mainViewModel.planets.forEach { planet ->
                            DropdownMenuItem(onClick = {
                                mSelectedText = planet.name.toString()
                                selectedPlanet = mainViewModel.planets.indexOf(planet)
                                mExpanded = false
                            }, text = { Text(text = planet.name.toString()) })
                        }
                    }
                }

            }
        }
        if(selectedList.size<4)
            Text(text = "Select rocket & planet ${selectedList.size + 1}")
        
        Row(modifier = Modifier.padding(0.dp,10.dp,0.dp,0.dp)) {
            Image(
                painter = painterResource(id = if (mainViewModel.rockets.isEmpty()) R.drawable.rocket_1 else rocketImageList[selectedRocket]),
                "",
                modifier = Modifier.size(200.dp)
            )
            Image(
                painter = painterResource(id = if (mainViewModel.planets.isEmpty()) R.drawable.planet_1 else planetImageList[selectedPlanet]),
                "",
                modifier = Modifier.size(200.dp)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(10.dp)) {
            if(selectedList.isNotEmpty() && selectedList.size<4) {
                Button(onClick = {
                    mainViewModel.removeLastItem()
                }) {
                    Text(text = "Prev")
                }
            }
            Button(onClick = {
                    if(selectedList.size <4)
                        selectedList.add(Pair(mainViewModel.rockets[selectedRocket].name!!,mainViewModel.planets[selectedPlanet].name!!))
                    else {
                        val planets = mutableListOf<String>()
                        val rockets = mutableListOf<String>()
                        selectedList.forEach {
                            rockets.add(it.first)
                            planets.add(it.second)
                        }
                        navController.navigate("second_screen/${planets.joinToString(",")}/${rockets.joinToString(",")}")
                    }
                    println(selectedList.forEach { println( "${it.first} ${it.second}" ) })
                }) {
                if(selectedList.size<4)
                    Text(text = "Next")
                else
                    Text(text = "Find Princess")
            }
        }
    }
}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun ResultScreen(planets: String, rockets: String, mainViewModel: MainViewModel) {
    val planetsList = planets.split(",")
    val rocketsList = rockets.split(",")
    LaunchedEffect(key1 = Unit, block = {mainViewModel.findPrincess(planetsList,rocketsList)})


    return Column (horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
        Text(text = "Falcone Finder", fontStyle = FontStyle.Italic, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(20.dp), fontSize = TextUnit(30F, TextUnitType.Sp), color = Color.Red)
        when (mainViewModel.findResponse.value.status) {
            "success" -> Text(text = "Princess found on ${mainViewModel.findResponse.value.planetName}",fontWeight = FontWeight.SemiBold, color = Color.Green)
            "false" -> Text(text = "Could not find Princess",fontWeight = FontWeight.SemiBold, color = Color.Red)
            else -> {
                Text(text = "Something went wrong (Token failure)",fontWeight = FontWeight.SemiBold, color = Color.Red)
            }
        }
        Image(painter = painterResource(id = R.drawable.planets), contentDescription = "",Modifier.padding(0.dp,50.dp))
    }


}

@Composable
fun mainScreen(mainViewModel: MainViewModel, navController: NavHostController){
    Column {
        radioButtons(mainViewModel, navController)
    }
}

@Composable
fun MyApp(mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "first_screen") {
        composable("first_screen") {
            mainScreen(mainViewModel,navController)
        }
        composable(
            route = "second_screen/{planets}/{rockets}",
            arguments = listOf(
                navArgument("planets") { type = NavType.StringType },
                navArgument("rockets") { type = NavType.StringType }
            )
        ) { entry ->
            ResultScreen(
                planets = entry.arguments?.getString("planets") ?: "",
                rockets = entry.arguments?.getString("rockets") ?: "",
                mainViewModel
            )
        }
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val ni = connectivityManager.activeNetworkInfo
    return ni != null && ni.state == NetworkInfo.State.CONNECTED
}