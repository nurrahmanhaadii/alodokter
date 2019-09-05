package id.haadii.alodoktersubmission.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import id.haadii.alodoktersubmission.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val list = intent.getStringArrayListExtra("detail")

        val pagerAdapter = SectionPagerAdapter(supportFragmentManager, list)
        container.adapter = pagerAdapter

        title = "Detail"
    }
}
