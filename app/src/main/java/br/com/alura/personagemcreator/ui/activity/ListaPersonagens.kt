package br.com.alura.personagemcreator.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import br.com.alura.personagemcreator.databinding.ActivityListaPersonagemsBinding
import br.com.alura.personagemcreator.model.Personagem
import br.com.alura.personagemcreator.model.dao.PersonagensDao
import br.com.alura.personagemcreator.ui.adapter.ListaPersonagensAdapter
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.alura.personagemcreator.R
import br.com.alura.personagemcreator.databinding.ActivityPersonagemCreatorBinding


class ListaPersonagens : AppCompatActivity(), ListaPersonagensAdapter.OnPersonagemClickListener {
    private val dao = PersonagensDao()
    private val adapter = ListaPersonagensAdapter(context = this, personagens = dao.buscaTodos(), this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListaPersonagemsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerView = binding.activityListaPersonagemsRecyclerView
        recyclerView.adapter = adapter


        val botaoCriarPersonagem = binding.activityListaPersonagemsBotaoHeroi
        botaoCriarPersonagem.setOnClickListener{
            val intent = Intent(this, PersonagemCreator::class.java)
            startActivity(intent)
        }
        val botaoBatalha = binding.activityListaPersonagemsBotaoBatalha
        botaoBatalha.setOnClickListener {
            // instância do fragmento
            val selectCharactersFragment = SelectCharactersFragment()

            // transação de fragmento
            val transaction = supportFragmentManager.beginTransaction()

            // Substituindo o conteúdo do contêiner de fragmento pelo fragmento
            transaction.replace(R.id.activity_lista_personagems_fragment_container, selectCharactersFragment)

            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    override fun onPersonagemClick(personagem: Personagem) {
        // Para permitir a utilização dos botões que aumentam e diminuem o level dos personagens
        adapter.atualiza(dao.buscaTodos())
    }
    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos())
    }

}