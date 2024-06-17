package vn.poly.mob305.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatViewModel : ViewModel() {
    // dinh nghĩa List Cat

    private val _cats = MutableLiveData<List<Cat>>()
    val cats: LiveData<List<Cat>> = _cats

    // định nghĩa các fun gọi chức năng như : thêm, sửa, xóa, get danh sách

    fun addCat(cat: Cat) {
        val currentList = _cats.value ?: emptyList()
        _cats.value = currentList + cat
    }

    fun removeCat(cat: Cat) {
        val currentList = _cats.value ?: emptyList()
        _cats.value = currentList.filter { it._id != cat._id }
    }

    fun getAllCat() {
        val apiS =
            RetrofitBase.getClient().create(ApiService::class.java)
                .getListCats("", 0, 100)
        apiS.enqueue(object : Callback<List<Cat>> {
            override fun onResponse(
                p0: Call<List<Cat>>,
                p1: Response<List<Cat>>
            ) {
                _cats.value = emptyList() // xoa du lieu cu di
                _cats.value = p1.body()
            }

            override fun onFailure(
                p0: Call<List<Cat>>,
                p1: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }

}