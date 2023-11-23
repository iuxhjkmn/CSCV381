package edu.arizona.cast.lxu.glucose

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import edu.arizona.cast.lxu.glucose.databinding.FragmentGlucoseListBinding
import kotlinx.coroutines.launch

private const val TAG = "GlucoseListFragment"

class GlucoseListFragment: Fragment(){
    private var _binding: FragmentGlucoseListBinding? = null

    private val binding
        get() = checkNotNull(_binding){
           "Cannot access binding because it is null. Is the view visible?"
        }

    private lateinit var glucoseRecyclerView: RecyclerView
    private var adapter:GlucoseListAdapter? = null

    private val glucoseListViewModel: GlucoseLab by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Here is the GlucoseListFragment being created")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlucoseListBinding.inflate(inflater,container, false)

        binding.glucoseRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                glucoseListViewModel.glucoses.collect { glucoses ->
                    binding.glucoseRecyclerView.adapter = GlucoseListAdapter(glucoses) {gDate ->
                            findNavController().navigate(
                                GlucoseListFragmentDirections.showGlucoseDetail(gDate)
                            )
                        }
                    }
                }

        }

    }

    companion object{
        fun newInstance():GlucoseListFragment{
            return GlucoseListFragment()
        }
    }

}