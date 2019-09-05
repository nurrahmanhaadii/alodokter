package id.haadii.alodoktersubmission.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import id.haadii.alodoktersubmission.data.Items
import id.haadii.alodoktersubmission.data.repository.FirebaseRepo

class MainViewModel : ViewModel() {

    private val repository = FirebaseRepo()

    val listChangeObserver = MutableLiveData<ArrayList<Items>>()

    val userNameChangedObserver = MutableLiveData<String>()

    private var userName = ""

    private val list = ArrayList<Items>()

    fun getList() {
        repository.getList { documents ->
            for (doc in documents) {
                val docId = doc.id
                val uri = doc["uri"].toString()
                val listMore = ArrayList<String>()

                repository.getMore(docId) {
                    for (document in it) {
                        listMore.add(document["uri"].toString())
                    }
                    list.add(Items(uri, listMore))

                    listChangeObserver.postValue(list)
                }
            }
        }
    }

    fun getUserData(listener: (DocumentSnapshot) -> Unit) {
        repository.getUserData {
            if (it["user_name"] != null) {
                userName = it["user_name"].toString()
            }
            userNameChangedObserver.postValue(userName)
            listener(it)
        }
    }

    fun updateUserName(userName: String, listener: () -> Unit) {
        repository.updateUsername(userName) {
            listener()
        }
    }
}
