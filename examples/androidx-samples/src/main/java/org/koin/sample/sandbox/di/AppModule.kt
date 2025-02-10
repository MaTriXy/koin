package org.koin.sample.sandbox.di

import org.koin.androidx.fragment.dsl.fragmentOf
// 4.0 deprecations
//import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.*
import org.koin.core.module.includes
import org.koin.core.qualifier.named
import org.koin.dsl.lazyModule
import org.koin.dsl.module
import org.koin.sample.sandbox.components.Counter
import org.koin.sample.sandbox.components.SCOPE_ID
import org.koin.sample.sandbox.components.SCOPE_SESSION
import org.koin.sample.sandbox.components.main.DumbServiceImpl
import org.koin.sample.sandbox.components.main.RandomId
import org.koin.sample.sandbox.components.main.SimpleService
import org.koin.sample.sandbox.components.main.SimpleServiceImpl
import org.koin.sample.sandbox.components.mvp.FactoryPresenter
import org.koin.sample.sandbox.components.mvp.ScopedPresenter
import org.koin.sample.sandbox.components.mvvm.*
import org.koin.sample.sandbox.components.scope.Session
import org.koin.sample.sandbox.components.scope.SessionActivity
import org.koin.sample.sandbox.components.scope.SessionConsumer
import org.koin.sample.sandbox.mvp.MVPActivity
import org.koin.sample.sandbox.mvvm.MVVMActivity
import org.koin.sample.sandbox.mvvm.MVVMFragment
import org.koin.sample.sandbox.navigation.NavViewModel
import org.koin.sample.sandbox.navigation.NavViewModel2
import org.koin.sample.sandbox.scope.ScopedActivityA
import org.koin.sample.sandbox.scope.ScopedFragment
import org.koin.sample.sandbox.workmanager.SimpleWorker
import org.koin.sample.sandbox.workmanager.SimpleWorkerService

val appModule = module {

    singleOf(::SimpleServiceImpl) { bind<SimpleService>() }
    singleOf(::DumbServiceImpl) {
        named("dumb")
        bind<SimpleService>()
    }
    factory { RandomId() }
}

val mvpModule = module {
    //factory { (id: String) -> FactoryPresenter(id, get()) }
    factoryOf(::FactoryPresenter)

    scope<MVPActivity> {
        scopedOf(::ScopedPresenter)// { (id: String) -> ScopedPresenter(id, get()) }

    }
}

val mvvmModule = module {

    viewModelOf(::SimpleViewModel)// { (id: String) -> SimpleViewModel(id, get()) }
    viewModelOf(::SimpleViewModel) { named("vm1") } //{ (id: String) -> SimpleViewModel(id, get()) }
    viewModel(named("vm2")) { (id: String) -> SimpleViewModel(id, get()) }

    viewModelOf(::SavedStateViewModel)// { params -> SavedStateViewModel(get(), params.get(), get()) }// injected params
    viewModelOf(::SavedStateBundleViewModel)// { SavedStateBundleViewModel(get(), get()) }// injected params

    // viewModel<AbstractViewModel> { ViewModelImpl(get()) }
    viewModelOf(::ViewModelImpl) { bind<AbstractViewModel>() }

    viewModelOf(::MyScopeViewModel)
    scope<MyScopeViewModel> {
        scopedOf(::Session)
    }

    viewModelOf(::MyScopeViewModel2)
    scope<MyScopeViewModel2> {
        scopedOf(::Session)
        scopedOf(::SessionConsumer)
    }

    viewModelOf(::SavedStateViewModel) { named("vm2") }

    viewModel { (s : Session) -> SharedVM(s)}
    scope<MVVMActivity> {
        scopedOf(::Session)
        fragmentOf(::MVVMFragment) // { MVVMFragment(get()) }

        scoped { MVVMPresenter1(get()) }
        scoped { MVVMPresenter2(get()) }
    }
    scope<MVVMFragment> {
        scoped { (id: String) -> ScopedPresenter(id, get()) }
        // to retrieve from parent
//        scopedOf(::Session)
    }
}

val scopeModule = module {
    scope(named(SCOPE_ID)) {
        scopedOf(::Session) {
            named(SCOPE_SESSION)
            onClose {
                // onRelease, count it
                Counter.released++
                println("Scoped -SCOPE_SESSION- release = ${Counter.released}")
            }
        }
    }
}

val scopeModuleActivityA = module {
    scope<ScopedActivityA> {
        fragmentOf(::ScopedFragment)
        scopedOf(::Session)
        scopedOf(::SessionActivity)
    }
    scope<ScopedFragment> {

    }
}

val workerServiceModule = module {
    singleOf(::SimpleWorkerService)
}

val workerScopedModule = module {
    workerOf(::SimpleWorker)// { SimpleWorker(get(), androidContext(), it.get()) }
}

val navModule = module {
    viewModelOf(::NavViewModel)
    viewModelOf(::NavViewModel2)
}

val allModules = module {
    includes(appModule, mvpModule, mvvmModule , scopeModule , workerServiceModule , workerScopedModule , navModule , scopeModuleActivityA)
}