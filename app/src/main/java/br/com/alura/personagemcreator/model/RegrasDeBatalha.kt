package br.com.alura.personagemcreator.model

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentManager
import br.com.alura.personagemcreator.R
import br.com.alura.personagemcreator.databinding.ActivityBatalhaBinding
import br.com.alura.personagemcreator.ui.activity.ActivityBatalha
import br.com.alura.personagemcreator.ui.activity.SelectCharactersFragment
import br.com.alura.personagemcreator.ui.activity.VencedorFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import kotlin.random.Random

class RegrasDeBatalha {


    suspend fun realizarTurnos(
        personagem1: Personagem,
        personagem2: Personagem,
        binding: ActivityBatalhaBinding,
        frameLayout: FrameLayout,
        fragmentManager: FragmentManager
    ) {
        val atacanteInicial = personagem1
        var atacante = personagem1
        var defensor = personagem2


            while (atacante.hpAtual > 0 && defensor.hpAtual > 0) {
                delay(3000) // Executar turno após delay de 3 segundos
                executarTurno(atacante, defensor, binding)
                // a seguinte lógica determina a cor da barra de hp dos personagens
                if(atacante == atacanteInicial){
                    val progress = (personagem2.hpAtual.toFloat() / personagem2.hpMax.toFloat()) * 100
                    if (progress <= 50) {
                        if(progress <= 20) {
                            // mudando a barra de hp para vermelho
                            withContext(Dispatchers.Main){
                                binding.personagem2View.activityPersonagemBatalhaHealthProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                            }
                        } else {
                            // mudando a barra de hp para amarelo
                            withContext(Dispatchers.Main){
                                binding.personagem2View.activityPersonagemBatalhaHealthProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW))
                            }
                        }
                    }
                    withContext(Dispatchers.Main) {
                        binding.personagem2View.activityPersonagemBatalhaHpatual.text = personagem2.hpAtual.toString()
                        binding.personagem2View.activityPersonagemBatalhaHealthProgressBar.progress = defensor.hpAtual.roundToInt()
                    }
                } else {
                    val progress = (personagem1.hpAtual.toFloat() / personagem1.hpMax.toFloat()) * 100

                    if(progress <= 50) {
                        if(progress <= 20){
                            // mudando a barra de hp para vermelho
                            withContext(Dispatchers.Main){
                                binding.personagem1View.activityPersonagemBatalhaHealthProgressBar.setProgressTintList(ColorStateList.valueOf(Color.RED))
                            }
                        } else {
                            // mudando a barra de hp para amarelo
                            withContext(Dispatchers.Main){
                                binding.personagem1View.activityPersonagemBatalhaHealthProgressBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW))
                            }
                        }
                    }

                    withContext(Dispatchers.Main){
                        binding.personagem1View.activityPersonagemBatalhaHpatual.text = personagem1.hpAtual.toString()
                        binding.personagem1View.activityPersonagemBatalhaHealthProgressBar.progress = defensor.hpAtual.roundToInt()
                    }
                }
                // Trocar os papéis de atacante e defensor para o próximo turno
                val temp = atacante
                atacante = defensor
                defensor = temp
            }


        // Verificar quem venceu a batalha
        val vencedor = if (personagem1.hpAtual <= 0) personagem2 else personagem1
        val vencedorNome = vencedor.nome
        personagem1.hpAtual = personagem1.hpMax
        personagem2.hpAtual = personagem2.hpMax
        val fragment = VencedorFragment.newInstance(vencedor.nome)
        fragmentManager.beginTransaction()
            .replace(frameLayout.id, fragment)
            .commit()
    }


    suspend fun executarTurno(personagemAtacante: Personagem, personagemDefensor: Personagem, binding: ActivityBatalhaBinding) {
        val random = Random
        val danoRecebido : Double
        when(personagemAtacante.classe) {
            // dentro da lógica de ataque dos personagens existem logs para verificar qual foi o dano causado em cada turno
            "Warrior" -> {
                Log.i(TAG, "executarTurno: personagem atacante: ${personagemAtacante.nome}")
                Log.i(TAG, "executarTurno: personagem defensor: ${personagemDefensor.nome}")
                val randomATT = random.nextInt(1, 5)
                val randomDEF = random.nextInt(1, 4)
                danoRecebido = ((personagemAtacante.força * randomATT) - (personagemDefensor.def * randomDEF)).toDouble()
                Log.i(TAG, "executarTurno: Dano Recebido: ${danoRecebido}")
                if(danoRecebido >= 0 ) personagemDefensor.hpAtual -= danoRecebido
                Log.i(TAG, "executarTurno: hp restante do defensor: ${personagemDefensor.hpAtual}")

            }
            "Archer" -> {
                Log.i(TAG, "executarTurno: personagem atacante: ${personagemAtacante.nome}")
                Log.i(TAG, "executarTurno: personagem defensor: ${personagemDefensor.nome}")
                val randomATT = random.nextInt(1, 6)
                val randomDEF = random.nextInt(1, 4)
                danoRecebido = ((personagemAtacante.destreza * randomATT) - (personagemDefensor.def * randomDEF)).toDouble()
                Log.i(TAG, "executarTurno: Dano Recebido: ${danoRecebido}")
                if(danoRecebido >= 0 ) personagemDefensor.hpAtual -= danoRecebido
                Log.i(TAG, "executarTurno: hp restante do defensor: ${personagemDefensor.hpAtual}")
            }

            "Mage" -> {
                Log.i(TAG, "executarTurno: personagem atacante: ${personagemAtacante.nome}")
                Log.i(TAG, "executarTurno: personagem defensor: ${personagemDefensor.nome}")
                val randomATT = random.nextInt(1, 5)
                val randomDEF = random.nextInt(1, 4)
                danoRecebido = (((personagemAtacante.inteligencia * 1.5) * randomATT) - (personagemDefensor.mDef * randomDEF)).toDouble()
                Log.i(TAG, "executarTurno: Dano Recebido: ${danoRecebido}")
                if(danoRecebido >= 0 ) personagemDefensor.hpAtual -= danoRecebido
                Log.i(TAG, "executarTurno: hp restante do defensor: ${personagemDefensor.hpAtual}")
            }
        }
    }

    fun exibirVencedor(binding: ActivityBatalhaBinding, frameLayout: FrameLayout, vencedorNome: String, fragmentManager: FragmentManager) {
        // Iniciar o Fragment e exibir o vencedor no FrameLayout
        val vencedorFragment = VencedorFragment()
        val fragmentTransaction = fragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("$vencedorNome", vencedorNome)
        fragmentTransaction.replace(R.id.activity_batalha_fragment_container, vencedorFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }


}
