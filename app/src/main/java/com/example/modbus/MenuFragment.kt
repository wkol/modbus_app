package com.example.modbus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.modbus.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_menu, container, false)
        binding.ReadingNavButton.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_menuFragment_to_readingFragment))
        return binding.root
    }

}