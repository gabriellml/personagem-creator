package br.com.alura.personagemcreator.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.personagemcreator.databinding.ActivityPersonagemFragmentBinding
import br.com.alura.personagemcreator.model.Personagem

class SelectCharatersAdapter(
    personagens: List<Personagem>,
    private val listener: OnCardClickListener
) : RecyclerView.Adapter<SelectCharatersAdapter.ViewHolder>(){

    interface OnCardClickListener {
        // Interface necessaria para permitir lógica de click nos cards
        fun onCardClick(personagem: Personagem)
    }

    private val personagens = personagens.toMutableList()

    class ViewHolder(binding: ActivityPersonagemFragmentBinding, private val listener: OnCardClickListener) : RecyclerView.ViewHolder(binding.root) {

        val selectedBox = binding.fragmentSelectedCharacter
        private val nome = binding.fragmentPersonagemNome
        private val classe = binding.fragmentPersonagemClasse
        private val lvl = binding.fragmentPersonagemLevel
        private val forca = binding.fragmentPersonagemAttStr
        private val dex = binding.fragmentPersonagemAttDex
        private val inteligencia = binding.fragmentPersonagemAttInt
        private val foto = binding.fragmentPersonagemImgClasse
        private val healthProgressBar = binding.healthProgressBar
        private val hpMax = binding.fragmentPersonagemHpmax
        private val hpAtual = binding.fragmentPersonagemHpatual
        private val def = binding.fragmentPersonagemAttDef
        private val mDef = binding.fragmentPersonagemAttMdef
        private val botaoMais = binding.fragmentPersonagemAtributoSoma
        private val botaoMenos = binding.fragmentPersonagemAtributoSubtrai
        val layoutParams = healthProgressBar.layoutParams




        fun vincula(personagem: Personagem) {

            layoutParams.height = 48 // espessura da barra de vida
            healthProgressBar.layoutParams = layoutParams

            nome.text = personagem.nome
            classe.text = personagem.classe
            lvl.text = personagem.level.toString()
            forca.text = personagem.força.toString()
            dex.text = personagem.destreza.toString()
            inteligencia.text = personagem.inteligencia.toString()
            hpMax.text = personagem.hpMax.toString()
            hpAtual.text = personagem.hpAtual.toString()
            healthProgressBar.max = personagem.hpMax.toInt()
            healthProgressBar.progress = personagem.hpAtual.toInt()
            def.text = personagem.def.toString()
            mDef.text = personagem.mDef.toString()
            val currentHealth = 30



            val drawable = ContextCompat.getDrawable(itemView.context, personagem.foto)
            foto.setImageDrawable(drawable)
            selectedBox.isSelected = personagem.isSelected

            itemView.setOnClickListener {
                // Chama o listener quando a view é clicada, passando o personagem associado
                listener.onCardClick(personagem)
                selectedBox.isChecked = personagem.isSelected
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityPersonagemFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = personagens.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personagem = personagens[position]
        holder.vincula(personagem)
    }

    fun atualiza(personagens: List<Personagem>) {
        this.personagens.clear()
        this.personagens.addAll(personagens)
        notifyDataSetChanged()
    }
}