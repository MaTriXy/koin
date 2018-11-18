package fr.ekito.myweatherapp.view.detail

import fr.ekito.myweatherapp.domain.repository.DailyForecastRepository
import fr.ekito.myweatherapp.util.mvp.RxPresenter
import fr.ekito.myweatherapp.util.coroutines.SchedulerProvider
import fr.ekito.myweatherapp.util.coroutines.with

class DetailPresenter(
    private val id: String,
    private val dailyForecastRepository: DailyForecastRepository,
    private val schedulerProvider: SchedulerProvider
) : RxPresenter<DetailContract.View>(), DetailContract.Presenter {

    override var view: DetailContract.View? = null

    override fun getDetail() {
        launch {
            dailyForecastRepository.getWeatherDetail(id).with(schedulerProvider).subscribe(
                { detail ->
                    view?.showDetail(detail)
                }, { error -> view?.showError(error) })
        }
    }
}