package vn.poly.mob305.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    // LazyColum là 1 compose hiển thị danh sách từ mảng - List - Array ...

    data class Cat(val id: Int, val name: String, val des: String, val url: String)

    // địa chỉ mà nếu truy cập thì sẽ trả về thông tin là JSON
    // https://cataas.com/api/cats?tags=cute&skip=0&limit=10.
    // BASE_URL : https://cataas.com
    // Path : api/cats
    // ? tags=cute&skip=0&limit=10 : params , tags , skip , limit
    // tags=cute : query
    // B1 : khởi tạo retrofit : nạp BaseURL và thư viện chuyển đổi JSON sang  Object
    // B2 : khởi tạo Object - Model : dữ liệu được chuyển từ JSON Object
    // B3 : khởi tạo Request bằng interface
    // B4 : Sử dụng

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // tạo 1 array các loài mèo
        var catList = listOf(
            Cat(
                1, "MEOMEO",
                "Meo rat quy hiem",
                "https://cdn.britannica.com/70/234870-050-D4D024BB/Orange-colored-cat-yawns-displaying-teeth.jpg"
            ),
            Cat(
                2, "MeoBeo",
                "Meo rat quy hiem, nhung hoi luoi an",
                "https://cdn.britannica.com/34/235834-050-C5843610/two-different-breeds-of-cats-side-by-side-outdoors-in-the-garden.jpg"
            ), Cat(
                3, "MEOMEO",
                "Meo rat quy hiem, nhung rat hay an",
                "https://www.animalfriends.co.uk/siteassets/media/images/article-images/cat-articles/51_afi_article1_the-secret-language-of-cats.png"
            ), Cat(
                4, "<MEOMEO>123",
                "Meo rat quy hiem",
                "https://cdn.britannica.com/25/172925-050-DC7E2298/black-cat-back.jpg"
            )
        )

        val catVM = ViewModelProvider(this).get(CatViewModel::class.java)

        setContent {
            var catSelected = remember {
                mutableStateOf<vn.poly.mob305.myapplication.Cat?>(null)
            }

            var listStateCatS = remember {
                mutableStateListOf<vn.poly.mob305.myapplication.Cat>()
            }

//            val cats by catVM.cats.observeAsState(initial = emptyList())
//            LazyColumn {
//                items(cats) { cat ->
//                    {
//
//                    }
//                }
//            }

            Column {

                Button(onClick = {
                    listStateCatS.add(Cat("23dsfsdf", "fsdfdsf", 333, listOf("444", "okok")))
                }) {
                    Text(text = "ADD")
                }

                Button(onClick = {
                    val apiS =
                        RetrofitBase.getClient().create(ApiService::class.java)
                            .getListCats("", 0, 100)
                    apiS.enqueue(object : Callback<List<vn.poly.mob305.myapplication.Cat>> {
                        override fun onResponse(
                            p0: Call<List<vn.poly.mob305.myapplication.Cat>>,
                            p1: Response<List<vn.poly.mob305.myapplication.Cat>>
                        ) {
                            listStateCatS.clear() // xoa du lieu cu di
                            p1.body()?.let {
                                listStateCatS.addAll(it)
                            }
                        }

                        override fun onFailure(
                            p0: Call<List<vn.poly.mob305.myapplication.Cat>>,
                            p1: Throwable
                        ) {
                            TODO("Not yet implemented")
                        }
                    })


                }) {
                    Text(text = "Load Data")
                }

                LazyColumn {
                    itemsIndexed(listStateCatS) { i, it ->
                        Column {
                            Button(onClick = {
                                catSelected.value = it
                            }) {
                                Text(text = "Detail")
                            }
                            Button(onClick = {
                                listStateCatS.remove(it)
                            }) {
                                Text(text = "Delete")
                            }
                            Button(onClick = {
                                var cat = it.copy("5555", "kkkk", 3333)
                                listStateCatS[i] = cat
                            }) {
                                Text(text = "Update")
                            }
                            Text(text = "ID : ${it._id}")
                            Text(text = "Name : ${it.mimetype}")
                            Text(text = "Des : ${it.size}")
                            AsyncImage(
                                modifier = Modifier.size(90.dp, 90.dp),
                                model = "https://cataas.com/cat/${it._id}",
                                contentDescription = "ABC"
                            )
                            Text(text = "-------------------------------------")
                        }
                    }
                }
            }
            // cu phap lambda de goi gia tri cua mutableState
            catSelected.value?.let {
                Dialog(onDismissRequest = { catSelected.value = null }) {
                    Column {
                        Text(text = "${catSelected.value?.size}")
                        Text(text = "${catSelected.value?.mimetype}")
                        AsyncImage(
                            modifier = Modifier.size(90.dp, 90.dp),
                            model = "${it.mimetype}",
                            contentDescription = "ABC"
                        )
                    }
                }
//                AlertDialog(
//                    onDismissRequest = {
//                        catSelected.value = null
//                    },
//                    confirmButton = {
//                        Button(
//                            onClick = {
//                                catSelected.value = null
//                            }) {
//                            Text(text = "OK")
//                        }
//                    },
//                    title = { Text(text = "${catSelected.value?.name}") },
//                    text = { Text(text = "${catSelected.value?.des}") },
//                )
            }
        }
    }
}
