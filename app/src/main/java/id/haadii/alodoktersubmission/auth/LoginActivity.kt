package id.haadii.alodoktersubmission.auth

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import id.haadii.alodoktersubmission.*
import id.haadii.alodoktersubmission.data.User
import id.haadii.alodoktersubmission.data.User_
import id.haadii.alodoktersubmission.data.objectbox.ObjectBox
import id.haadii.alodoktersubmission.main.MainActivity
import io.objectbox.Box
import io.objectbox.kotlin.boxFor
import io.objectbox.kotlin.query
import io.objectbox.query.Query
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var userBox : Box<User>
    private lateinit var userQuery : Query<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        login.setOnClickListener {
            loginUserAccount()
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        ObjectBox.init(this)

        userBox = ObjectBox.boxStore.boxFor()

        userQuery = userBox.query {
            order(User_.id)
        }

        val user = userQuery.find()

        if (user.isNotEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun loginUserAccount() {
        progressBar.visibility = View.VISIBLE

        if (TextUtils.isEmpty(email.text)) {
            email.error = "This field not allowed to empty"
            return
        }

        if (TextUtils.isEmpty(password.text)) {
            password.error = "This field not allowed to empty"
            return
        }

        viewModel.login(email.text.toString(), password.text.toString()) { isSuccessful, it ->
            if (isSuccessful) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE

                val uid = it.result?.user?.uid.toString()
                val mail = it.result?.user?.email.toString()

                viewModel.setUserData(uid, mail)

                viewModel.uploadUser(uid)

                userBox.put(
                    User(
                        0,
                        uid,
                        email.text.toString()
                    )
                )

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login failed, please try again later!", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        ObjectBox.boxStore.close()
    }
}