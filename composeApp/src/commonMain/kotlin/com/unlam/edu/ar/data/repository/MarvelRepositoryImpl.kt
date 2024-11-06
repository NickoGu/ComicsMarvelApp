package com.unlam.edu.ar.data.repository

import com.unlam.edu.ar.data.network.MarvelCharactersClient
import com.unlam.edu.ar.data.models.Comic
import com.unlam.edu.ar.data.models.Character

class MarvelRepositoryImpl(private val client: MarvelCharactersClient) :
    IMarvelRepository {

    // ACÁ CREO LAS FUNCIONES PARA USAR DESPUES EN EL COMPOSABLE

    override suspend fun getCharacters(timestamp: Long, md5: String): List<Character> {
        val response = client.getAllCharacters(timestamp, md5)

        return response.characters.list.map { characterResult ->
            Character(
                id = characterResult.id,
                name = characterResult.name,
                description = characterResult.description,
                thumbnailUrl = characterResult.thumbnail.toUrl()
            )
        }
    }

    override suspend fun getComics(timestamp: Long, md5: String): List<Comic> {
        val response = client.getAllComics(timestamp, md5)

        return response.comics.list.map { comicResult ->
            Comic(
                id = comicResult.id,
                title = comicResult.title,
                description = comicResult.description?:"no description",
                thumbnailUrl = comicResult.thumbnail.toUrl()
            )
        }
    }

    override suspend fun getCharacterById(timestamp: Long, md5: String, characterId: Int): Character {
        val response = client.getCharacterById(timestamp, md5, characterId)

        return Character(
            id = response.characters.list[0].id,
            name = response.characters.list[0].name,
            description = response.characters.list[0].description,
            thumbnailUrl = response.characters.list[0].thumbnail.toUrl()
        )
    }

    override suspend fun getComicById(timestamp: Long, md5: String, comicId: Int): Comic {
        val response = client.getComicById(timestamp, md5, comicId)

        return Comic(
            id = response.comics.list[0].id,
            title = response.comics.list[0].title,
            description = response.comics.list[0].description ?: "no description",
            thumbnailUrl = response.comics.list[0].thumbnail.toUrl()
        )
    }

    override suspend fun getCharacterFromComic(timestamp: Long, md5: String, comicId: Int): List<Character> {
        val response = client.getCharacterFromComic(timestamp, md5, comicId)

        return response.characters.list.map { characterResult ->
            Character(
                id = characterResult.id,
                name = characterResult.name,
                description = characterResult.description,
                thumbnailUrl = characterResult.thumbnail.toUrl()
            )
        }
    }

    override suspend fun getComicsFromCharacter(timestamp: Long, md5: String, characterId: Int): List<Comic> {
        val response = client.getComicsFromCharacter(timestamp, md5, characterId)

        return response.comics.list.map { comicResult ->
            Comic(
                id = comicResult.id,
                title = comicResult.title,
                description = comicResult.description ?: "no description",
                thumbnailUrl = comicResult.thumbnail.toUrl()
            )
        }
    }

}
