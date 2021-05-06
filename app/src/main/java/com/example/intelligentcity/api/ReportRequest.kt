package com.example.intelligentcity.api

data class ReportRequest (
        val id: Int,
        val utilizador_id: Int,
        val titulo: String,
        val descricao: String,
        val data: String,
        val localizacao: String,
        val fotografia: String,
        val latitude: String,
        val longitude: String
)