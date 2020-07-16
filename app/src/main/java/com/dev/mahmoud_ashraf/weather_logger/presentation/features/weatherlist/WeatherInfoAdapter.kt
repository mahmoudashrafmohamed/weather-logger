package com.dev.mahmoud_ashraf.weather_logger.presentation.features.weatherlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.mahmoud_ashraf.weather_logger.data.entities.WeatherEntity
import com.dev.mahmoud_ashraf.weather_logger.databinding.WeatherItemViewBinding

class WeatherInfoAdapter :
    ListAdapter<WeatherEntity, WeatherInfoAdapter.WeatherViewHolder>(WeatherDiffCallback()) {

    var onItemClicked: ((position: Int, weatherEntity: WeatherEntity) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
            WeatherItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val actorListItem = getItem(position)
        holder.bind(actorListItem, onItemClicked, position)
    }

    class WeatherViewHolder(
        private val binding: WeatherItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: WeatherEntity,
            onItemClicked: ((position: Int, actor: WeatherEntity) -> Unit)?,
            position: Int
        ) {

            binding.date.text = data.requestTime.toString()
            binding.nameText.text = data.weatherTemperature.toString()

            binding.itemCardView.setOnClickListener {
                onItemClicked?.invoke(position, data)
            }

            binding.executePendingBindings()


        }
    }

    class WeatherDiffCallback : DiffUtil.ItemCallback<WeatherEntity>() {
        override fun areItemsTheSame(
            oldItem: WeatherEntity,
            newItem: WeatherEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: WeatherEntity,
            newItem: WeatherEntity
        ): Boolean {
            return oldItem == newItem
        }


    }
}