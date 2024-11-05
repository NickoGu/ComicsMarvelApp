package com.unlam.edu.ar


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale


import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.unlam.edu.ar.data.Comic
import comicsmarvelapp.composeapp.generated.resources.Res
import comicsmarvelapp.composeapp.generated.resources.chevron
import comicsmarvelapp.composeapp.generated.resources.doom_banner
import comicsmarvelapp.composeapp.generated.resources.no_superhero


import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

//https://dribbble.com/shots/6491257-Comic-book-Reading-App/attachments/6491257-Comic-book-Reading-App?mode=media

@Composable
fun HomeScreen(paddingValue: PaddingValues, controller: NavController) {

    var superhero by remember { mutableStateOf<List<Character?>>(emptyList()) }
    var comics by remember { mutableStateOf<List<Comic?>>(emptyList()) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            val client = MarvelApiClient()
            val repository = MarvelRepositoryImpl(client)
            val service = CharactersService(repository)
            superhero = service.getCharacters()
            comics = service.getComics()
        }
    }


    Scaffold (
        content = { paddingValues ->
            Column {
                LazyColumn {
                    item {
                        Box(
                            modifier = Modifier.width(360.dp).height(230.dp).background(Color(0xFFf97f02))
                        ){
                            Image(
                                painter = painterResource(Res.drawable.doom_banner),
                                contentDescription = "Banner",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth().fillMaxHeight(
                            ))

                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Bottom,
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxHeight().fillMaxWidth().padding(16.dp),
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Text("DR DOOM IS BACK", style = TextStyle(fontWeight = FontWeight.Black,  shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(0f, 0f),
                                        blurRadius = 10f
                                    )), color = Color.White, fontSize = 40.sp, )
                                    Text("¡NEW DOOM COMIC AVAILABLE!", style = TextStyle(fontWeight = FontWeight.Black,  shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(0f, 0f),
                                        blurRadius = 10f
                                    )), color = Color.White, fontSize = 10.sp)


                                }



                            }
                        }
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("SUPERHEROES", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer( modifier = Modifier.width(16.dp))
                            Image(
                                painter = painterResource( Res.drawable.chevron ),
                                contentDescription = "Search",
                                modifier = Modifier.height(28.dp)
                            )
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(
                                    start = 12.dp,
                                    end = 12.dp
                                )
                        ){
                            items(superhero.size){ index ->
//                                val imageRequest = ImageRequest.Builder(LocalContext.current)
//                                    .data(superhero[index]?.thumbnailUrl)
//                                    .crossfade(true)
//                                    .listener(
//                                        onError = { _, result ->
//                                            Log.e("ImageLoadError", "Error loading image: ${result.throwable.message}")
//                                        }
//                                    )
//                                    .build()

                                    AsyncImage(
                                            model  = superhero[index]?.thumbnailUrl,
                                        contentDescription = superhero[index]?.name ?: "",
                                        error = painterResource(Res.drawable.no_superhero),
                                        placeholder = painterResource(Res.drawable.no_superhero),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.clip(CircleShape).height(80.dp).width(80.dp).aspectRatio(1f).clickable {
                                            controller.navigate("superhero_details/${superhero[index]?.id}"){
                                                launchSingleTop = true

                                            }
                                        },
                                    )
                                    //Log.d("ImageURL", superhero[index]?.thumbnailUrl ?: "URL is null")


                            }
                        }
                        Row(
                            modifier = Modifier.padding(12.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("BEST COMICS", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            Spacer( modifier = Modifier.width(16.dp))
                           Image(
                                painter = painterResource( Res.drawable.chevron ),
                                contentDescription = "Search",
                                modifier = Modifier.height(28.dp)
                           )
                        }
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .padding(
                                    start = 12.dp,
                                    end = 12.dp
                                )
                        ){
                            items(comics.size){ index ->
                                Column (
                                    modifier = Modifier.padding(4.dp).width(120.dp)
                                ){
                                    AsyncImage(
                                        model = comics[index]?.thumbnailUrl,
                                        contentDescription = comics[index]?.title ?: "",
                                       error = painterResource(Res.drawable.doom_banner),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).height(200.dp).width(120.dp).aspectRatio(0.6f).clickable {
                                            controller.navigate("comic_details/${comics[index]?.id}"){
                                                launchSingleTop = true

                                            }
                                        },
                                    )
                                    Spacer( modifier = Modifier.height(4.dp))
                                    Text(comics[index]?.title ?: "Error", fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                    Text(comics[index]?.description ?: "Error", fontSize = 10.sp, maxLines = 5, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }


    )
}


@Preview
@Composable
fun HomeScreenPreview() {
    val controller = rememberNavController()
    HomeScreen(paddingValue = PaddingValues(0.dp), controller = controller)
}