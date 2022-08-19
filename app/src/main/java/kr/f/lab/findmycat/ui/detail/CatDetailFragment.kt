package kr.f.lab.findmycat.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import kr.f.lab.findmycat.databinding.FragmentCatDetailBinding

class CatDetailFragment : Fragment() {

    private val viewModel: CatDetailViewModel by viewModels()
    val safeArgs: CatDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCatDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.apply {
            getCat(safeArgs.query ?: "")
        }

        return binding.root
    }
}
