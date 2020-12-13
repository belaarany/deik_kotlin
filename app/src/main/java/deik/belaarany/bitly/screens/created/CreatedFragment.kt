package deik.belaarany.bitly.screens.created

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.ViewModelProvider
import deik.belaarany.bitly.R
import deik.belaarany.bitly.databinding.FragmentCreatedBinding

class CreatedFragment : Fragment() {

    private lateinit var viewModel: CreatedViewModel
    private lateinit var viewModelFactory: CreatedViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: FragmentCreatedBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_created,
            container,
            false
        )

        viewModelFactory = CreatedViewModelFactory(CreatedFragmentArgs.fromBundle(requireArguments()).longUrl)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(CreatedViewModel::class.java)

        binding.longUrl.text = viewModel.lu.toString()



        return binding.root
    }
}