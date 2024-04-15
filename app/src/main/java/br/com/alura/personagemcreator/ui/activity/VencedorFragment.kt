package br.com.alura.personagemcreator.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import br.com.alura.personagemcreator.databinding.FragmentVencedorBinding
import br.com.alura.personagemcreator.model.Personagem

class VencedorFragment : Fragment() {

    private var _binding: FragmentVencedorBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVencedorBinding.inflate(inflater, container, false)

        val vencedorNome = arguments?.getString(ARG_VENCEDOR_NOME)
        binding.fragmentVencedorWinner.text = vencedorNome


        val okButton: Button = binding.fragmentVencedorBotao
        okButton.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
            activity?.finish()
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_VENCEDOR_NOME = "vencedor_nome"

        fun newInstance(vencedorNome: String): VencedorFragment {
            val fragment = VencedorFragment()
            val args = Bundle()
            args.putString(ARG_VENCEDOR_NOME, vencedorNome)
            fragment.arguments = args
            return fragment
        }
    }

}



