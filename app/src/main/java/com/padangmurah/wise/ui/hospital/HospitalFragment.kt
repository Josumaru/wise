package com.padangmurah.wise.ui.hospital

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.padangmurah.wise.R

class HospitalFragment : Fragment() {

    companion object {
        fun newInstance() = HospitalFragment()
    }

    private val viewModel: HospitalViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_hospital, container, false)
    }
}