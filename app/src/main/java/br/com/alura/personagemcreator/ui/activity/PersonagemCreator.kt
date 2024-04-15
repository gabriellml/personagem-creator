package br.com.alura.personagemcreator.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.personagemcreator.R
import br.com.alura.personagemcreator.databinding.ActivityPersonagemCreatorBinding
import br.com.alura.personagemcreator.model.Personagem
import br.com.alura.personagemcreator.model.dao.PersonagensDao

class PersonagemCreator : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var imageViewCharacter: ImageView
    private lateinit var buttonClass1: Button
    private lateinit var buttonClass2: Button
    private lateinit var buttonClass3: Button
    private var selecionaClasse: String = "Warrior"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPersonagemCreatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editTextName = binding.activityPersonagemCreatorEditTextName
        imageViewCharacter = binding.activityPersonagemCreatorImageViewCharacter
        buttonClass1 = binding.activityPersonagemCreatorButtonClass1
        buttonClass2 = binding.activityPersonagemCreatorButtonClass2
        buttonClass3 = binding.activityPersonagemCreatorButtonClass3
        imageViewCharacter.tag = R.drawable.warrior

        buttonClass1.setOnClickListener {
            // atualizar a imagem
            imageViewCharacter.setImageResource(R.drawable.warrior)
            selecionaClasse = "Warrior"
            imageViewCharacter.tag = R.drawable.warrior
        }

        buttonClass2.setOnClickListener {
            // atualizar a imagem
            imageViewCharacter.setImageResource(R.drawable.mage)
            selecionaClasse = "Mage"
            imageViewCharacter.tag = R.drawable.mage
        }

        buttonClass3.setOnClickListener {
            // atualizar a imagem
            imageViewCharacter.setImageResource(R.drawable.archer)
            selecionaClasse = "Archer"
            imageViewCharacter.tag = R.drawable.archer
        }

        val botaoSalvar = binding.activityPersonagemCreatorBotaoSalvar
        botaoSalvar.setOnClickListener {
            val campoNome = binding.activityPersonagemCreatorEditTextName
            val nome = campoNome.text.toString()
            val foto = imageViewCharacter.tag as Int
            var HP: Double = 0.0
            var DEF: Int = 0
            var MDEF: Int = 0
            when (selecionaClasse) {
                // Adicionando as características iniciais de HP para cada classe
                "Warrior" -> {
                    HP = 30.0
                    DEF = 2
                    MDEF = 0
                }

                "Archer" -> {
                    HP = 28.0
                    DEF = 1
                    MDEF = 1
                }

                "Mage" -> {
                    HP = 26.0
                    DEF = 0
                    MDEF = 2
                }
            }


            val novoPersonagem = Personagem(
                nome = nome,
                foto = foto,
                classe = selecionaClasse,
                hpMax = HP,
                hpAtual = HP,
                def = DEF,
                mDef = MDEF
            )

            val dao = PersonagensDao()
            dao.adiciona(novoPersonagem)
            finish()
        }
    }
}