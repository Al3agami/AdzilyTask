package com.agamidev.adzilytask.Api


class NetworkStatus(val status: Status, val message:String) {

    companion object{
        val LOADING: NetworkStatus
        val LOADED: NetworkStatus
        val ERROR: NetworkStatus
        val END_OF_LIST: NetworkStatus

        init {
            LOADING =
                NetworkStatus(
                    Status.LOADING,
                    "Data Loading"
                )
            LOADED =
                NetworkStatus(
                    Status.SUCCESS,
                    "Data Loaded Successfully"
                )
            ERROR =
                NetworkStatus(
                    Status.FAILED,
                    "Error Fetching Data!"
                )
            END_OF_LIST =
                NetworkStatus(
                    Status.FAILED,
                    "End of List"
                )
        }

    }
}


enum class Status{
    LOADING,
    SUCCESS,
    FAILED
}