package com.mihailchistousov.leroymerlin.models

data class Category(
    val id: Int,
    val title: String)

data class Product(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val price: Double,
    val quantity: String)