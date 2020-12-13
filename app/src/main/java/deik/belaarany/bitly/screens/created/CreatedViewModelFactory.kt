package deik.belaarany.bitly.screens.created

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CreatedViewModelFactory (private val longUrl: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatedViewModel::class.java)) {
            return CreatedViewModel(longUrl) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}