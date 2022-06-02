package com.example.beerstest.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.collectLatest

abstract class BaseFragment<Event : ViewEvent, State : ViewState, Effect : ViewEffect> : Fragment() {

    protected abstract val binding: ViewBinding?

    protected abstract val viewModel: BaseViewModel<Event, State, Effect>

    abstract fun setupUi()

    abstract fun handleState(state: State)

    abstract fun handleEffect(effect: Effect)

    open fun showLoader() { }

    open fun hideLoader() { }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()

        lifecycleScope.launchWhenCreated {
            viewModel.state.collect(::handleState)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.effect.collect(::handleEffect)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.loader.collectLatest { loader ->
                when (loader) {
                    is Loader.Idle -> hideLoader()
                    is Loader.Loading -> showLoader()
                }
            }
        }
    }
}
