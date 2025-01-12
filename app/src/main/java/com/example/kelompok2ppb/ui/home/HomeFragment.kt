package com.example.kelompok2ppb.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kelompok2ppb.R
import com.example.kelompok2ppb.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var recyclerView: RecyclerView
    private lateinit var bannerAdapter: BannerAdapter
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root = binding.root

        // Setup RecyclerView
        recyclerView = binding.recyclerViewBanner
        val images = listOf(
            R.drawable.gambar1,
            R.drawable.gambar2,
            R.drawable.gambar1,
            R.drawable.gambar1
        )

        bannerAdapter = BannerAdapter(images)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = bannerAdapter

        // Auto-scroll setup
        startAutoScroll()

        return root
    }

    private fun startAutoScroll() {
        val runnable = object : Runnable {
            override fun run() {
                if (bannerAdapter.itemCount == 0) return

                currentPosition++
                if (currentPosition >= bannerAdapter.itemCount) {
                    currentPosition = 0
                }

                recyclerView.smoothScrollToPosition(currentPosition)
                handler.postDelayed(this, 3000) // Scroll setiap 3 detik
            }
        }

        handler.postDelayed(runnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null) // Hentikan auto-scroll
        _binding = null
    }
}
