package id.haadii.alodoktersubmission.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SectionPagerAdapter(fragment: FragmentManager, private var list: ArrayList<String>): FragmentStatePagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1, list)
    }

    override fun getCount(): Int {
        return list.size
    }

}