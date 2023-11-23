package edu.arizona.cast.lxu.glucose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "GlucoseLab"

@RequiresApi(Build.VERSION_CODES.O)
class GlucoseLab: ViewModel(){
    private val glucoseRepository = GlucoseRepository.get()

    private val _glucoses: MutableStateFlow<List<Glucose>> = MutableStateFlow(emptyList())
    val glucoses: StateFlow<List<Glucose>>
        get() = _glucoses.asStateFlow()

    init{

        viewModelScope.launch{
            glucoseRepository.getGlucoses().collect{
                _glucoses.value = it
            }
        }
    }
}