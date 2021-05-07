package com.example.intelligentcity.api

data class ReportRequest (
        val id: String,
        val utilizador_id: String,
        val titulo: String,
        val descricao: String,
        val data: String,
        val localizacao: String,
        val fotografia: String,
        val latitude: String,
        val longitude: String
)