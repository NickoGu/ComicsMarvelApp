package com.unlam.edu.ar.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.unlam.edu.ar.Character
import com.unlam.edu.ar.MarvelApiClient
import com.unlam.edu.ar.MarvelRepositoryImpl
import com.unlam.edu.ar.data.CharactersService
import com.unlam.edu.ar.data.Comic
import comicsmarvelapp.composeapp.generated.resources.Res
import comicsmarvelapp.composeapp.generated.resources.no_superhero
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun ComicDetailsScreen(paddingValue: PaddingValues, controller: NavController, comicId: Int?) {
    var comic by remember { mutableStateOf<Comic?>(null) }
    var characters by remember { mutableStateOf<List<Character>>(emptyList()) }
    val scope = rememberCoroutineScope()
    val random = (0..10).random()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            val client = MarvelApiClient()
            val repository = MarvelRepositoryImpl(client)
            val service = CharactersService(repository)
            comic = service.getComicById(comicId!!)
            characters = service.getCharacterFromComic(comicId)
        }
    }

    Scaffold {  paddingValue ->
        Surface {
            LazyColumn {

                item {
                    Row (
                        modifier = Modifier.padding(16.dp).fillMaxWidth().height(260.dp),
                    ){
                        AsyncImage(
                            model = comic?.thumbnailUrl ?: "",
                            contentDescription = comic?.title ?: "No title",
                            modifier = Modifier.clip(RoundedCornerShape(8.dp)).padding(paddingValue).width(160.dp)
                        )
                        Column (
                            modifier = Modifier
                                .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                                .width(200.dp)
                                .fillMaxHeight(),
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.Top
                        ){
                            Text(comic?.title ?: "No title", style = TextStyle(fontSize = 20.sp), maxLines = 3, overflow = TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Rating: ${random}/10", style = TextStyle(fontSize = 16.sp))

                        }
                    }
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ){
                        Text("Description", style = TextStyle(fontSize = 20.sp))
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(if (comic?.description == null || comic?.description == "" || comic?.description == "no description" ) "No description available" else comic?.description!!)
                    }
                     Column (
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.Start
                     ) {
                         Spacer(modifier = Modifier.height(16.dp))
                         Text("Characters", style = TextStyle(fontSize = 20.sp))
                         Spacer(modifier = Modifier.height(8.dp))
                         if(characters.isEmpty()){
                             Text("No characters available")
                         }
                         LazyRow(
                             horizontalArrangement = Arrangement.spacedBy(8.dp),
                             modifier = Modifier
                                 .padding(
                                     start = 12.dp,
                                     end = 12.dp
                                 )
                         ){
                             items(characters.size){ index ->

                                 AsyncImage(
                                     model  = characters[index].thumbnailUrl,
                                     contentDescription = characters[index].name ?: "",
                                     error = painterResource(Res.drawable.no_superhero),
                                     placeholder = painterResource(Res.drawable.no_superhero),
                                     contentScale = ContentScale.Crop,
                                     modifier = Modifier.clip(CircleShape).height(80.dp).width(80.dp).aspectRatio(1f).clickable {
                                         controller.navigate("superhero_details/${characters[index].id}"){
                                             launchSingleTop = true

                                         }
                                     },
                                 )
                                 //Log.d("ImageURL", characters!![index]?.thumbnailUrl ?: "URL is null")


                             }
                         }
                     }

                    TextButton(
                        onClick = {

                        },
                        modifier = Modifier.padding(16.dp).fillMaxWidth().background(Color(0xFFf97f02)),
                    ) {
                        Text("Read Comic", style = TextStyle(color = Color.White))
                    }

                }
            }


        }
    }

}
