package fr.ekito.myweatherapp.view.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import fr.ekito.myweatherapp.R
import fr.ekito.myweatherapp.domain.entity.DailyForecast
import fr.ekito.myweatherapp.domain.entity.getColorFromCode
import fr.ekito.myweatherapp.util.android.argument
import fr.ekito.myweatherapp.view.ErrorState
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Weather Detail View
 */
class DetailActivity : AppCompatActivity() {

    // Detail id passed by argument
    private val detailId by argument<String>(ARG_WEATHER_ITEM_ID)

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // Observe States
        viewModel.states.observe(this, Observer { state ->
            state?.let {
                when (state) {
                    is ErrorState -> showError(state.error)
                    is DetailViewModel.WeatherDetailState -> showDetail(state.weather)
                }
            }
        })
        viewModel.getDetail(detailId)
    }

    private fun showError(error: Throwable) {
        Snackbar.make(
            weatherItem,
            getString(R.string.loading_error) + " $error",
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun showDetail(weather: DailyForecast) {
        weatherIcon.text = weather.icon
        weatherDay.text = weather.day
        weatherText.text = weather.fullText
        weatherWindText.text = weather.wind.toString()
        weatherTempText.text = weather.temperature.toString()
        weatherHumidityText.text = weather.humidity.toString()
        weatherItem.background.setTint(getColorFromCode(weather))
        // Set back on background click
        weatherItem.setOnClickListener {
            onBackPressed()
        }
    }

    companion object {
        const val ARG_WEATHER_ITEM_ID: String = "WEATHER_ID"
    }
}
