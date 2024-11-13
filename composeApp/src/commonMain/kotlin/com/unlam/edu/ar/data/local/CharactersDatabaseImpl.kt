package com.unlam.edu.ar.data.local

import DatabaseDriverFactory
import com.unlam.edu.ar.MarvelDatabase
import com.unlam.edu.ar.MarvelDatabaseQueries
import com.unlam.edu.ar.data.models.Character

class CharactersDatabaseImpl(driverFactory: DatabaseDriverFactory) : CharactersDatabase{
    private val charactersDB = MarvelDatabase(driverFactory.createDriver())
    private val query: MarvelDatabaseQueries = charactersDB.marvelDatabaseQueries

    override suspend fun getAllCharacters(): List<Character> {
        return query.getAllMarvelCharacters(::mapToCharacter).executeAsList()
    }

    override suspend fun getCharacter(id: Long): Character {
        return query.getMarvelCharacter(id, ::mapToCharacter).executeAsOne()
    }

    override suspend fun insertCharacters(characters: List<Character>) {
        charactersDB.transaction {
            for (character in characters) {
                query.insertMarvelCharacter(
                    character.id,
                    character.name,
                    character.description,
                    character.thumbnailUrl
                )
            }
        }
    }

    override suspend fun deleteAllCharacters() {
        query.deleteAllMarvelCharacters()
    }

    override suspend fun deleteCharacter(id: Long) {
        query.deleteMarvelCharacter(id)
    }

    private fun mapToCharacter(
        id: Long,
        name: String,
        description: String,
        thumbnailUrl: String
    ): Character {
        return Character(id, name, description, thumbnailUrl)
    }
}