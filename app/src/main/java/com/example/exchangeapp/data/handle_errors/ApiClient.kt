package com.example.exchangeapp.data.handle_errors

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

suspend fun <R,M>  handleResponse(request: suspend () -> Response<R>, mapResponse: (R) -> M) : Flow<M> {
    return flow {
        val response = request()
        if (response.isSuccessful){
            val returningData : M? = response.body()?.let { mapResponse(it) }
            returningData?.let {
                emit(it)
                return@flow
            }
        } else {
            if (response.code() == 404){
                // TODO this should be fixed ASAP
                error(Error("not found"))

            }else{
                error(error(response.errorBody().toString()))
            }
        }
    }.flowOn(Dispatchers.IO)
}
