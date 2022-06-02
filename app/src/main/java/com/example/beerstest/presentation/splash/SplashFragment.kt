package com.example.beerstest.presentation.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.beerstest.core.base.BaseFragment
import com.example.beerstest.core.viewbinding.viewBinding
import com.example.beerstest.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override val binding by viewBinding(FragmentSplashBinding::inflate)

    override val viewModel by viewModels<SplashViewModel>()

    override fun setupUi() {
    }

    override fun handleState(state: SplashContract.State) {
    }

    override fun handleEffect(effect: SplashContract.Effect) {
        when (effect) {
            is SplashContract.Effect.GoToBeerList -> goToBeerList()
        }
    }

    private fun goToBeerList() {
        val direction = SplashFragmentDirections.actionSplashFragmentToBeerListFragment()
        findNavController().navigate(direction)
    }
}
