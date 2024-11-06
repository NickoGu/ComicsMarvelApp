package com.unlam.edu.ar.data.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface MarvelCharactersClient {


    suspend fun getAllCharacters(
        ts: Long,
        hash: String
    ): CharactersResponse

    suspend fun getAllComics(
        ts: Long,
        hash: String
    ): ComicsResponse

    suspend fun getCharacterById(
        ts: Long,
        hash: String,
        characterId: Int
    ): CharactersResponse

    suspend fun getComicById(
        ts: Long,
        hash: String,
        comicId: Int
    ): ComicsResponse

    suspend fun getCharacterFromComic(
        ts: Long,
        hash: String,
        comicId: Int
    ): CharactersResponse

    suspend fun getComicsFromCharacter(
        ts: Long,
        hash: String,
        characterId: Int
    ): ComicsResponse
}

@Serializable
data class ComicsResponse(
    @SerialName("data") val comics: ComicData
)

@Serializable
data class CharactersResponse(
    @SerialName("data") val characters: CharacterData
)


@Serializable
data class ComicData(
    @SerialName("results")
    val list: List<ComicResult>
)

@Serializable
data class CharacterData(
    @SerialName("results")
    val list: List<CharacterResult>
)

@Serializable
data class ComicResult(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String?,
    @SerialName("thumbnail") val thumbnail: Thumbnail
)

@Serializable
data class CharacterResult(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("thumbnail") val thumbnail: Thumbnail
)
@Serializable
data class Thumbnail(
    @SerialName("path") val path: String,
    @SerialName("extension") val extension: String
) {
    fun toUrl() : String {
        return "$path.$extension"
    }
}