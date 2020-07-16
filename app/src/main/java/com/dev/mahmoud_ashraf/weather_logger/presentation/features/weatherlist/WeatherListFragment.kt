package com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dev.mahmoud_ashraf.weather_logger.R
import com.dev.mahmoud_ashraf.weather_logger.databinding.FragmentWeatherListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WeatherListFragment : Fragment() {

    private val viewModel by viewModel<WeatherListViewModel>()

    private val adapter by lazy { WeatherInfoAdapter() }

    private lateinit var binding: FragmentWeatherListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpVenuesRecycler()
        initListener()
        viewModel.weatherListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


    }

    private fun initListener() {
        binding.fab.setOnClickListener {
            viewModel.refresh()
        }
    }

    private fun setUpVenuesRecycler() {

        binding.weatherRecycler.adapter = adapter
        binding.weatherRecycler.itemAnimator = null
        adapter.onItemClicked = { _, data ->
//            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment,
//                Bundle().also {
//                    it.putParcelable(ARGS_ACTOR,actor)
//                })
        }

   
    }




}