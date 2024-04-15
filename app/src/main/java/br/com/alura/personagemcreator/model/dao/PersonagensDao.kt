package br.com.alura.personagemcreator.model.dao

import br.com.alura.personagemcreator.model.Personagem
import java.util.UUID

class PersonagensDao {

    fun adiciona(personagem: Personagem){
        personagens.add(personagem)
    }

    fun buscaTodos() : List<Personagem>{
        return personagens.toList()
    }

    fun buscarPorId(Id : UUID): Personagem? {
        // Procurar o personagem na lista pelo seu ID
        return personagens.find { it.Id == Id }
    }

    companion object {
        private val personagens = mutableListOf<Personagem>()
    }
}