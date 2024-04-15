import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.alura.personagemcreator.model.Personagem

class SelectCharactersViewModel : ViewModel() {

    private val _selectedCharacters = mutableListOf<Personagem>()
    val selectedCharacters: List<Personagem> get() = _selectedCharacters

    fun toggleCharacterSelection(personagem: Personagem) {
        if (_selectedCharacters.contains(personagem)) {
            _selectedCharacters.remove(personagem)
        } else {
            _selectedCharacters.add(personagem)
        }
    }
}
