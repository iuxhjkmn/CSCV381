package edu.arizona.cast.lxu.glucose

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.arizona.cast.lxu.glucose.databinding.ListItemGlucoseBinding
import java.text.DateFormat
import java.util.*

private const val TAG = "GlucoseListAdaptor"
class GlucoseHolder(
    val binding: ListItemGlucoseBinding
): RecyclerView.ViewHolder(binding.root){
    private lateinit var g:Glucose

    private val df: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)

    fun bind(g:Glucose, onGlucoseClicked: (gDate:Date)-> Unit){
        Log.d(TAG, "Bind with ${g.toString()}")
        this.g = g
        processGlucose(g)
        val avgText = (if (avg == 0) "N/A" else avg.toString())
        binding.glucoseListItemAvgGlucoseTextView.text = avgText
        binding.glucoseListItemDateTextView.text = df.format(g.date)
        if (isNormal)
            binding.glucoseListItemIsNormalCheckBox.isChecked = true
        binding.glucoseListItemIsNormalCheckBox.jumpDrawablesToCurrentState()
        binding.root.setOnClickListener{
            onGlucoseClicked(g.date)
        }
    }
    var isNormal:Boolean = false

    var avg:Int = -1

    fun processGlucose(g: Glucose){
        this.g = g
        calculateAvg()
        Log.d(TAG, "isNormal = " + isNormal.toString())
        Log.d(TAG, g.toString())
    }


    private fun isNormalMeasure(m:Int, i:Int):Boolean {
        if (m < 70) return false
        if (i==0){
            if (m>=70 && m<=90) return true
            return false
        }
        else{
            if (m>140)return false
            return true
        }

    }
    private fun calculateAvg(){
        var n:Int = 0
        var sum:Int = 0
        isNormal = true
        if (g.fasting != -1){
            sum += g.fasting
            n++

            isNormal = isNormal and isNormalMeasure(g.fasting, 0)
            Log.d(TAG, "after fasting isNormal = " + isNormal.toString())

        }
        if (g.breakfast != -1){
            sum += g.breakfast
            n++

            isNormal = isNormal and isNormalMeasure(g.breakfast, 1)
            Log.d(TAG, "after breakfast isNormal = " + isNormal.toString())
        }
        if (g.lunch != -1){
            sum += g.lunch
            n++
            isNormal = isNormal and isNormalMeasure(g.lunch, 1)
            Log.d(TAG, "after lunch isNormal = " + isNormal.toString())
        }
        if (g.dinner != -1){
            sum += g.dinner
            n++
            isNormal = isNormal and isNormalMeasure(g.dinner, 1)
            Log.d(TAG, "after dinner isNormal = " + isNormal.toString())
        }
        if (n != 0)
            avg = sum/n
        else {
            avg = 0
            isNormal = false
        }
        Log.d(TAG, "after everything isNormal = " + isNormal.toString())

    }

}
class GlucoseListAdapter(
    private val glucoses:List<Glucose>,
    private val onGlucoseClicked: (gDate: Date) -> Unit
): RecyclerView.Adapter<GlucoseHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GlucoseHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGlucoseBinding.inflate(inflater, parent, false)
        return GlucoseHolder(binding)

    }

    override fun onBindViewHolder(holder: GlucoseHolder, position: Int) {
        val g = glucoses[position]

        holder.bind(g, onGlucoseClicked)
    }

    override fun getItemCount(): Int {
        return glucoses.size
    }


}