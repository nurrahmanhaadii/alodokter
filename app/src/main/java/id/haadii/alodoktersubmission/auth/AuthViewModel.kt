package id.haadii.alodoktersubmission.auth

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import id.haadii.alodoktersubmission.data.repository.FirebaseRepo

class AuthViewModel : ViewModel() {

    private val repository = FirebaseRepo()

    private var userData = HashMap<String, Any>()

    fun register(email: String, password: String, listener: (Boolean, Task<AuthResult>) -> Unit) {
        repository.registerUser(email, password) {
            if (it.isSuccessful) {
                listener(true, it)
            } else {
                listener(false, it)
            }
        }
    }

    fun login(email: String, password: String, listener: (Boolean, Task<AuthResult>) -> Unit) {
        repository.loginUser(email, password) {
            if (it.isSuccessful) {
                listener(true, it)
            } else {
                listener(false, it)
            }
        }
    }

    fun setUserData(uid: String, email: String) {
        userData = HashMap<String, Any>()
        userData["uid"] = uid
        userData["email"] = email
        userData["user_name"] = ""
        userData["photo_profile"] = ""
    }

    fun uploadUser(userId: String) {
        repository.uploadUser(userId, userData)
    }
}