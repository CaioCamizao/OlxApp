package com.example.hipolito.olx

import java.util.Date

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id


@Entity
class Anuncio {

    @Id
    var id: Long = 0

    var nome: String = ""
    var categoria: String = ""
    var preco: String = ""
    var imagem: Int = 0
    var data: Date? = null

    constructor(nome: String, categoria: String, imagem: Int, preco: String, data: Date) {
        this.nome = nome
        this.categoria = categoria
        this.imagem = imagem
        this.preco = preco
        this.data = data
    }

    constructor(){

    }

}
