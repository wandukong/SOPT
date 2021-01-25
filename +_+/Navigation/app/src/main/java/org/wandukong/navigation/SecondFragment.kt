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
import org.wandukong.navigation.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    private var _binding : FragmentSecondBinding? = null
    private val binding get() = _binding!!

    val args: SecondFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_second, container, false)
        binding.fragment = this
        return binding.root
    }

    fun moveFirstFragment(){
        val action = SecondFragmentDirections.actionSecondFragmentToFirstFragment("From Second")
        findNavController().navigate(action)
    }

    fun moveThirdFragment(){
        val action = SecondFragmentDirections.actionSecondFragmentToThirdFragment("From Second")
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}