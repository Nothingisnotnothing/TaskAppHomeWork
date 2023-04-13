package kg.geeks.hw.taskapp.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geeks.hw.taskapp.databinding.ItemTaskBinding
import kg.geeks.hw.taskapp.model.Car

class CarAdapter : Adapter<CarAdapter.CarViewHolder>() {

    private val data = arrayListOf<Car>()

    fun addCars(cars: List<Car>) {
        data.clear()
        data.addAll(cars)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CarViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.tvTitle.text = car.name
            binding.tvDesc.text = car.model
        }
    }
}