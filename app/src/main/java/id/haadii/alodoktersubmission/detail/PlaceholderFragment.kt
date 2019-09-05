package id.haadii.alodoktersubmission.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import id.haadii.alodoktersubmission.R
import kotlinx.android.synthetic.main.item_corousel.view.*

class PlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_corousel, container, false)

        val uriList = arguments!!.getStringArrayList(URI_LIST)

        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 1){
            Glide.with(context!!.applicationContext)
                .load(uriList?.get(0))
                .into(rootView.image_iv)
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 2){
            Glide.with(context!!.applicationContext)
                .load(uriList?.get(1))
                .into(rootView.image_iv)
        }
        if (arguments!!.getInt(ARG_SECTION_NUMBER) == 3){
            Glide.with(context!!.applicationContext)
                .load(uriList?.get(2))
                .into(rootView.image_iv)
        }
        return rootView
    }

    companion object {

        const val ARG_SECTION_NUMBER = "section_number"
        const val URI_LIST = "uri_list"

        fun newInstance(sectionNumber: Int, uriList: ArrayList<String>): PlaceholderFragment {
            val fragment = PlaceholderFragment()
            val args = Bundle()
            args.putInt(ARG_SECTION_NUMBER, sectionNumber)
            args.putStringArrayList(URI_LIST, uriList)
            fragment.arguments = args
            return fragment
        }
    }
}