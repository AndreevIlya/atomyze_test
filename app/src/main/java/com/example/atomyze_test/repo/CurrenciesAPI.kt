package com.example.atomyze_test.repo

import com.google.gson.*
import com.google.gson.annotations.SerializedName
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.lang.reflect.Type
import javax.inject.Inject

interface CurrenciesAPI {

    @GET("daily_json.js")
    fun getCurrencies(): Single<Valute>

    data class Valute(
        @SerializedName("AUD")
        val aud: Currency,
        @SerializedName("AZN")
        val azn: Currency,
        @SerializedName("GBP")
        val gbp: Currency,
        @SerializedName("AMD")
        val amd: Currency,
        @SerializedName("BYN")
        val byn: Currency,
        @SerializedName("BGN")
        val bgn: Currency,
        @SerializedName("BRL")
        val brl: Currency,
        @SerializedName("HUF")
        val huf: Currency,
        @SerializedName("HKD")
        val hkd: Currency,
        @SerializedName("DKK")
        val dkk: Currency,
        @SerializedName("USD")
        val usd: Currency,
        @SerializedName("EUR")
        val eur: Currency,
        @SerializedName("INR")
        val inr: Currency,
        @SerializedName("KZT")
        val kzt: Currency,
        @SerializedName("CAD")
        val cad: Currency,
        @SerializedName("KGS")
        val kgs: Currency,
        @SerializedName("CNY")
        val cny: Currency,
        @SerializedName("MDL")
        val mdl: Currency,
        @SerializedName("NOK")
        val nok: Currency,
        @SerializedName("PLN")
        val pln: Currency,
        @SerializedName("RON")
        val ron: Currency,
        @SerializedName("XDR")
        val xdr: Currency,
        @SerializedName("SGD")
        val sgd: Currency,
        @SerializedName("TJS")
        val tjs: Currency,
        @SerializedName("TRY")
        val `try`: Currency,
        @SerializedName("TMT")
        val tmt: Currency,
        @SerializedName("UZS")
        val uzs: Currency,
        @SerializedName("CZK")
        val czk: Currency,
        @SerializedName("SEK")
        val sek: Currency,
        @SerializedName("CHF")
        val chf: Currency,
        @SerializedName("ZAR")
        val zar: Currency,
        @SerializedName("KRW")
        val krw: Currency,
        @SerializedName("JPY")
        val jpy: Currency,
    ) {

        val currencies
            get() = listOf(
                amd,
                aud,
                azn,
                bgn,
                brl,
                byn,
                gbp,
                cad,
                chf,
                cny,
                czk,
                dkk,
                eur,
                hkd,
                huf,
                inr,
                jpy,
                kgs,
                krw,
                kzt,
                mdl,
                nok,
                pln,
                ron,
                sek,
                sgd,
                tjs,
                tmt,
                `try`,
                usd,
                uzs,
                xdr,
                zar,
            )

        data class Currency(
            @SerializedName("CharCode")
            val code: String,
            @SerializedName("Name")
            val name: String,
            @SerializedName("Value")
            val rate: Float,
        )
    }

    private class ValuteDeserializer : JsonDeserializer<Valute> {

        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?,
        ): Valute = Gson().fromJson(
            json?.asJsonObject?.get("Valute")!!,
            Valute::class.java
        )

    }

    class Factory @Inject constructor() {

        fun create(): CurrenciesAPI {
            val gson = GsonBuilder()
                .registerTypeAdapter(Valute::class.java, ValuteDeserializer())
                .create()

            return Retrofit.Builder()
                .baseUrl("https://www.cbr-xml-daily.ru/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CurrenciesAPI::class.java)
        }
    }

}