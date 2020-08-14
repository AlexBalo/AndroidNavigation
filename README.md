# AndroidNavigation
This is a repository that I used as a playground to test Android Architecture components and Single Activity Navigation.

The application is composed of 1 activity that follows MVVM architecture and data is fetched remotely (using the [Foursquare venues APIs](https://developer.foursquare.com/docs/api-reference/venues/search/)) and temporarily cached locally without persistence.

The activity and fragments are controlled by VieModels that handle the business logic and notify the Views about changes in a reactive way using LiveData. Network operations and data retrieval from local storage are also handled reactively using RxJava.

The application allows to:
1. Display restaurants around the user location
2. Display restaurants around the new location when the map is panned by the user and centered elsewhere
3. Access the details of a restaurant by showing a new overlaying screen. This is possible after clicking on the restaurant marker and then on the bubble that appears on top.

**Note:** The detail page loads additional information about the restaurant but those information are available only for premium users of the APIs and they have a [rate limit](https://developer.foursquare.com/docs/places-api/rate-limits/), so you can expect the detail screen not to show all the information after loading it several times.