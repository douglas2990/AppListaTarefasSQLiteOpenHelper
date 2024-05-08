package com.douglas2990.aulas.applistatarefas.model

import java.io.Serializable

data class Tarefa(
    val idTarefa: Int,
    val descricao: String,
    val dataCadastro: String
) : Serializable
