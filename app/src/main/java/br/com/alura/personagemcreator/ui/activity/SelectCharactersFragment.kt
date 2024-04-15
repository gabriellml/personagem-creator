package br.com.alura.personagemcreator.ui.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import br.com.alura.personagemcreator.databinding.FragmentSelectCharactersBinding
import br.com.alura.personagemcreator.model.Personagem
import br.com.alura.personagemcreator.model.dao.PersonagensDao
import br.com.alura.personagemcreator.ui.adapter.SelectCharatersAdapter
import kotlin.math.log


class SelectCharactersFragment : Fragment(), SelectCharatersAdapter.OnCardClickListener {
    // métodos, propriedades, adapter, dao
    private var contadorPersoangens: Int = 0    // variável necessária para limitar o número de personagens selecionados
    private var primeiroPersonagem: Personagem? = null
    private var segundoPersonagem: Personagem? = null
    private var _binding: FragmentSelectCharactersBinding? = null
    private val binding get() = _binding!!
    private val dao = PersonagensDao()
    private val adapter = SelectCharatersAdapter(personagens = dao.buscaTodos(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflando o layout do fragment
        _binding = FragmentSelectCharactersBinding.inflate(inflater, container, false)

        // Configura RecyclerView e Adapter para exibir a lista de personagens
        val recyclerView = binding.fragmentSelectCharactersRecyclerview
        recyclerView.adapter = adapter

        // Adicionando listener de clique ao Adapter
        val startButton: Button = binding.fragmentSelectCharactersBotaoBatalha
        startButton.setOnClickListener {
            if(contadorPersoangens == 2) {iniciarBatalha()}
        }
        return binding.root
    }

    fun iniciarBatalha() {
        val intent = Intent(requireContext(), ActivityBatalha::class.java)
        intent.putExtra("primeiroPersonagem", primeiroPersonagem?.Id.toString())
        intent.putExtra("segundoPersonagem", segundoPersonagem?.Id.toString())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //mutando o estado de seleção dos personagens caso a activity seja finalizada
        if(primeiroPersonagem?.isSelected == true) primeiroPersonagem!!.isSelected = false
        if(segundoPersonagem?.isSelected == true) segundoPersonagem!!.isSelected = false

        _binding = null
    }

    override fun onCardClick(personagem: Personagem) {
        // Lógica de seleção de personagens para determinar quantidade máxima e mínima de personagens selecionados
        if (personagem.isSelected) {
            personagem.isSelected = !personagem.isSelected
            if (primeiroPersonagem == personagem) {
                primeiroPersonagem = null
            } else if (segundoPersonagem == personagem) {
                segundoPersonagem = null
            }
            contadorPersoangens--
        } else if (contadorPersoangens < 2) {
            if (primeiroPersonagem == null) {
                personagem.isSelected = !personagem.isSelected
                primeiroPersonagem = personagem
                contadorPersoangens++
            } else if (segundoPersonagem == null) {
                personagem.isSelected = !personagem.isSelected
                segundoPersonagem = personagem
                contadorPersoangens++
            }
        } else {
            println("Número Máximo de personagens selecionados")
        }

    }
}