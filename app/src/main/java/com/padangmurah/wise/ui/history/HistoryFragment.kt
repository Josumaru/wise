package com.padangmurah.wise.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padangmurah.wise.R
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.databinding.FragmentHistoryBinding
import com.padangmurah.wise.ui.common.factory.ViewModelFactory
import com.padangmurah.wise.ui.hospital.HospitalViewModel
import com.padangmurah.wise.util.Result

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var _result: List<HistoryEntity>? = null
    private val result get() = _result!!

    private var _viewModel: HistoryViewModel? = null
    private val viewModel get() = _viewModel!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        _viewModel = ViewModelProvider(requireActivity(), factory)[HistoryViewModel::class.java]

        binding.srfHistory.setOnRefreshListener {
            viewModel.getHistory(true)
            binding.srfHistory.isRefreshing = false
        }
        binding.ivBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val historyAdapter = HistoryAdapter()

        viewModel.getHistory()

        viewModel.result.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.rvHistory.visibility = View.GONE
                        for (i in 1..10) {
                            val shimmerFrameLayout = layoutInflater.inflate(
                                R.layout.item_history_shimmer,
                                binding.llShimmerContainer,
                                false
                            )
                            binding.llShimmerContainer.addView(shimmerFrameLayout)
                        }
                    }

                    is Result.Success -> {
                        binding.rvHistory.visibility = View.VISIBLE
                        binding.llShimmerContainer.visibility = View.GONE
                        historyAdapter.submitList(result.data)
                        _result = result.data
                    }

                    is Result.Error -> {
                        binding.rvHistory.visibility = View.VISIBLE
                        binding.llShimmerContainer.visibility = View.GONE
                    }
                }
            }
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }


    }
}