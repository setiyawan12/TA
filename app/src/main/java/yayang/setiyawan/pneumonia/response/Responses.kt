package yayang.setiyawan.pneumonia.response

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("data") var data : T,
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Int
)
data class WrappedListResponse<T>(
    @SerializedName("data") var data : List<T>,
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Int
)
data class WrappedListResponseArray<T>(
    @SerializedName("data") var data : ArrayList<T>,
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Int
)