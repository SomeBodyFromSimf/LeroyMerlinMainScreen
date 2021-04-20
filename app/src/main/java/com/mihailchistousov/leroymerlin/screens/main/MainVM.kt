package com.mihailchistousov.leroymerlin.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mihailchistousov.leroymerlin.models.Category
import com.mihailchistousov.leroymerlin.models.Product

class MainVM: ViewModel() {
    fun addToRecent(product: Product) {
        _recentlyViewed.postValue(mutableListOf<Product>().apply {
            add(0,product)
            recentlyViewed.value?.let(this::addAll)
        }.distinct())
    }

    private val _catalog: MutableLiveData<List<Category>> = MutableLiveData(listOf(
        Category(1, "Сад"),
        Category(2, "Стройматериалы"),
        Category(3, "Столярные изделия"),
        Category(4, "Окна и двери"),
        Category(5, "Электротовары"),
        Category(6, "Инструменты"),
        Category(7, "Напольные полы")
    ))
    val catalog:LiveData<List<Category>> = _catalog

    private val _recentlyViewed: MutableLiveData<List<Product>> = MutableLiveData(listOf())
    val recentlyViewed: LiveData<List<Product>> = _recentlyViewed

    private val _limitedOffer: MutableLiveData<List<Product>> = MutableLiveData(listOf(
        Product(1, "Грунтовка", "https://e7.pngegg.com/pngimages/168/9/png-clipart-computer-icons-construction-worker-laborer-mason-computer-icons-construction.png", 25.70, "уп."),
        Product(2, "Краска", "https://w7.pngwing.com/pngs/816/224/png-transparent-factory-building-industry-computer-icons-laborer-building-thumbnail.png", 2500.70, "уп."),
        Product(3, "Средство какое-то)", "https://i.pinimg.com/474x/cb/9c/49/cb9c49c5968a1f7c71d52ab758018146.jpg", 1278.20, "кор."),
        Product(4, "Дверь красивая", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJTsFbCGJD32rg8hva8s-8D5QP0y9niLZz0A&usqp=CAU", 2225.0, "шт."),
        Product(5, "Дверь с дыркой для собаки", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWza0ait388J6PBQvt9dvah_8kyc9gAIaAjdBawm5rtRcfDt2GnL0YZs6QMGdh2vsv-vE&usqp=CAU", 25832.70, "шт."),
        Product(6, "Ложка", "https://i.pinimg.com/474x/6e/4a/5b/6e4a5b9d1dba231f9c2f47c2bf21ec26.jpg", 25.0, "шт."),
        Product(7, "Вилка", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqguAv7knd9pPoPrggbIs3eFs-RnYujp8yZw&usqp=CAU", 25.0, "шт.")
    ).shuffled())
    val limitedOffer: LiveData<List<Product>> = _limitedOffer

    private val _bestPrice: MutableLiveData<List<Product>> = MutableLiveData(listOf(
        Product(1, "Грунтовка", "https://e7.pngegg.com/pngimages/168/9/png-clipart-computer-icons-construction-worker-laborer-mason-computer-icons-construction.png", 25.70, "уп."),
        Product(2, "Краска", "https://w7.pngwing.com/pngs/816/224/png-transparent-factory-building-industry-computer-icons-laborer-building-thumbnail.png", 2500.70, "уп."),
        Product(3, "Средство какое-то)", "https://i.pinimg.com/474x/cb/9c/49/cb9c49c5968a1f7c71d52ab758018146.jpg", 1278.20, "кор."),
        Product(4, "Дверь красивая", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJTsFbCGJD32rg8hva8s-8D5QP0y9niLZz0A&usqp=CAU", 2225.0, "шт."),
        Product(5, "Дверь с дыркой для собаки", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWza0ait388J6PBQvt9dvah_8kyc9gAIaAjdBawm5rtRcfDt2GnL0YZs6QMGdh2vsv-vE&usqp=CAU", 25832.70, "шт."),
        Product(6, "Ложка", "https://i.pinimg.com/474x/6e/4a/5b/6e4a5b9d1dba231f9c2f47c2bf21ec26.jpg", 25.0, "шт."),
        Product(7, "Вилка", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqguAv7knd9pPoPrggbIs3eFs-RnYujp8yZw&usqp=CAU", 25.0, "шт.")
    ).shuffled())
    val bestPrice: LiveData<List<Product>> = _bestPrice

}