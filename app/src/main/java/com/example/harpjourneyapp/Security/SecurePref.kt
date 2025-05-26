//package com.example.harpjourneyapp.Security
//
//object SecurePref {
//
//    private const val PREFS_NAME = "my_secure_prefs"
//
//    private lateinit var encryptedPrefs: EncryptedSharedPreferences
//
//    fun init(context: Context) {
//        val masterKey = MasterKey.Builder(context)
//            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//            .build()
//
//        encryptedPrefs = EncryptedSharedPreferences.create(
//            context,
//            PREFS_NAME,
//            masterKey,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }
//
//    fun saveAuthToken(token: String) {
//        encryptedPrefs.edit().putString("auth_token", token).apply()
//    }
//
//    fun getAuthToken(): String? {
//        return encryptedPrefs.getString("auth_token", null)
//    }
//}