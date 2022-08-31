package com.sonasetiana.githubuser.data.local
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.sonasetiana.githubuser.data.DetailUserData
import com.sonasetiana.githubuser.data.DetailUserResults
import com.sonasetiana.githubuser.data.UserData
import com.sonasetiana.githubuser.data.UserResults
import java.io.IOException

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun Context.getGithubUsers() : ArrayList<UserData> {
    val json = getJsonDataFromAsset(context = this, fileName = "github_user.json")
    val data = Gson().fromJson(json, UserResults::class.java)
    return data.data
}

fun Context.getDetailGithubUser(userId: Int) : DetailUserData? {
    val json = getJsonDataFromAsset(context = this, fileName = "github_detail_user.json")
    val data = Gson().fromJson(json, DetailUserResults::class.java)
    return data.data.find { it.id == userId }
}

fun AppCompatImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun AppCompatImageView.loadCircleImage(url: String) {
    Glide.with(this).load(url).circleCrop().into(this)
}

fun ImageView.loadImage(url: String) {
    Glide.with(this).load(url).into(this)
}

fun Context.share(url: String, title: String) {
    Glide
        .with(this)
        .asBitmap()
        .load(url)
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .into(object : SimpleTarget<Bitmap>(250, 250){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val intent = Intent(Intent.ACTION_SEND)
                val path = MediaStore.Images.Media.insertImage(contentResolver, resource, "", null)
                val uri = Uri.parse(path)
                intent.putExtra(Intent.EXTRA_TEXT, title)
                intent.putExtra(Intent.EXTRA_STREAM, uri)
                intent.type = "image/*"
                ContextCompat.startActivity(this@share, Intent.createChooser(intent, "Share image via..."), null)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                Toast.makeText(this@share, "Gagal me-load image", Toast.LENGTH_SHORT).show()
                super.onLoadFailed(errorDrawable)
            }
        })

}