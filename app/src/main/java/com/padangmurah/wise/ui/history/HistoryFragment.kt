package com.padangmurah.wise.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.padangmurah.wise.data.source.local.entity.history.HistoryEntity
import com.padangmurah.wise.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

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
        val histories: Array<HistoryEntity> = arrayOf(
            HistoryEntity(
                id = 1,
                date = "Friday, 17 August 2024",
                image = "",
                title = "Skin Scratch",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 2,
                date = "Monday, 19 August 2024",
                image = "",
                title = "Hair Damage",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 3,
                date = "Wednesday, 21 August 2024",
                image = "",
                title = "Skin Lesions",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 3,
                date = "Wednesday, 21 August 2024",
                image = "",
                title = "Skin Lesions",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 3,
                date = "Wednesday, 21 August 2024",
                image = "",
                title = "Skin Lesions",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 3,
                date = "Wednesday, 21 August 2024",
                image = "",
                title = "Skin Lesions",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 3,
                date = "Wednesday, 21 August 2024",
                image = "",
                title = "Skin Lesions",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
            HistoryEntity(
                id = 3,
                date = "Wednesday, 21 August 2024",
                image = "",
                title = "Skin Lesions",
                injury = "Minor Injuries",
                hospital = "RSUD Kali Gede"
            ),
        )
        val historyAdapter = HistoryAdapter()
        historyAdapter.submitList(histories.toList())
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }

    }
}