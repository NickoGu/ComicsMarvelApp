package com.unlam.edu.ar


import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class MarvelApiClient : MarvelCharactersClient {

    private val httpClient = HttpClient {
        install(ContentNegotiation){
            json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
            level = LogLevel.ALL
        }
    }

    override suspend fun getAllCharacters(timestamp: Long, md5: String): CharactersResponse {
        val response = httpClient.get("https://gateway.marvel.com:443/v1/public/characters?nameStartsWith=Spider&limit=20") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("limit", 10)
            parameter("apikey", PUBLIC_KEY)
        }

        return response.body()
    }

    override suspend fun getAllComics(timestamp: Long, md5: String): ComicsResponse {
        val response = httpClient.get("https://gateway.marvel.com/v1/public/comics?titleStartsWith=Avengers&limit=50") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("limit", 10)
            parameter("apikey", PUBLIC_KEY)
        }

        return response.body()
    }

    override suspend fun getCharacterById(timestamp: Long, md5: String, characterId: Int): CharactersResponse {
        val response = httpClient.get("https://gateway.marvel.com/v1/public/characters/$characterId") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("apikey", PUBLIC_KEY)
        }

        return response.body()
    }

    override suspend fun getComicById(timestamp: Long, md5: String, comicId: Int): ComicsResponse {
        val response = httpClient.get("https://gateway.marvel.com/v1/public/comics/$comicId") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("apikey", PUBLIC_KEY)
        }

        return response.body()
    }

    override suspend fun getCharacterFromComic(timestamp: Long, md5: String, comicId: Int): CharactersResponse {
        val response = httpClient.get("https://gateway.marvel.com/v1/public/comics/$comicId/characters") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("apikey", PUBLIC_KEY)
        }

        return response.body()
    }

    override suspend fun getComicsFromCharacter(timestamp: Long, md5: String, characterId: Int): ComicsResponse {
        val response = httpClient.get("https://gateway.marvel.com/v1/public/characters/$characterId/comics") {
            parameter("ts", timestamp)
            parameter("hash", md5)
            parameter("apikey", PUBLIC_KEY)
        }

        return response.body()
    }


}