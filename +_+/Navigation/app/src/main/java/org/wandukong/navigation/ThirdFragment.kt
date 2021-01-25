package org.wandukong.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wandukong.navigation.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding : FragmentThirdBinding? = null
    private val binding get() = _binding!!

    val args : ThirdFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    fun moveFirstFragment(){
        val action = ThirdFragmentDirections.actionThirdFragmentToFirstFragment("From Third")
        findNavController().navigate(action)
    }

    fun moveSecondFragment(){
        val action = ThirdFragmentDirections.actionThirdFragmentToSecondFragment("From Third")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}