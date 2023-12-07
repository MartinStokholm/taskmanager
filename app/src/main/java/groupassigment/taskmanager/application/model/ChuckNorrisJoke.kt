package groupassigment.taskmanager.application.model

import com.google.gson.annotations.SerializedName

data class ChuckNorrisJoke(
    @SerializedName("icon_url") val iconUrl: String,
    val id: String,
    val url: String,
    val value: String
)
