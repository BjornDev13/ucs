package org.odk.collect.android.preferences

import com.google.android.gms.maps.GoogleMap
import com.mapbox.mapboxsdk.maps.Style
import org.odk.collect.android.R
import org.odk.collect.android.application.Collect
import org.odk.collect.android.configure.keys.ProjectKeys
import org.odk.collect.android.utilities.QuestionFontSizeUtils

object Defaults {

    @JvmStatic
    val unprotected: HashMap<String, Any>
        get() {
            val hashMap = HashMap<String, Any>()
            // odk_server_preferences.xml
            hashMap[ProjectKeys.KEY_SERVER_URL] = "https://demo.getodk.org"
            hashMap[ProjectKeys.KEY_USERNAME] = ""
            hashMap[ProjectKeys.KEY_PASSWORD] = ""
            // form_management_preferences.xml
            hashMap[ProjectKeys.KEY_AUTOSEND] = "off"
            hashMap[ProjectKeys.KEY_GUIDANCE_HINT] = "no"
            hashMap[ProjectKeys.KEY_DELETE_AFTER_SEND] = false
            hashMap[ProjectKeys.KEY_COMPLETED_DEFAULT] = true
            hashMap[ProjectKeys.KEY_CONSTRAINT_BEHAVIOR] = ProjectKeys.CONSTRAINT_BEHAVIOR_ON_SWIPE
            hashMap[ProjectKeys.KEY_HIGH_RESOLUTION] = true
            hashMap[ProjectKeys.KEY_IMAGE_SIZE] = "original_image_size"
            hashMap[ProjectKeys.KEY_INSTANCE_SYNC] = true
            hashMap[ProjectKeys.KEY_PERIODIC_FORM_UPDATES_CHECK] = "every_fifteen_minutes"
            hashMap[ProjectKeys.KEY_AUTOMATIC_UPDATE] = false
            hashMap[ProjectKeys.KEY_HIDE_OLD_FORM_VERSIONS] = true
            hashMap[ProjectKeys.KEY_BACKGROUND_LOCATION] = true
            hashMap[ProjectKeys.KEY_BACKGROUND_RECORDING] = true
            hashMap[ProjectKeys.KEY_FORM_UPDATE_MODE] = "manual"
            // form_metadata_preferences.xml
            hashMap[ProjectKeys.KEY_METADATA_USERNAME] = ""
            hashMap[ProjectKeys.KEY_METADATA_PHONENUMBER] = ""
            hashMap[ProjectKeys.KEY_METADATA_EMAIL] = ""
            // google_preferences.xml
            hashMap[ProjectKeys.KEY_SELECTED_GOOGLE_ACCOUNT] = ""
            hashMap[ProjectKeys.KEY_GOOGLE_SHEETS_URL] = ""
            // identity_preferences.xml
            hashMap[ProjectKeys.KEY_ANALYTICS] = true
            // server_preferences.xml
            hashMap[ProjectKeys.KEY_PROTOCOL] = ProjectKeys.PROTOCOL_SERVER
            // user_interface_preferences.xml
            hashMap[ProjectKeys.KEY_APP_THEME] = Collect.getInstance().getString(R.string.app_theme_system)
            hashMap[ProjectKeys.KEY_APP_LANGUAGE] = ""
            hashMap[ProjectKeys.KEY_FONT_SIZE] = QuestionFontSizeUtils.DEFAULT_FONT_SIZE.toString()
            hashMap[ProjectKeys.KEY_NAVIGATION] = ProjectKeys.NAVIGATION_BOTH
            hashMap[ProjectKeys.KEY_SHOW_SPLASH] = false
            hashMap[ProjectKeys.KEY_SPLASH_PATH] = Collect.getInstance().getString(R.string.default_splash_path)
            hashMap[ProjectKeys.KEY_EXTERNAL_APP_RECORDING] = false
            // map_preferences.xml
            hashMap[ProjectKeys.KEY_BASEMAP_SOURCE] = ProjectKeys.BASEMAP_SOURCE_GOOGLE
            hashMap[ProjectKeys.KEY_CARTO_MAP_STYLE] = "positron"
            hashMap[ProjectKeys.KEY_USGS_MAP_STYLE] = "topographic"
            hashMap[ProjectKeys.KEY_GOOGLE_MAP_STYLE] = GoogleMap.MAP_TYPE_NORMAL.toString()
            hashMap[ProjectKeys.KEY_MAPBOX_MAP_STYLE] = Style.MAPBOX_STREETS
            return hashMap
        }
}
