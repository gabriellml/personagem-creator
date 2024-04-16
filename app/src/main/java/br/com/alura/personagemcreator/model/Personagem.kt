package br.com.alura.personagemcreator.model

import java.util.UUID

class Personagem(
    val Id: UUID = UUID.randomUUID(),
    val nome: String,
    var foto: Int,
    val classe: String,
    var level: Int = 1,
    var força: Int = 1,
    var destreza: Int = 1,
    var inteligencia: Int = 1,
    var hpMax: Double,
    var hpAtual: Double,
    var def: Int,
    var mDef: Int
) {
    var isSelected: Boolean = false // essa variável serve para idenficiar o estado de seleção dos personagens no fragment de seleção
        internal set

    fun sobelevel(){
        level++
        when(classe) {
                "Warrior" -> {
                    hpMax += 20
                    hpAtual += 10
                    if (level % 2 == 0) {
                        força += 2
                    } else força++
                    if (level % 3 == 0){
                        def +=2
                        mDef++
                    } else def++
                }
                "Archer" -> {
                    hpMax += 16
                    hpAtual += 8
                    if (level % 2 == 0) {
                        destreza += 2
                    } else destreza++
                    if (level % 3 == 0){
                        def += 2
                        mDef += 2
                    }
                }

                "Mage" -> {
                    hpMax += 13
                    hpAtual += 6.5
                    if (level % 2 == 0) {
                        inteligencia += 2
                    } else inteligencia++
                    if (level % 3 == 0){
                        mDef += 2
                        def ++
                    } else mDef ++
                }
            }
        }

    fun descelevel() {
        if (level > 1) {
            level--
            when (classe) {
                "Warrior" -> {
                    hpMax -= 10
                    hpAtual -= 10
                    if (level % 2 != 0) {
                        força -= 2
                    } else força--
                    if((level+1) % 3 == 0){
                        def -=2
                        mDef --
                    } else def--
                }
                "Archer" -> {
                    hpMax -= 8
                    hpAtual -= 8
                    if (level % 2 != 0) {
                        destreza -= 2
                    } else destreza--
                    if((level+1) % 3 == 0){
                        def -=2
                        mDef -=2
                    }
                }

                "Mage" -> {
                    hpMax -= 6.5
                    hpAtual -= 6.5
                    if (level % 2 != 0) {
                        inteligencia -= 2
                    } else inteligencia--
                    if((level+1) % 3 == 0){
                    mDef -= 2
                    def --
                    } else mDef --
                }
            }
        }
    }
}

