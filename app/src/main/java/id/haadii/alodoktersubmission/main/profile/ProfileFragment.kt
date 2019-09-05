package id.haadii.alodoktersubmission.main.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuth
import id.haadii.alodoktersubmission.R
import id.haadii.alodoktersubmission.main.MainViewModel
import kotlinx.android.synthetic.main.profile_fragment.*
import java.util.*
import id.haadii.alodoktersubmission.auth.LoginActivity
import id.haadii.alodoktersubmission.data.User
import id.haadii.alodoktersubmission.data.objectbox.ObjectBox
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.query.Query
import id.haadii.alodoktersubmission.data.User_

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var userBox : Box<User>
    private lateinit var userQuery : Query<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(Objects.requireNonNull(activity!!)).get(MainViewModel::class.java)

        userBox = ObjectBox.boxStore.boxFor()

        userQuery = userBox.query {
            order(User_.id)
        }

        val user = userQuery.find()

        viewModel.getUserData { userData ->
            val email = userData["email"].toString()

            initObserver()

            tv_email.text = email
        }

        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        et_name.setOnEditorActionListener { _, actionId, _ ->
            var handled = false

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                pb_profile.visibility = View.VISIBLE
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                updateUsername()
                handled = true
            }

            return@setOnEditorActionListener handled
        }

        btn_signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            user.clear()
            ObjectBox.boxStore.close()
            ObjectBox.boxStore.deleteAllFiles()

            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun initObserver() {
        viewModel.userNameChangedObserver.observe(this, androidx.lifecycle.Observer {
            et_name.setText(it)
            pb_profile.visibility = View.GONE
        })
    }

    private fun updateUsername() {
        val username = et_name.text.toString()

        viewModel.updateUserName(username) {
            et_name.clearFocus()
            pb_profile.visibility = View.GONE
        }
    }

}
