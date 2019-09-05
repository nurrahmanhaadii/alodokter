package id.haadii.alodoktersubmission.data.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRepo () {

    private val db = FirebaseFirestore.getInstance()

    private val auth = FirebaseAuth.getInstance()

    private fun currentUserId() = auth.currentUser?.uid.toString()

    fun registerUser(email: String, password: String, listener: (Task<AuthResult>) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                listener(it)
            }
    }

    fun loginUser(email: String, password: String, listener: (Task<AuthResult>) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                listener(it)
            }
    }

    fun getList(listener: (QuerySnapshot) -> Unit) {
        db.collection("alodokter")
            .get()
            .addOnSuccessListener {
                listener(it)
            }
    }

    fun getMore(docId: String, listener: (QuerySnapshot) -> Unit) {
        db.collection("alodokter").document(docId).collection("more")
            .get()
            .addOnSuccessListener {
                listener(it)
            }
    }

    fun uploadUser(userId: String, data: HashMap<String, Any>) {
        db.collection("users").document(userId)
            .set(data)
    }

    fun getUserData(listener: (DocumentSnapshot) -> Unit) {
        db.collection("users").document(currentUserId())
            .get()
            .addOnSuccessListener {
                listener(it)
            }
    }

    fun updateUsername(userName: String, listener: () -> Unit) {
        db.collection("users").document(currentUserId())
            .update("user_name", userName)
            .addOnSuccessListener {
                listener()
            }
    }

}