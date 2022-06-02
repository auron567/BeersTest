package com.example.beerstest.core.viewbinding

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingInflateDelegate<T : ViewBinding>(
    val fragment: Fragment,
    val viewBindingFactory: (LayoutInflater) -> T?
) : ReadOnlyProperty<Fragment, T?> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                val viewLifecycleOwner = it ?: return@Observer

                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        binding = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        if (binding != null) {
            return binding
        }

        try {
            val lifecycle = fragment.viewLifecycleOwner.lifecycle
            if (!lifecycle.currentState.isAtLeast(INITIALIZED)) {
                throw IllegalStateException("Should not attempt to inflate when Fragment is destroyed.")
            }
        } catch (e: IllegalStateException) {
            return null
        }

        return viewBindingFactory(thisRef.layoutInflater).also { this.binding = it }
    }
}

fun <T : ViewBinding> Fragment.viewBinding(viewInflationFactory: (LayoutInflater) -> T?) =
    FragmentViewBindingInflateDelegate(this, viewInflationFactory)
