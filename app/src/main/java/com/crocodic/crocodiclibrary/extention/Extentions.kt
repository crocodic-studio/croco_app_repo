package com.crocodic.crocodiclibrary.extention

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.PorterDuff
import android.os.Build
import android.os.Handler
import android.support.annotation.Px
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

fun TextView.leftIcon(drawableId: Int) {
    setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(context, drawableId), null, null, null)
}

@SuppressLint("NewApi")
fun TextView.iconTint(colorId: Int) {
    MorAbove {
        compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, colorId))
    }
}

var View.scale: Float
    get() = Math.min(scaleX, scaleY)
    set(value) {
        scaleY = value
        scaleX = value
    }

fun View.addTopMargin(@Px marginInPx: Int) {
    (layoutParams as ViewGroup.MarginLayoutParams).topMargin = marginInPx
}

fun View.addBottomMargin(@Px marginInPx: Int) {
    (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = marginInPx
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.onClick(function: () -> Unit) {
    setOnClickListener {
        function()
    }
}

infix fun ViewGroup.inflate(layoutResId: Int): View =
    LayoutInflater.from(context).inflate(layoutResId, this, false)

fun ImageView.tint(colorId: Int) {
    setColorFilter(context.takeColor(colorId), PorterDuff.Mode.SRC_IN)
}

operator fun ViewGroup.get(index: Int): View = getChildAt(index)


infix fun Context.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)

operator fun Context.get(resId: Int): String = getString(resId)

operator fun Fragment.get(resId: Int): String = getString(resId)

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

inline fun delay(milliseconds: Long, crossinline action: () -> Unit) {
    Handler().postDelayed({
        action()
    }, milliseconds)
}

@Deprecated("Use emptyString instead", ReplaceWith("emptyString"), level = DeprecationLevel.WARNING)
fun emptyString() = ""

val emptyString = ""

inline fun LorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        body()
    }
}

inline fun MorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        body()
    }
}

inline fun NorAbove(body: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        body()
    }
}

@SuppressLint("NewApi")
fun String.toHtml(): Spanned {
    NorAbove {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    }
    return Html.fromHtml(this)
}

fun Int.toPx(context: Context): Int {
    val density = context.resources.displayMetrics.density
    return (this * density).toInt()
}

fun <T> unSafeLazy(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) {
        initializer()
    }
}

fun Int.isZero(): Boolean = this == 0

inline fun <F, S> doubleWith(first: F, second: S, runWith: F.(S) -> Unit) {
    first.runWith(second)
}

val Any?.isNull: Boolean
    get() = this == null

inline fun <reified T> Activity.start() {
    this.startActivity(Intent(this, T::class.java))
}
inline fun <reified T> Activity.start(name : String,value : String) {
    this.startActivity(Intent(this, T::class.java).putExtra(name,value))
}

inline fun <reified T> Activity.startClearTask() {
    this.startActivity(Intent(this, T::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
}

fun Activity.isPortrait() = this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

fun Activity.overideFade(){
    try {
        this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

infix fun Activity.takeColor(colorId: Int) = ContextCompat.getColor(this, colorId)
