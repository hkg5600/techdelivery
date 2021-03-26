package com.example.core.data.session

import com.example.core.data.preference.PreferenceDataStore
import com.example.core.domain.session.model.RefreshToken
import com.example.core.domain.session.model.Token
import com.example.core.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheAccountDataSource @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore
) {

    fun loadToken(): Flow<String?> {
        return preferenceDataStore.loadToken()
    }

    fun saveToken(token : Token) : Flow<Result<Unit>> {
        return preferenceDataStore.saveToken(token)
    }

    fun saveRefreshToken(token : RefreshToken) : Flow<Result<Unit>> {
        return preferenceDataStore.saveRefreshToken(token)
    }

}
