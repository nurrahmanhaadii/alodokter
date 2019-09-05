package id.haadii.alodoktersubmission.main.home

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.haadii.alodoktersubmission.*
import kotlinx.android.synthetic.main.home_fragment.*
import java.util.*
import id.haadii.alodoktersubmission.detail.DetailActivity
import id.haadii.alodoktersubmission.main.MainViewModel

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(Objects.requireNonNull(activity!!)).get(MainViewModel::class.java)

        pb_home.visibility = View.VISIBLE

        initObserver()
    }

    private fun initObserver() {
        viewModel.listChangeObserver.observe(this, Observer { items ->
            rv_home.apply {
                layoutManager = LinearLayoutManager(activity)
                val homeAdapter = HomeAdapter(items) {
                    val intent = Intent(activity, DetailActivity::class.java)
                    intent.putExtra("detail", it.list)
                    startActivity(intent)
                }
                adapter = homeAdapter
            }
            pb_home.visibility = View.GONE
        })
    }

}
