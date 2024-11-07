package com.stdev.shopit.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stdev.shopit.R
import com.stdev.shopit.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class SplashFragment : Fragment() {


// here we define the variable
    @Inject
    lateinit var viewModel : SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash,container,false)

    }

    @SuppressLint("CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // here we call our variable
        val  ivAppLogo:ImageView = view.findViewById(R.id.ellipse)

        val aniFade:Animation =AnimationUtils.loadAnimation(requireActivity(), R.anim.fade_in)
       ivAppLogo.startAnimation(aniFade)

        val slideUp: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.slide_up_from_outside)

        val tvAProductOf: TextView = view.findViewById(R.id.tvQuality)
        tvAProductOf.startAnimation(slideUp)

        val ivLogo: ImageView = view.findViewById(R.id.ellipse)
        ivLogo.startAnimation(slideUp)

      val view: Button = view.findViewById(R.id.buttonStart)
        view.startAnimation(slideUp)
        Handler().postDelayed({
            //check if the user is logged in
            if (viewModel.loggedIn){
                //navigate to the home fragment
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }else{
                //navigate to the login fragment
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        },5000)
    }

}