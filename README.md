# Grazer Android

Basic android example showing login to a HTTP API and rendering one list of resources

## Features overview

When the app is launched, it opens one "Startup screen". This screen does not have a view currently, 
but could show the logo of the app while some startup operations are done.
At the moment, the screen is used only to check the login state of the user.

If the user is not logged in, the app will navigate to one screen letting the user log in.

If the user is logged in, the app will navigate to one screen where a list of users is fetched and
shown.

Once the user has logged in, the auth token will be persisted in local storage, making subsequent 
sessions go straight to the the content of the app without the need to log again.

## Architecture overview

Clean architecture (without interactors):

- separation in domain, data and presentation layers
- the domain layer (models, interfaces of the repositories) do not depend on the
  lower level APIs.
- mapping between layers:
    - network models are mapped to domain models before been passed to other layers
    - to simplify, domain models are passed straight away to to presentation layer. We could map these to UI models if needed.
- to simplify, the ViewModels receive the repositories directly - no interactor used.   

MVVM.

Async communication between ViewModels and the repos with RxJava2.
Views (Fragment) observe the ViewModels with LiveData. 
Dependency injection with Hilt.

### Data layer insights

Network communication done with Retrofit2.
Local storage of the auth token done with SharedPreferences.

The repositories typically possess data sources for the network and local storage. They are responsible
for saving data that has been fetched from the network in local storage. In this app, that is exemplified
in the Auth Repository, which saves the auth token obtained from the network data source (Retrofit) into
the local data source (SharePreferences)

I am not inspecting the "code" attribute found in the HTTP responses. I am just trusting the HTTP code 
of the HTTP layer. I assume that one HTTP 200 will imply one 200 in the JSON of the response. 
(By the way, I'm not sure the design of the API is the best in this regard)

## Testing

Repositories, VideModels and DataSources are tested with JVM tests. The mappers could
be tested easily (and they should) but I did not have the time.

Unfortunately, I have not been able to write any instrumentation tests, due to issues setting up
Hilt in the testing infrastructure and lack of time to investigate.

## Main TODOs

- support pagination
- check that the user is logged in the inner screens instead of just in the startup screen
- write Espresso tests
- Use a navigator instead of letting the `Activity` navigate on its own (lack of time)
- Persist list of users in local storage for offline mode.

## Libs/tech used:

- Kotlin
- Hilt for dependency injection
- Retrofit2 for HTTP communication
- LiveData and ViewModel from android JetPack
- RxJava2 for data flowing and async operation management
- Glide to download images (although they all resolve in 404 :-( )
- JUnit and mockito for unit testing.

Sergio Torres. sergiotorresdev@gmail.com