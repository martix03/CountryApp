package it.prova.prima.spalla

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}

/*
*
*
Bandiera
mappa
Nome completo
Nome ufficiale
Lingue ufficiali
Capitale
Superficie Totale
Continente	Asia
Confini	Iran, Pakistan, Turkmenistan, Uzbekistan, Tagikistan, Cina
Fuso orario	UTC+4:30
Valuta	Afghani
*
* */