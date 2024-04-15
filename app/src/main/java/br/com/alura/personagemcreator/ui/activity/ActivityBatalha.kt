package br.com.alura.personagemcreator.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.alura.personagemcreator.databinding.ActivityBatalhaBinding
import br.com.alura.personagemcreator.model.RegrasDeBatalha
import br.com.alura.personagemcreator.model.dao.PersonagensDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import kotlin.math.roundToInt
import kotlin.random.Random

class ActivityBatalha : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBatalhaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = PersonagensDao()
        // variáveis necessárias para as funções RegrasDeBatalha realizarem as modificações e chamadas de ui
        val frameLayout = binding.activityBatalhaFragmentContainer
        val fragmentManager = supportFragmentManager

        // Recebe os IDs dos extras vindos do fragment
        val regrasDeBatalha = RegrasDeBatalha() // Instancia da classe que detém a lógica de batalha
        val primeiroPersonagem = intent.getStringExtra("primeiroPersonagem")
        val segundoPersonagem = intent.getStringExtra("segundoPersonagem")

        // Transforma as Strings dos IDs de volta para UUID
        val primeiroPersonagemUUID = UUID.fromString(primeiroPersonagem)
        val segundoPersonagemUUID = UUID.fromString(segundoPersonagem)

        // Randomizando os dois personagens selecionados para que a ordem seja sempre aleatória
        val personagemAtacanteInicial = if (Random.nextBoolean()) primeiroPersonagemUUID else segundoPersonagemUUID
        val personagemDefensorInicial =
            if (personagemAtacanteInicial == primeiroPersonagemUUID) segundoPersonagemUUID else primeiroPersonagemUUID

        val personagem1 = dao.buscarPorId(personagemAtacanteInicial)
        val personagem2 = dao.buscarPorId(personagemDefensorInicial)

        // Distribuindo os personagens na tela
        personagem1?.let {
            binding.personagem1View.activityPersonagemBatalhaNome.text = personagem1.nome
            binding.personagem1View.activityPersonagemBatalhaHpmax.text = personagem1.hpMax.toString()
            binding.personagem1View.activityPersonagemBatalhaHpatual.text = personagem1.hpAtual.toString()
            binding.personagem1View.activityPersonagemBatalhaLevel.text = personagem1.level.toString()
            binding.personagem1View.activityPersonagemBatalhaImgClasse.setImageResource(personagem1.foto)
            binding.personagem1View.activityPersonagemBatalhaHealthProgressBar.max = personagem1.hpMax.toInt()
            binding.personagem1View.activityPersonagemBatalhaHealthProgressBar.progress = personagem1.hpAtual.roundToInt()
        }
        personagem2?.let {
            binding.personagem2View.activityPersonagemBatalhaNome.text = personagem2.nome
            binding.personagem2View.activityPersonagemBatalhaHpmax.text = personagem2.hpMax.toString()
            binding.personagem2View.activityPersonagemBatalhaHpatual.text = personagem2.hpAtual.toString()
            binding.personagem2View.activityPersonagemBatalhaLevel.text = personagem2.level.toString()
            binding.personagem2View.activityPersonagemBatalhaImgClasse.setImageResource(personagem2.foto)
            binding.personagem2View.activityPersonagemBatalhaHealthProgressBar.max = personagem2.hpMax.toInt()
            binding.personagem2View.activityPersonagemBatalhaHealthProgressBar.progress = personagem2.hpAtual.roundToInt()
        }

        // Iniciando a batalha em uma thread separada
        lifecycleScope.launch {
            withContext(Dispatchers.Main){
                personagem1?.let {
                    personagem2?.let {
                        regrasDeBatalha.realizarTurnos(personagem1, personagem2, binding, frameLayout, fragmentManager)
                    }
                }
            }
        }
    }
}