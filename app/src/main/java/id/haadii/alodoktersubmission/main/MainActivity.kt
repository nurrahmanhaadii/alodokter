package id.haadii.alodoktersubmission.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.haadii.alodoktersubmission.R
import id.haadii.alodoktersubmission.data.User
import id.haadii.alodoktersubmission.data.User_
import id.haadii.alodoktersubmission.data.objectbox.ObjectBox
import id.haadii.alodoktersubmission.main.home.HomeFragment
import id.haadii.alodoktersubmission.main.profile.ProfileFragment
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.query.Query

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var userBox : Box<User>
    private lateinit var userQuery : Query<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.getList()

        setupBottomNavigation()

        userBox = ObjectBox.boxStore.boxFor()

        userQuery = userBox.query {
            order(User_.id)
        }

        val fragment = HomeFragment.newInstance()
        openFragment(fragment)

        val user = userQuery.find()

    }

    private fun setupBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener())
    }

    private fun navigationItemSelectedListener() : BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {

            it.itemId
            when (it.itemId) {
                R.id.action_home -> {
                    val fragment = HomeFragment.newInstance()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.action_profile -> {
                    val fragment = ProfileFragment.newInstance()
                    openFragment(fragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, fragment)
//        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        ObjectBox.boxStore.close()
    }
}
