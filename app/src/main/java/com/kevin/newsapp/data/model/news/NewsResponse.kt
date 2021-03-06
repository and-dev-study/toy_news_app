package com.kevin.newsapp.data.model.news

data class NewsResponse (
        val status : String?,
        val totalResults : Int?,
        val articles : List<NewsArticle>?
)