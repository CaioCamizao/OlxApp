package com.example.hipolito.olx.adapters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.example.hipolito.olx.Anuncio
import com.example.hipolito.olx.R
import io.objectbox.Box
import kotlinx.android.synthetic.main.dialog_editar_anuncio.*
import kotlinx.android.synthetic.main.dialog_editar_anuncio.view.*
import java.util.*

/**
 * Created by hipolito on 10/02/18.
 */

class AnunciosRVAdapter (
        private var context: Activity,
        private var anuncios: MutableList<Anuncio>,
        private var anunciosBox: Box<Anuncio>
    ) : RecyclerView.Adapter<AnunciosRVAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var txtNome: TextView
        var txtCategoria: TextView
        var txtPreco: TextView
        var txtData: TextView
        var ivImagemProduto: ImageView

        init {
            txtNome = itemView.findViewById(R.id.txtItemNomeProduto)
            txtCategoria = itemView.findViewById(R.id.txtItemCategoria)
            txtPreco = itemView.findViewById(R.id.txtItemPreco)
            txtData = itemView.findViewById(R.id.txtItemData)
            ivImagemProduto = itemView.findViewById(R.id.ivItemImagem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {

        var contexto = parent.context
        var inflater = LayoutInflater.from(contexto)

        val view = inflater.inflate(R.layout.item_lista_produtos, parent, false)

        var viewHolder = ViewHolder(view)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var anuncio = anuncios.get(position)

        holder.txtNome.setText(anuncio.nome)
        holder.txtCategoria.setText(anuncio.categoria)
        holder.txtPreco.setText(anuncio.preco)
        holder.txtData.setText("" + anuncio.data!!.day + "/" + anuncio.data!!.month)
        holder.ivImagemProduto.setImageResource(anuncio.imagem)

        setupClicks(holder, anuncio, position);
    }

    private fun setupClicks(holder: AnunciosRVAdapter.ViewHolder, anuncio: Anuncio, position: Int) {

        holder.itemView.setOnLongClickListener { view ->

            var popupMenu = PopupMenu(context.applicationContext, view)
            var inflater = popupMenu.menuInflater

            inflater.inflate(R.menu.popup_menu_anuncios, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.popup_deletar){
                    deletarAnuncio(anuncio, position)
                }else if(menuItem.itemId == R.id.popup_editar){
                    editarAnuncio(anuncio, position)
                }

                return@setOnMenuItemClickListener true
            }

            popupMenu.show()

            return@setOnLongClickListener true
        }

    }

    private fun editarAnuncio(anuncio: Anuncio, position: Int) {

        var inflater = context.layoutInflater
        var dialog = inflater.inflate(R.layout.dialog_editar_anuncio, null)

        dialog.editCategoriaDialog.setText(anuncio.categoria)
        dialog.editPrecoDialog.setText(anuncio.preco)
        dialog.editNomeDialog.setText(anuncio.nome)

        var builder = AlertDialog.Builder(context)

        builder.setTitle("Editar")
        builder.setView(dialog)
                .setPositiveButton("OK", { dialogInterface: DialogInterface, i: Int ->
                    var nome = dialog.editNomeDialog.text.toString()
                    var categoria = dialog.editCategoriaDialog.text.toString()
                    var preco = dialog.editPrecoDialog.text.toString()

                    var upAnuncio = anunciosBox.get(anuncio.id)
                    upAnuncio.nome = nome
                    upAnuncio.preco = preco
                    upAnuncio.categoria = categoria

                    anuncios.set(position, upAnuncio)
                    anunciosBox.put(upAnuncio)
                    notifyItemChanged(position)
                } )
                .setNegativeButton("Cancelar", { dialogInterface, i -> })

        builder.create()
        builder.show()

    }

    private fun deletarAnuncio(anuncio: Anuncio, position: Int) {
        anuncios.remove(anuncio)
        anunciosBox.remove(anuncio)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)
        Toast.makeText(context, anuncio.nome + " Removido!", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount(): Int {
        return anuncios.size
    }

}

