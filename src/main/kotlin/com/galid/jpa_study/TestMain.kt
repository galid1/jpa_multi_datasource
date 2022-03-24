package com.galid.jpa_study

class TestMain {
}

data class A(
    val key: String,
    val data: String
)

class T(
    val li: List<String>
) {
    init {
        verify()
    }

    fun verify() {
        if(li.size < 2) {
            throw RuntimeException("작아 !")
        }

    }
}

fun main() {
    val a = setOf(
        1, 2
    )

    println(a.containsAll(listOf(1,2,3)))

//    val li = mutableListOf<A>()
//    val willFindData = listOf("1", "989", "1000", "500", "233", "123", "1", "989", "1000", "500", "233", "123","1", "989", "1000", "500", "233", "123")
//
//
//    for(i in 0 .. 10000) {
//        li.add(A(i.toString(), "A"))
//    }
//
//    val startTime1 = System.currentTimeMillis()
//    willFindData.forEach {
//        val find = li.find { repo -> it == repo.key }
//    }
//    println("result time : ${System.currentTimeMillis() - startTime1}")
//
//
//    val startTime2 = System.currentTimeMillis()
//    val liMap = li.associate { it.key to it.data }
//    willFindData.forEach {
//        val find = liMap[it]
//    }
//    println("result time : ${System.currentTimeMillis() - startTime2}")

}