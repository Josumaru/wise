package com.padangmurah.wise.ui.hospital

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.padangmurah.wise.data.source.local.entity.hospital.HospitalEntity
import com.padangmurah.wise.databinding.FragmentHospitalBinding

class HospitalFragment : Fragment() {

    private var _binding: FragmentHospitalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hospitals: Array<HospitalEntity> = arrayOf(
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
            HospitalEntity(
                id = 1,
                name = "Rumah Sakit UMS",
                image = "https://e1.pxfuel.com/desktop-wallpaper/974/943/desktop-wallpaper-artstation-anime-hospital.jpg",
                status = "Nearby",
                distance = 2.5,
                address = "Nomina CC, Nimbus 72 Main Complex Suite, Boston, MA. 02111",
            ),
        )
        val hospitalAdapter = HospitalAdapter()
        hospitalAdapter.submitList(hospitals.toList())
        binding.rvHospital.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = hospitalAdapter
        }

    }
}