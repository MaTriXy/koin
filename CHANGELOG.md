# Change Log

## [0.9.3]()

Gradle & Continuous Integration

*version published with new build chain*

## [0.9.2]()

_Core_

* `[UPDATED]` build updated to Kotlin 1.2.31
* `[UPDATED]` dissociate overload with dynamic parameters on `get()` and `inject()` for better autocomplete suggestions
* `[FIXED]` fix `startKoin()` property loading from boolean `loadProperties`
* `[FIXED]` inject by name take now care of injected type [#92](https://github.com/Ekito/koin/issues/92)

_Android_

* `[FIXED]` `startKoin()` doesn't break RestrictMode anymore and you can avoid properties load directly from koin.properties [#96](https://github.com/Ekito/koin/issues/96)
* `[UPDATED]` dissociate overload with dynamic parameters on `get()` and `inject()` for better autocomplete suggestions

_Android Architecture_

* `[UPDATED]` dissociate overload with dynamic parameters on `viewModel(), `getViewModel()` and `getSharedViewModel()` for better autocomplete suggestions
* `[UPDATED]` now exclude gradle dependency against `LiveData`

_Samples_

* `FIXED` Ktor hello appp sample

## [0.9.1]()

_DSL_

* `[FIXED]` Parameter propagation - You can now reuse params in `get()` to propagate it [#66](https://github.com/Ekito/koin/issues/66)

_Core_

* `[ADDED]` Parameters are now expressed via Kotlin function to allow lazy evaluation [#67](https://github.com/Ekito/koin/issues/67)
* `[UPDATED]` Parameter propagation [#66](https://github.com/Ekito/koin/issues/66)
* `[FIXED]` Stack resolution engine fixed [#72](https://github.com/Ekito/koin/issues/72)
* `[FIXED]` Better Logs, Error & Stacktraces
* `[FIXED]` Properties loading from external `properties` file can read values as String/Integer/Float

_Android_

* `[ADDED]` `get()` direct dependency eager fetching function for Activity/Fragment/Service [#78](https://github.com/Ekito/koin/issues/78)


_Android Architecture_

* `[ADDED]` To help misuse the Koin ViewModel API, we introduce the `getSharedViewModel()` and `sharedViewModel()` from Fragment, to reuse `ViewModel` from parent `Activity`. `getViewModel()` and `viewModel()` now creates `ViewModel` instance (no `FromActivity` parameter anymore)
* `[UPDATED]` ViewModel factory logs to see what Activity/Fragment is getting `ViewModel` instance


## [0.9.0]()

_DSL_

* `[DEPRECATED]` - `provide` - can be directly replace with `bean` 
* `[UPDATED]` - `bean` and `factory` can now use `bind`
* `[ADDED]` - defintion function inside `bean` or `factory` can now have parameters

_Core_

* `[FIXED]` Context resolution and scope isolation reworked - now fully functionnal
* `[FIXED]` Stack resolution
* `[ADDED]` `StandAloneContext` function `loadKoinModules` to load Koin modules wether Koin is already started
* `[ADDED]` `StandAloneContext` function `loadProperties` to load Koin properties wether Koin is already started
* `[ADDED]` `by inject()` function handle parameters to definition ([#59](https://github.com/Ekito/koin/issues/59))
* `[FIXED]` Logging with class name need introspection
* `[ADDED]` Context lifecycle notification callback - to allow a callback when `releaseContext()` is called on a context. You can register with `StandAloneContext.registerContextCallBack()` ([#58](https://github.com/Ekito/koin/pull/58))

_Android_

* `[FIXED]` StartKoin have a `logger` parameter, with default as AndroidLogger(), to allow specify logging implementation to use with Koin ([#50](https://github.com/Ekito/koin/issues/50)) - allow no logging at startKoin of logger is set to `EmptyLogger()`
* `[ADDED]` `by inject()` function handle parameters to definition ([#49](https://github.com/Ekito/koin/issues/49))


_Android Architecture_

* `[FIXED]` fix viewModel extensions rewritten from `LifecycleOwner` interface
* `[FIXED]` fix `by viewModel()` API to allow use KClass object argument instead of type inference - offers now `getViewModelByclass()` and `viewModelByClass()` ([#56](https://github.com/Ekito/koin/issues/56))
* `[ADDED]` `by viewModel()` function handle parameters to definition ([#49](https://github.com/Ekito/koin/issues/49))
* `[ADDED]` `by viewModel()` function handle key instance for Android ViewModelFactory ([#49](https://github.com/Ekito/koin/issues/49))


## [0.8.2]()

_Core_

* fix internal instance registry due to compat error with Kolin `1.1.61` & `1.2.21` - broke  Koin API for app using jdk 7 under the hood - [issue #43](https://github.com/Ekito/koin/issues/43)

## [0.8.1]()

_Android Architecture_

<div class="alert alert-primary" role="alert">
  Now declare your ViewModel lazyly in attributes, with <b>by viewModel()</b> like <b>by inject()</b>
</div>

* `by viewModel()` lazy function call the `getViewModel()` function and allow `val` attribute declaration of your `ViewModel` (like with `by inject()`) - ([issue #37](https://github.com/Ekito/koin/issues/37))
* Better support to share `ViewModel` between Activity and Fragments
* `android.arch.lifecycle:extensions` updated to `1.1.0`

_Spark_

<div class="alert alert-primary" role="alert">
    <b>start</b> and <b>startKoin</b> functions have been merged 
</div>

* `startSpark {}` has been renamed to `start {}` and have a `modules` arguments to list all of your modules
* `start()` and `startKoin` has been merged to `start( modules = <List of modules>)`
* `stopSpark{}` has been renamed to `stop {}` and include `closeKoin()`

_Core_

* Kotlin `1.2.21`
* Internal fixes around Bean definition lookup / Duplicated defintion ([issue #39](https://github.com/Ekito/koin/issues/39))
* Fixed loading from koin.properties (for embedded jar)


## [0.8.0]()

<div class="alert alert-secondary" role="alert">
  Introducing new modules: koin-spark and koin-android-architecture
</div>

_Android Architecture_

* `viewModel` DSL keyword (specialized `provide` alias for ViewModel), to declare an Architecture Components ViewModel
* `getViewModel()` function in Activities and Fragments, to get and bind ViewModel components

_Spark_

* `controller {}` DSL keyword (specialized `provide` alias), to declare Spark controllers classes
* `start() {}` function to start Spark server (optional port number) - lambda expression to allow the start of Koin and any controller instantiation 
* `runControllers()` function to instantiate all spark controllers, declared with `controller` keyword DSL

_Core_

* Better logging and error display
* Some fixes about internals resolution for bound types

<br/>

## [0.7.1]()

<div class="alert alert-warning" role="alert">
This 0.7.x branch brings great simplifications in DSL and API.  Users from Koin 0.6.x may check the <a href="{{ site.baseurl }}/docs/{{ site.docs_version }}/updates/whats-new">What's new in 0.7.x</a> guide 👍
</div>

Kotlin compilation has been updated to **1.1.61** 

_Core_

* Simplified DSL modules: no more need of `Module` class. Now use directly the `applicationContext`function
* Default Koin Logger to `PrintLogger` instead of `EmptyLogger`
* a definition can be overriden (with a definition same name and type)
* DSL `provide` aliases with `bean` and `factory`
* direct interface binding writing style (avoid to use `bind` keyword)
* Koin instances resolution is now thread safe and compatible with coroutines
* starter chain reviewed to allow better extension of `startKoin()`
* better logs to display how instance and reoslution are made
* Context isolation disabled by default. Can be activitaed later

_Android_

<div class="alert alert-warning" role="alert">
  Android ContextAware components have been dropped. Please, check the <a href="{{ site.baseurl }}/docs/{{ site.docs_version }}/updates/whats-new">migration</a> guide
</div>

* Default Koin Logger to `AndroidLogger` instead of `EmptyLogger`
* No need anymore of `AndroidModule`, just use `applicationContext` to declare a module
* Android extensions have been reworked to avoid need of support Library
* `androidApplication()` is a DSL extension to provide Android `Application` resolution (can also be done with `get<Application>()`)

_Samples_

* Updated Android weather app, with multiple Activity and better demo for property usage
* Simple Kotlin app sample added

<br/>
## [0.6.1]()

_Core_

* Added context/sub-contexts visibility checks
* `startKoin()` is now part of standAloenContext
* `closeKoin()` now close actual Koin context
* `KoinComponent` has `get()` and `getProperty()`

_Android_

* reworked `starKoin()` function
* reviewed android base extensions to find high-level extension point

_Samples_

* added kotlin-simpleapp - sample application for pure Kotlin 
* added ktor-starter - for demoing Koin for Ktor web app development

_Ktor_

* koin-ktor module has been started


## [0.6.0]()

The target of this release was to simplify and make clearer the syntax of Koin.

_DSL_

* `getProperties` can now have a default value

_Core_

* Stand alone complex & KoinComponent have been reviewed
* renamed startContext functions to `startKoin()`
* KoinComponent
  - inject/property
  - setProperty
  - release context or properties
  - startKoin

* Koin can load properties from koin.properties file or system properties
* `startKoin()` have now a `properties` parameter to give additional properties at startup

#### No more `getKoin()` !

KoinTest
* directly extends `KoinComponent`
* context assert/test tools & extensions
* `dryRun()` is usable in a Kointest component, after a `startKoin`

Android
* fix/better extensions for Android
* `bindProperty()` renamed to `setProperty()`
* `startAndroidContext()` has been renamed to `startKoin()`
* ContextAware Components can be configured for drop stratgey (onDestroy or onPause). Default method is onPause
* load assets/koin.properties if present

## [0.5.2]()

_Core_

* `getProperty()` can provide a default value if key property is missing 
* `provide()` can be use on a KoinContext directly, to declare on component

_Android_

* `applicationContext` has been renamed `androidApplication`


## [0.5.1]()

_Core_

* property value set does not allow Any? value
* starters builder have been moved to [test] & [android]
* removed base starter 

_Android_

* starter builder extension for Application class only

_Tests_

* starter builder extension for KoinTest class only

javadoc is now generated with Dokka


## [0.5.0]()

_Core_ 

* Koin now provide a **stand alone context** support to provide koin context in an agnostic way
* `startContext` function to start a Koin context
* property injection/resolution is now strict. Property injection is not nullable anymore
* `koinContext.removeProperties()` remove properties by their keys
* Simple logger to help logging/debugging

_Android_
* `startAndroidContext` function to start an Android Koin context
* `KoinContextAware` interfaces have been removed
* Koin/Android extensions greatly simplified
* packages have been moved - need reimport for AndroidModule, `by inject` and `by property`
* `ContextAwareActivity` & `ContextAwareFragment` are components to help you automatically release contexts - don't hesitate to do it manually

_Tests_

* Creation of 'org.koin:koin-test:' module, provide testing feature about Koin


## [0.4.0]()

Koin DSL has been reviewed to be more easy to understand, and allow easy writing of modules. No need anymore of several module classes, you can now include subcontexts in one module. You can now provide factory components, and bind is simpler to write.

The **dry run** feature, allows to run all of you modules in order to check if dependency graph is complete.

_DSL_

* replaced `declareContext{}` has been renamed `applicationContext{}`, and behind gives a better idea that you are describing your application context (the root context of your app)
* Updated `scope(){}`has been dropped for `context(){}` - *context* describes a sub context of your application, has a a name and can have also sub context itself (sub contexts are hierarchicals)
* Updated `bind{}` doesn't need lambda anymore to declare your bound class, but just the class in argument: `bind()`
* Added `provideFactory` is a DSL keyword to provide a factory definition instead of singleton


_Core_

* Updated `KoinContext.release()` is now called with context's name, no owner class instance
* Added `KoinContext.dryRun()` help to run your modules and check if every dependency can be injected

_Sample App_ & _Android_

* updated for the new core update

## [0.3.1]()

_Core_
* Kotlin 1.1.51
* Removed all loggers - rethinking of better Log/API

_Android_
* KoinContext function builders : newKoinContext & lazyKoinContext
* KoinContextAware interface now has a property, instead of a getter - better to overload

_Sample App_
* moved to folder koin-sample-app

## [0.3.0]()

_Core_
* Kotlin 1.1.50
* KoinContext / Context - simplified operator injection (removed all nullable inject/get)
* KoinContext.get() can make Injection by name
* Koin.build() can now build a list of *Module*

_DSL_
* Context.get() can now provide a definition with a name
* Context.get() operator have a bind property, to specify list of compatible interfaces to bind - is an alternative to bind {} operator

_Android_
* Merge android & android-support modules
* inject() can't be null anymore
* inject() can inject component by its name
* property() can do property injection - can be nullable data

_Sample App_
* Sample app (Weather demo application) has been reviewed to be more complete : Full MVP example, RxJava Scheduling, Unit Tests with partial modules loading & mocks 

## [0.2.x]()

**Koin & Koin-Android project has meen merged**

_DSL_

* Module/AndroiModule class must now give a `context()` function implementation, to return a Context object. The `declareContext` function unlock the Koin DSL to describe dependencies and injection:

```Kotlin
class MykModule : Module() {
    override fun context() = declareContext { ... }
}
```
* You can now [bind](#type-binding) additional types for provided definitions
```Kotlin
class MykModule : Module() {
    override fun context() = declareContext { 
        provide { Processor() } bind { ProcessorInterface::class}
    }
}
```

_Scope_

* You can declare/reuse scopes in your modules, with `scope {}` operator. See scopes section
* Release scope instances with `release()` on a KoinContext

```kotlin
class MainActivityModule : Module() {
    override fun context() =
            declareContext {
                // Scope MainActivity
                scope { MainActivity::class }
                // provided WeatherService
                provide { WeatherService(get()) }
            }
}
```

_Android_

* `KoinAwareContext` interface used to setup Android application (KoinApplication & KoinMultidexApplication are removed)

```Kotlin
class MainApplication : Application(), KoinContextAware {

     /**
     * Koin context
     */
    lateinit var context: KoinContext

    /**
     * KoinContextAware - Retrieve Koin Context
     */
    override fun getKoin(): KoinContext = context

    override fun onCreate() {
        super.onCreate()
        // insert Koin !
        context = Koin().init(this).build(MyModule()) 
        // ...
    }
}
```

* `by inject<>()` function operator to inject any dependency in any Activity or Fragment

```Kotlin
class MainActivity : AppCompatActivity() {

    // inject my WeatherService 
    val weatherService by inject<WeatherService>()
}
```

_Koin_

* Koin builder takes module instances (instead of module classes):

```Kotlin
// fill applicationContext for Koin context
val ctx = Koin().init(applicationContext).build(Module1(),Module2()...)
```
Internal rework for simpler use with Scopes:
* `Koin().build()` return KoinContext
* factory, stack operators have been removed, for the scope fatures
* delete/remove replcaed with `release()` Scope operation
* import is replaced with module instances load
* All reflection & kotlin-reflect code have been removed


