package kg.geeks.hw.taskapp.ui.onboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kg.geeks.hw.taskapp.databinding.ItemOnBoardingBinding
import kg.geeks.hw.taskapp.model.OnBoard
import kg.geeks.hw.taskapp.utils.loadImage

class OnBoardingAdapter(private val onClick: () -> Unit, private val onClickNext: () -> Unit) :
    Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val onBoardList = arrayListOf(
        OnBoard(
            "https://d57439wlqx3vo.cloudfront.net/iblock/f5d/f5dcf76697107ea302a1981718e33c95/1f68f84b53199df9cae4b253225eae63.png",
            "Fresh Food",
            "Something about fresh food. Fresh food is very good."
        ), OnBoard(
            "https://flow-e.com/wp-content/uploads/bfi_thumb/Google-task-list-379tmv50jkyo35v5zqpoui.png",
            "Fast Delivery",
            "Something about fast delivery. Fast delivery is very good."
        ), OnBoard(
            "https://img.officetimeline.com/website/Content/images/articles/PM-Task-Management/task-management-hero-banner.png",
            "Easy Payment",
            "Something about easy payment. Easy payment is very good."
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemOnBoardingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = onBoardList.size

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(onBoardList[position])
    }

    inner class OnBoardingViewHolder(private val binding: ItemOnBoardingBinding) :
        ViewHolder(binding.root) {

        fun bind(onBoard: OnBoard) {
            binding.apply {
                tvTitle.text = onBoard.title
                tvDesc.text = onBoard.desc
                imgBoard.loadImage(onBoard.image)

                initListeners()
                isViewVisible()
                seekbarPosition()

            }
        }

        private fun seekbarPosition() {
            binding.seekBar.progress = adapterPosition
        }

        private fun isViewVisible() {
            binding.apply {
                if (adapterPosition != 2) {
                    tvSkip.isVisible = true
                    tvNext.isVisible = true
                    seekBar.isVisible = true
                }
                btnStart.isVisible = adapterPosition == 2
            }
        }

        private fun initListeners() {
            binding.apply {
                tvSkip.setOnClickListener {
                    onClick()
                }
                btnStart.setOnClickListener {
                    onClick()
                }
                tvNext.setOnClickListener {
                    onClickNext()
                }
            }
        }
    }

}