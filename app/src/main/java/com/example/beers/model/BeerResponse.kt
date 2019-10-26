package com.example.beers.model


data class BeerResponse(
    val abv: Double,
    val attenuation_level: Double,
    val boil_volume: BoilVolume,
    val brewers_tips: String,
    val contributed_by: String,
    val description: String,
    val ebc: Double,
    val first_brewed: String,
    val food_pairing: List<String>,
    val ibu: Double,
    val id: Int,
    val image_url: String,
    val ingredients: Ingredients,
    val method: Method,
    val name: String,
    val ph: Double,
    val srm: Double,
    val tagline: String,
    val target_fg: Double,
    val target_og: Double,
    val volume: Volume
) {

    data class BoilVolume(
        val unit: String,
        val value: Int
    )

    data class Ingredients(
        val hops: List<Hop>,
        val malt: List<Malt>,
        val yeast: String
    )

    data class Hop(
        val add: String,
        val amount: Amount,
        val attribute: String,
        val name: String
    )

    data class Amount(
        val unit: String,
        val value: Float
    )

    data class Malt(
        val amount: AmountX,
        val name: String
    )

    data class AmountX(
        val unit: String,
        val value: Double
    )

    data class Method(
        val fermentation: Fermentation,
        val mash_temp: List<MashTemp>,
        val twist: Any
    )

    data class Fermentation(
        val temp: Temp
    )

    data class Temp(
        val unit: String,
        val value: Double
    )

    data class MashTemp(
        val duration: Int,
        val temp: TempX
    )

    data class TempX(
        val unit: String,
        val value: Int
    )

    data class Volume(
        val unit: String,
        val value: Int
    )
}