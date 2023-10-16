package edu.arizona.cast.kangzheng.glucose

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import edu.arizona.cast.kangzheng.glucose.databinding.FragmentGlucoseBinding
import java.util.Date

class GlucoseDetailFragment:Fragment() {
    private lateinit var glucose: Glucose
    private lateinit var binding: FragmentGlucoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glucose = Glucose()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGlucoseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addFasting.doOnTextChanged { text, _, _, _ ->
                glucose = glucose.copy(fasting = text.toString().toInt())
                if(glucose.fasting in 1..69) {
                    binding.resultFasting.text = "HYPOGLYCEMIC"
                    binding.addFasting.setTextColor(Color.RED)
                    binding.capFasting.setTextColor(Color.RED)
                    binding.resultFasting.setTextColor(Color.RED)
                    binding.capBreakfast.setTextColor(Color.RED)
                    binding.resultBreakfast.setTextColor(Color.RED)
                    binding.capLunch.setTextColor(Color.RED)
                    binding.resultLunch.setTextColor(Color.RED)
                    binding.capDinner.setTextColor(Color.RED)
                    binding.resultDinner.setTextColor(Color.RED)
                    binding.timeTitle.setTextColor(Color.RED)
                    binding.evaluate.text="ISNORMAL: FALSE"
                    binding.evaluate.setTextColor(Color.RED)
                    }
                else{
                    binding.resultFasting.text = "NORMAL"
                    binding.addFasting.setTextColor(Color.BLACK)
                    binding.capFasting.setTextColor(Color.BLACK)
                    binding.resultFasting.setTextColor(Color.BLACK)
                    binding.capBreakfast.setTextColor(Color.BLACK)
                    binding.resultBreakfast.setTextColor(Color.BLACK)
                    binding.capLunch.setTextColor(Color.BLACK)
                    binding.resultLunch.setTextColor(Color.BLACK)
                    binding.capDinner.setTextColor(Color.BLACK)
                    binding.resultDinner.setTextColor(Color.BLACK)
                    binding.timeTitle.setTextColor(Color.BLACK)
                    binding.evaluate.text="ISNORMAL: TRUE"
                    binding.evaluate.setTextColor(Color.BLACK)}
                }
            addBreakfast.doOnTextChanged { text, _, _, _ ->
                glucose = glucose.copy(breakfast = text.toString().toInt())
                if (glucose.breakfast!! > 140) {
                    binding.resultBreakfast.text = "ABNORMAL"
                    binding.addBreakfast.setTextColor(Color.RED)
                    binding.capFasting.setTextColor(Color.RED)
                    binding.resultFasting.setTextColor(Color.RED)
                    binding.capBreakfast.setTextColor(Color.RED)
                    binding.resultBreakfast.setTextColor(Color.RED)
                    binding.capLunch.setTextColor(Color.RED)
                    binding.resultLunch.setTextColor(Color.RED)
                    binding.capDinner.setTextColor(Color.RED)
                    binding.resultDinner.setTextColor(Color.RED)
                    binding.timeTitle.setTextColor(Color.RED)
                    binding.evaluate.text="ISNORMAL: FALSE"
                    binding.evaluate.setTextColor(Color.RED)
                }
                else{
                    binding.resultBreakfast.text = "NORMAL"
                    binding.evaluate.text="ISNORMAL: TRUE"
                    binding.evaluate.setTextColor(Color.BLACK)}
            }
            addLunch.doOnTextChanged { text, _, _, _ ->
                glucose = glucose.copy(lunch = text.toString().toInt())
                if(glucose.lunch!!>140){
                    binding.resultLunch.text="ABNORMAL"
                    binding.addLunch.setTextColor(Color.RED)
                    binding.capFasting.setTextColor(Color.RED)
                    binding.resultFasting.setTextColor(Color.RED)
                    binding.capBreakfast.setTextColor(Color.RED)
                    binding.resultBreakfast.setTextColor(Color.RED)
                    binding.capLunch.setTextColor(Color.RED)
                    binding.resultLunch.setTextColor(Color.RED)
                    binding.capDinner.setTextColor(Color.RED)
                    binding.resultDinner.setTextColor(Color.RED)
                    binding.timeTitle.setTextColor(Color.RED)
                    binding.evaluate.text="ISNORMAL: FALSE"
                    binding.evaluate.setTextColor(Color.RED)
                }
                else{
                    binding.resultLunch.text="NORMAL"
                    binding.evaluate.text="ISNORMAL: TRUE"
                    binding.evaluate.setTextColor(Color.BLACK)}
            }
            addDinner.doOnTextChanged { text, _, _, _ ->
                glucose = glucose.copy(dinner = text.toString().toInt())
                if(glucose.dinner!!>140){
                    binding.resultDinner.text = "ABNORMAL"
                    binding.addDinner.setTextColor(Color.RED)
                    binding.capFasting.setTextColor(Color.RED)
                    binding.resultFasting.setTextColor(Color.RED)
                    binding.capBreakfast.setTextColor(Color.RED)
                    binding.resultBreakfast.setTextColor(Color.RED)
                    binding.capLunch.setTextColor(Color.RED)
                    binding.resultLunch.setTextColor(Color.RED)
                    binding.capDinner.setTextColor(Color.RED)
                    binding.resultDinner.setTextColor(Color.RED)
                    binding.timeTitle.setTextColor(Color.RED)
                    binding.evaluate.text="ISNORMAL: FALSE"
                    binding.evaluate.setTextColor(Color.RED)
                }
                else{
                    binding.resultDinner.text = "NORMAL"
                    binding.evaluate.text="ISNORMAL: TRUE"
                    binding.evaluate.setTextColor(Color.BLACK)}
            }
            binding.timeTitle.text = glucose.date.toString()
            }
        }
    }


