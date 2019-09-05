package id.haadii.alodoktersubmission.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import id.haadii.alodoktersubmission.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

        register.setOnClickListener {
            registerNewUser()
        }
    }

    private fun registerNewUser() {
        progressBar.visibility = View.VISIBLE

        if (TextUtils.isEmpty(email.text)) {
            email.error = "This field is not allowed to empty"
            return
        }

        if (TextUtils.isEmpty(password.text)) {
            password.error = "This field is not allowed to empty"
            return
        }

        viewModel.register(email.text.toString(), password.text.toString()) { isSuccessful, it ->
            if (isSuccessful) {
                Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE

                val uid = it.result?.user?.uid.toString()
                val mail = it.result?.user?.email.toString()

                viewModel.setUserData(uid, mail)

                viewModel.uploadUser(uid)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Registration failed, please try again later", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }
    }
}
