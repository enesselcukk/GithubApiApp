package com.enesselcuk.githubapiapp.base


import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VB : ViewDataBinding>(
    private val inflateLayout: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected lateinit var activityDataBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDataBinding = inflateLayout(layoutInflater)
        definition()
        setContentView(activityDataBinding.root)
        
    }

    open fun definition() {}
    open fun setObserver() {}


}