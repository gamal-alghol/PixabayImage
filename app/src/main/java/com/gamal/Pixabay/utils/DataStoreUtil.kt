package com.gamal.Pixabay.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gamal.Pixabay.data.model.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class DataStoreUtil(private val context: Context){

    companion object {
        private var INSTANCE: DataStoreUtil? = null

        // Define DataStore by creating a Context extension
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

        // Method to get the singleton instance
        fun getInstance(context: Context): DataStoreUtil {
            return INSTANCE ?: synchronized(this) {
                val instance = DataStoreUtil(context)
                INSTANCE = instance
                instance
            }
        }
    }
    private val userKey = stringPreferencesKey(USER)
    private val checkSessionUserKey = booleanPreferencesKey(CHECK_SESSION_USER)

     suspend fun getUser() : User? {
        val userName: String = context.dataStore.data
            .map {store->
                store[userKey] ?: ""
            }.firstOrNull().toString()
         return if (userName.isNotEmpty()) {
             Gson().fromJson(userName, User::class.java)
         } else {
             null
         }
     }

    suspend fun saveUser(userJson: String?) {
        context.dataStore.edit {dataStore->
            dataStore[userKey]=userJson.toString() }
    }
    suspend fun setCheckSessionUser(isLogging: Boolean) {
        context.dataStore.edit {dataStore->
            dataStore[checkSessionUserKey]=isLogging
            }
    }
     suspend fun getCheckSessionUser(): Boolean {
         val preferences = context.dataStore.data.firstOrNull()
         return preferences?.get(checkSessionUserKey) ?: false
    }

}


