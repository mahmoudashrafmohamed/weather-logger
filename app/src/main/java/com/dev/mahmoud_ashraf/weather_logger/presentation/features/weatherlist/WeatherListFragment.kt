package com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.dev.mahmoud_ashraf.weather_logger.R
import com.dev.mahmoud_ashraf.weather_logger.databinding.FragmentWeatherListBinding
import com.dev.mahmoud_ashraf.weather_logger.presentation.core.LocationApi
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


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

        initUI()
        setUpVenuesRecycler()
        viewModel.weatherListLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })


    }

    private fun initUI() {
       binding.fab.setOnClickListener { view ->

            LocationApi(
                requireActivity(),
                callbacks = object :
                    LocationApi.Callbacks {
                    override fun onSuccess(location: Location) {
                        Timber.e("done " + location.latitude)
                        viewModel.refresh(location.latitude, location.longitude)
                        Snackbar.make(view, "Location Detected please wait...", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show()
                    }

                    override fun onFailed(locationFailedEnum: LocationApi.LocationFailedEnum) {
                        Timber.e("error " + locationFailedEnum.name)
                    }

                })

        }
    }

    private fun setUpVenuesRecycler() {

        binding.weatherRecycler.adapter = adapter
        binding.weatherRecycler.itemAnimator = null
        adapter.onItemClicked = { _, _ -> }

   
    }




}