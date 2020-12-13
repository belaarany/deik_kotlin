package deik.belaarany.bitly.screens.created

import android.util.Log
import androidx.lifecycle.ViewModel

class CreatedViewModel(longUrl: String) : ViewModel() {

    // The final score
    var lu = longUrl

    init {
        Log.i("CreatedViewModel", "Long URL score is $lu")
    }
}
