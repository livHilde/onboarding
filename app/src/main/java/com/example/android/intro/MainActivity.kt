package com.example.android.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)
    }

    private fun setOnboardingItems(){
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    onboardingImage = R.drawable.search,
                    title = "Velg lokasjon for info om siktforhold",
                    description = "Her kan du søke i søkefeltet eller trykke på kartet"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.results,
                    title = "Få info for valgt lokasjon",
                    description = "Her kan du se vær- og siktforhold under solnedgang og soloppgang"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.favorite,
                    title = "Lagre dine favoritter",
                    description = "Her kan du slette og legge til, gøy!"
                ),
                OnboardingItem(
                    onboardingImage = R.drawable.settings,
                    title = "Innstillinger",
                    description = "Bytt til darkmode, les spennende info om appen!"
                ),
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onBoardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
        findViewById<ImageView>(R.id.iv_next).setOnClickListener {
            if(onboardingViewPager.currentItem+1 < onboardingItemsAdapter.itemCount) {
                onboardingViewPager.currentItem += 1
            }else{
                navigateToHomeActivity()
            }
        }
        findViewById<MaterialButton>(R.id.buttonSkip).setOnClickListener {
            navigateToHomeActivity()
        }
    }
    private fun navigateToHomeActivity() {
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }
    private fun setupIndicators(){
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.let{
                it.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                it.layoutParams = layoutParams
                indicatorsContainer.addView(it)
            }
        }
    }
    private fun setCurrentIndicator(position: Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active_background
                    )
                )
            }else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }
    }
}