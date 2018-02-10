package com.example.hipolito.olx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_add_anuncios.*
import java.util.*

class AddAnunciosActivity : AppCompatActivity() {

    private var anunciosBox: Box<Anuncio>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_anuncios)
        setupViews()
    }

    private fun setupViews() {

        anunciosBox = (application as App).boxStore!!.boxFor(Anuncio::class.java)

        btnConfirmar.setOnClickListener(View.OnClickListener {
            var nome = editNome.text.toString()
            var categoria = editCategoria.text.toString()
            var preco = "R$ " + editPreco.text.toString()
            var data = Calendar.getInstance().time

            var anuncio = Anuncio(nome, categoria, R.drawable.icon_olx, preco, data)

            anunciosBox!!.put(anuncio)
            finish()
        })

        btnCancelar.setOnClickListener(View.OnClickListener {
            finish()
        })

    }
}
