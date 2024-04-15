package br.com.alura.personagemcreator.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.personagemcreator.databinding.ActivityPersonagemBinding
import br.com.alura.personagemcreator.model.Personagem


class ListaPersonagensAdapter (
    private val context: Context,
    personagens: List<Personagem>,
    private val listener: OnPersonagemClickListener
    ) : RecyclerView.Adapter<ListaPersonagensAdapter.ViewHolder>(){

    interface OnPersonagemClickListener {
        // Interface necessaria para permitir lógica de click nos cards
        fun onPersonagemClick(personagem: Personagem)

    }

    private val personagens = personagens.toMutableList()

    class ViewHolder(binding: ActivityPersonagemBinding, private val listener: OnPersonagemClickListener) : RecyclerView.ViewHolder(binding.root) {

        private val nome = binding.activityPersonagemNome
        private val classe = binding.activityPersonagemClasse
        private val lvl = binding.activityPersonagemLevel
        private val forca = binding.activityPersonagemAttStr
        private val dex = binding.activityPersonagemAttDex
        private val inteligencia = binding.activityPersonagemAttInt
        private val foto = binding.activityPersonagemImgClasse
        private val healthProgressBar = binding.healthProgressBar
        private val hpMax = binding.activityPersonagemHpmax
        private val hpAtual = binding.activityPersonagemHpatual
        private val def = binding.activityPersonagemAttDef
        private val mDef = binding.activityPersonagemAttMdef
        private val botaoMais = binding.activityPersonagemAtributoSoma
        private val botaoMenos = binding.activityPersonagemAtributoSubtrai
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

            botaoMais.setOnClickListener{
                personagem.sobelevel()
                listener.onPersonagemClick(personagem)
            }

            botaoMenos.setOnClickListener{
                personagem.descelevel()
                listener.onPersonagemClick(personagem)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityPersonagemBinding.inflate(LayoutInflater.from(context), parent, false)
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