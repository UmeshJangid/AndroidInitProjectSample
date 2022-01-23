package com.app.androidinitialproject.domain.model
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {
    @SerializedName("id")
    var id: Int = 0
    var account_type_id: Int = 0
    var first_name: String? = null
    var last_name: String? = null
    var mobile: String? = null
    var dob: String? = null
    var username: String? = null
    var email: String? = null
    var address: String? = null
    var fake_pass: String? = null
    var profile_photo: String? = null
    var latitude: Double = 0.toDouble()
    var longitude: Double = 0.toDouble()
    var gender: String? = null
    var link: String? = null
    var youtube_link: String? = null
    var blog: String? = null
    var bio: String? = null
    var isStatus: Boolean = false
    var isIs_verified: Boolean = false
    var isIs_deactivate: Boolean = false
    var isIs_private: Boolean = false
    var isIs_notification: Boolean = false
    var device_id: String? = null
    var device_type: String? = null
    var badge_count: Int = 0
    var auth_token: String? = null
    var verification_code: String? = null
    var login_count: Int = 0
    var created: String? = null
    var modified: String? = null
    var profile_photo_full_path: String? = null
    var token: String? = null
}
