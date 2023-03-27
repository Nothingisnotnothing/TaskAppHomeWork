package kg.geeks.hw.taskapp.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import kg.geeks.hw.taskapp.data.local.Pref
import kg.geeks.hw.taskapp.databinding.FragmentOnBoardBinding
import kg.geeks.hw.taskapp.databinding.ItemOnBoardingBinding
import kg.geeks.hw.taskapp.ui.onboard.adapter.OnBoardingAdapter

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding
    private lateinit var bindingItem: ItemOnBoardingBinding
    private val adapter = OnBoardingAdapter(this::onStartClick)
    private lateinit var pref: Pref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        bindingItem = ItemOnBoardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = Pref(requireContext())
        binding.viewPager.adapter = adapter

        seekBarisVisible()
    }

    private fun seekBarisVisible() {
        binding.viewPager.registerOnPageChangeCallback(object: OnPageChangeCallback(){
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                binding.seekBar.isVisible = position != 2
                binding.seekBar.progress = position
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })
    }

    private fun onStartClick(view: View) {
        when(view.id){
            bindingItem.tvSkip.id -> findNavController().navigateUp()
            bindingItem.btnStart.id -> findNavController().navigateUp()
            bindingItem.tvNext.id -> binding.apply { viewPager.currentItem = viewPager.currentItem + 1 }
        }
        pref.saveUserSeen()
    }

}