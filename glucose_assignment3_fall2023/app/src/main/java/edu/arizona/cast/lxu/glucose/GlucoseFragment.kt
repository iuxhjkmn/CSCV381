package edu.arizona.cast.lxu.glucose
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*

import android.widget.EditText
import androidx.annotation.RequiresApi

import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import java.text.DateFormat
import java.util.*

import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.launch
import edu.arizona.cast.lxu.glucose.databinding.FragmentGlucoseBinding

private const val TAG = "GlucoseFragment"
const val EXTRA_GLUCOSE_DATE = "glucoseintent.GLUCOSE_DATE"

class GlucoseFragment: Fragment(){

    private var _binding: FragmentGlucoseBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null.  Is the view visible?"
        }
    private val args: GlucoseFragmentArgs by navArgs()
    private val glucoseViewModel: GlucoseViewModel by viewModels {
        Log.d(TAG, "The date passed is ${args.gDate.toString()}")
        GlucoseViewModelFactory(args.gDate)

    }

    private val textBoxNormalColor = Color.WHITE
    private val msgBboxNormalColor = Color.BLACK


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding =
            FragmentGlucoseBinding.inflate(inflater, container, false)

        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            fastingBox.doOnTextChanged{
                text, _, _, _ ->
                try {
                    glucoseViewModel.updateFasting(Integer.valueOf(text.toString()))
                    refreshFields()
                } catch (e: Exception) {
                    glucoseViewModel.updateFasting(-1)
                    refreshFields()
                }

            }
            breakfastBox.doOnTextChanged{
                    text, _, _, _ ->
                try {
                    glucoseViewModel.updateBreakfast(Integer.valueOf(text.toString()))
                    refreshFields()
                } catch (e: Exception) {
                    glucoseViewModel.updateBreakfast(-1)
                    refreshFields()
                }

            }
            lunchBox.doOnTextChanged{
                    text, _, _, _ ->
                try {
                    glucoseViewModel.updateLunch(Integer.valueOf(text.toString()))
                    refreshFields()
                } catch (e: Exception) {
                    glucoseViewModel.updateLunch(-1)
                    refreshFields()
                }

            }
            dinnerBox.doOnTextChanged{
                    text, _, _, _ ->
                try {
                    glucoseViewModel.updateDinner(Integer.valueOf(text.toString()))
                    refreshFields()
                } catch (e: Exception) {
                    glucoseViewModel.updateDinner(-1)
                    refreshFields()
                }

            }
            clearButton.setOnClickListener {
                clearFields()
            }
            historyButton.setOnClickListener {
                it.findNavController().popBackStack()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                glucoseViewModel.glucose.collect { glucose ->
                    glucose?.let { updateUi(it) }
                }
            }
        }
        setFragmentResultListener(DatePickerFragment.REQUEST_KEY_DATE) {
                requestKey, bundle ->
                val newDate = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE, Date::class.java)
                if (newDate != null)
                    glucoseViewModel.updateGlucose { it.copy(date = newDate) }
        }
    }
    private fun updateUi(glucose: Glucose) {
        Log.d(TAG, "updateUi has ${glucose.toString()}")
        binding.apply {
            fillInWidgets(glucose)
            displayMsgText()
            refreshFields()
            glucoseDate.setOnClickListener {
                findNavController().navigate(
                    GlucoseFragmentDirections.selectDate(glucose.date)
                )
            }
        }
    }


    override fun onPause() {
        super.onPause()


    }

    override fun onStop() {
        super.onStop()
        glucoseViewModel.save()

    }


    private fun updateDate(g:Glucose){
        val df:DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
        binding.glucoseDate.text = df.format(g.date)
    }
    private fun refreshFields() {
        glucoseViewModel.processGlucose()
        Log.d(TAG, "Glucose data is " + glucoseViewModel.glucose.value!!.toString())
        setColorfulBoxes()
        displayMsgText()

    }
    private fun setColorfulBoxes(){
        var i =0
        while (i<glucoseViewModel.isNormals.size){
            setColorful(i, glucoseViewModel.isNormals[i])
            i++
        }

    }
    private fun updateTextBoxes(g: Glucose){
        setColorfulBoxes()
        g.fasting.let { updateTextBox(it, binding.fastingBox) }
        g.breakfast.let { updateTextBox(it, binding.breakfastBox) }
        g.lunch.let { updateTextBox(it, binding.lunchBox) }
        g.dinner.let { updateTextBox(it, binding.dinnerBox) }
    }
    private fun updateTextBox(n:Int, box:EditText){
        if (n==-1) box.setText("")
        else box.setText(n.toString())

    }
    private fun setColorful(i:Int, b:Boolean){

        var c  = Color.RED
        if (b) c = textBoxNormalColor
        when(i){
            0->binding.fastingBox.setTextColor(c)
            1->binding.breakfastBox.setTextColor(c)
            2->binding.lunchBox.setTextColor(c)
            3->binding.dinnerBox.setTextColor(c)
        }

    }
    private fun clearFields() {
        binding.apply {
            fastingBox.setText("")
            fastingBox.setHint(R.string.input_hint)
            breakfastBox.setText("")
            breakfastBox.setHint(R.string.input_hint)
            lunchBox.setText("")
            lunchBox.setHint(R.string.input_hint)
            dinnerBox.setText("")
            dinnerBox.setHint(R.string.input_hint)
            msgBox.setText("")
        }

    }
    private fun fillInWidgets(g: Glucose) {
        updateDate(g)
        setColorfulBoxes()
        if (g.fasting != -1) binding.fastingBox.setText(g.fasting.toString())

        if (g.breakfast != -1) binding.breakfastBox.setText(g.breakfast.toString())
        if (g.lunch != -1) binding.lunchBox.setText(g.lunch.toString())
        if (g.dinner != -1) binding.dinnerBox.setText(g.dinner.toString())
        displayMsgText()
    }

    private fun displayMsgText() {
        if (!glucoseViewModel.isNormal) {
            binding.msgBox.setTextColor(Color.RED)
        }
        else
            binding.msgBox.setTextColor(msgBboxNormalColor)
        binding.msgBox.setText(glucoseViewModel.msg)

    }



    companion object{
        fun newInstance(glucoseDate: Date):GlucoseFragment{
            Log.d(TAG, "newInstance with date = " + glucoseDate.toString())
            val args:Bundle = Bundle()
            args.putSerializable(EXTRA_GLUCOSE_DATE, glucoseDate)

            var fragment: GlucoseFragment = GlucoseFragment()
            fragment.arguments = args
            Log.d(TAG, "newInstance with bundle = " + fragment.arguments.toString())
            return fragment
        }
        fun newInstance():GlucoseFragment{
            var fragment: GlucoseFragment = GlucoseFragment()
            return fragment
        }
    }

}
