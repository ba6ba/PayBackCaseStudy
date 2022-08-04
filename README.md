# PayBack Case Study

## Objective
This challenge has the goal to give us an impression of your coding skills.

## API Gateway
Pixabay api service that helps in fetching data from their servers.[Pixabay Images](
https://pixabay.com/api/).

## Structure
###### Application have two modules; app and network
App Module contains following packages:
- common
- core
- database
- images
- metadata

Network Module contains following:
- OkHttpClientProvider.kt
- NetworkDataSource.kt
- RetrofitProvider.kt
- Helper classes for network stuff

###### Images module follows clean architecture with packages for listing and details screens:
- data (data related stuff)
- domain (business logic)
- presentation (ui related stuff)

## Data Layer
- Service
- Response POJOs
- Ui Models
- Repository
- Network datasource
- Local datasource

## Presentation Layer
- Bindings
- Fragment
- ViewModel
- Adapter + ViewHolder
- LoadState Adapter + ViewHolder

## Domain Layer
- UseCases
- Entities

## DI Layer
- Modules

## DI
Hilt is used for dependency injection, following classes are used for DI related stuff.
- ApplicationModule
- ImagesModule
- MetadataModule
- NetworkModule
- DatabaseModule
- UseCaseModule

## Testing
This case study includes unit-tests for providers, managers, use-case, repository, viewModels.
- Mockito
- JUnit

## Explanation
This case-study is responsible to show images from pixabay public API. On app start up there's a pre-filled 
query `fruits` against which application queries data from server and show all the relevant images in 
Grid view. Pagination is implemented using Paging3 library. Listing screen has multiple states:
Loading: shimmer displays until data is fetching from remote.
Failure: error screen shows up that indicates there's some error with lottie animation and CTA to try again.
Success: RecyclerView with Grid layout manager to show fetched images either from remote/database.
For offline/caching purpose, this case-study is using Room database to persist images and metadata
to show the fetched data even there's no internet to have smooth experience for the user.

Also for caching, logic is implemented that whatever data has fetched gets persisted in DB along-with 
the number of pages and last used query so if user comes back after killing the application then 
there's a possibility of two cases:
i) Internet available; app will fetch data from DB until the last fetched page for last used query 
and would not send request to remote to fetch data and if the DB returned data for all fetched pages
then app will request remote to fetch new data from the very next page of last fetched page.
ii) No internet available; app will fetch data from DB until there's no data left and data will get
cleared if user swipes to refresh the screen OR enter new query to get data in both offline and online cases.

On tap of grid cell, a confirmation pop-up gets displayed which asked confirmation from user to show
details of the image and on tap of YES a new screen with detailed layout gets displayed. From detail
screen user can go back by tapping on back button on top. Below that there's a card that's holding
a high quality image of the tapped cell with further details; user name, likes, comments, downloads
and added tags.

## App Glimpse
![Images in Day mode](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/day_mode_listing.png)
![Images in Night mode](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/night_mode_listing.png)
![Error Screen](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/error_screen.png)
![Confirmation pop-up Screen](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/confirmation_pop_up.png)
![Details Screen](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/details_screen.png)
![Details Screen Night](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/details_screen_night.png)
![Placeholder](https://github.com/ba6ba/PayBackCaseStudy/blob/master/docs/assets/placeholder.png)
