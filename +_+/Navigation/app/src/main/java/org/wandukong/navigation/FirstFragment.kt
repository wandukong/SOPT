package org.wandukong.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.wandukong.navigation.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding : FragmentFirstBinding? = null
    private val binding get() = _binding!!

    val args : FirstFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    fun moveSecondFragment(){
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment("From First")
        findNavController().navigate(action)
    }

    fun moveThirdFragment(){
        val action = FirstFragmentDirections.actionFirstFragmentToThirdFragment("From First")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}