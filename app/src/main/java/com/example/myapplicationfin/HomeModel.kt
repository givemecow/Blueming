package com.example.myapplicationfin

// Json: JavaScript에서 데이터를 표현하는 방법, 웹에서 데이터를 주고 받을 때 주로 사용
// Gson : json을 JAVA의 객체로 역직렬화, 직렬화 해주는 자바 라이브러리
// body

data class ItemHomeModel (
    var INST_NM: String? = null,
    var TELNO_INFO: String? = null,
    var HMPG_URL: String? = null,
    var REFINE_LOTNO_ADDR: String? = null,
    var REFINE_ROADNM_ADDR: String? = null,
    var HOSPTL_DIV_NM: String? = null,
)

data class MyItems(val row:MutableList<ItemHomeModel>)
data class MyModel(val Ggmindmedinst:MutableList<MyItems>)