package com.example.hipolito.olx

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.hipolito.olx.adapters.AnunciosRVAdapter
import io.objectbox.Box
import kotlinx.android.synthetic.main.activity_anuncios.*

class AnunciosActivity : AppCompatActivity() {

    var anuncios: MutableList<Anuncio> = ArrayList()
    var anunciosBox: Box<Anuncio>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncios)
        setupViews()
    }

    override fun onResume() {
        super.onResume()

        anuncios = anunciosBox!!.all

        var anunciosRVAdapter = AnunciosRVAdapter(this, anuncios, anunciosBox!!)

        rvAnuncios.adapter = anunciosRVAdapter
        rvAnuncios.setHasFixedSize(true)

        var linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.scrollToPosition(0)

        rvAnuncios.layoutManager = linearLayoutManager

    }

    private fun setupViews() {

        anunciosBox = (application as App).boxStore!!.boxFor(Anuncio::class.java!!)

        fabAddAnuncio.setOnClickListener { view ->
            var intent = Intent(this@AnunciosActivity, AddAnunciosActivity::class.java)
            startActivity(intent)
        }

    }
}
