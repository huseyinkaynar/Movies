# Movies
This is a spring-boot project for a movies application

## Used Technolojies

 -  Java 11
  - Spring Boot
  - JPA Hibernate
  - PostgreSQL
  - Maven
  
  ### Structure of Spring Boot Project
```bash
├── MovieApplication.java
├── configuration
│   └── cors
│      ├── CorsConfig.java
│   └── security
│      ├── WebSecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── ExceptionHelper.java
│   ├── MovieController.java
│   ├── UserController.java
├── model
│ └── data
│   ├──AuthData.java
│   ├──ErrorObject.java
│   ├──MovieData.java
│   ├──ServiceResponseData.java
│   ├──UserData.java
│ └── entity
│     └── movie
│        ├── MovieModel.java
│     └── security
│        ├── RoleModel.java
│        ├── UserModel.java
│ └── enums
│ 	├──Erole.java
│ 	├──ProcessStatus.java
│ └── jwt
│ 	├──AuthEntryPointJwt.java
│ 	├──AuthTokenFilter.java
│ 	├──JwtUtils.java
├── repository
│   ├── MovieRepository.java
│   ├── RoleRepository.java
│   ├── UserRepository.java
└── service
│ └── exception
│     └── model
│        ├── ModelNotFoundException.java
│        ├── ModelRemoveException.java
│        ├── ModelSaveException.java
│        ├── UserException.java
│ └── impl
│     ├── AuthServiceImp.java
│     ├── MovieServiceImp.java
│     ├── UserDetailServiceImp.java
│     ├── UserDetailsImp.java
│     ├── UserServiceImp.java
│   ├── AuthService.java
│   ├── MovieService.java
│   ├── UserService.java
 
```
### How Using the System 
--------

### External Service Used

I used a service that lists movies. The service I use is [TMDB](https://www.themoviedb.org/)(The Movie Db).

### Movies 
  - You can get movies from services and save database with requests like this:
```sh
   @GetMapping("save-from-external-service")
    public ServiceResponseData saveMoviesFromExternal() {
        movieService.saveMovieFromExternalService();
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;
    }
```

  - You can get movies list with requests like this:
```sh
   @GetMapping
    public ServiceResponseData getMovies() {
        var movieData = movieService.getAllMovies();
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(movieData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;
    }
```

  - You can get movie with id requests like this:
```sh
    @GetMapping("/{id}")
    public ServiceResponseData getMovie(@PathVariable long id) {
        var movieData = movieService.getMovieById(id);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(movieData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
```
  - You can update movie with requests like this:
```sh
  @PutMapping("/{id}")
    public ServiceResponseData update(@PathVariable long id, @RequestBody MovieData movieData) {
        movieService.update(id, movieData);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;
    }
```
  - You can delete movie with requests like this:
```sh
  @DeleteMapping("/{id}")
    public ServiceResponseData remove(@PathVariable long id) {
        movieService.remove(id);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
```

### Auth
  - You can sigin with requests like this:
```sh
  @PostMapping("/signin")
    public AuthData authenticateUser(@Valid @RequestBody AuthData authData) {
        return authService.authenticate(authData);
    }
```
  - You can sigin with requests like this:
```sh
  @PostMapping("/signup")
    public ServiceResponseData registerUser(@Valid @RequestBody AuthData authData) {
        authService.register(authData);
        var responseData = new ServiceResponseData();
        responseData.setStatus(ProcessStatus.SUCCESS);
        return responseData;
    }
```
### User
  - You can get users with requests like this:
```sh
 @GetMapping
    public ServiceResponseData getUsers() {
        var userData = userService.getAllUsers();
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(userData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
```
  - You can get user with id requests like this:
```sh
   @GetMapping("/{id}")
    public ServiceResponseData getMovie(@PathVariable long id) {
        var userData = userService.getUserById(id);
        var serviceResponseData = new ServiceResponseData();
        serviceResponseData.setData(userData);
        serviceResponseData.setStatus(ProcessStatus.SUCCESS);
        return serviceResponseData;

    }
```
### Postman Requests    
--------
##### GET http://localhost:8080/api/movies/save-from-external-service
```bash
{
    "status": "SUCCESS",
    "errorMessage": null,
    "errorMessageDetail": null,
    "data": null
}
```
##### GET http://localhost:8080/api/movies
```bash
{
    "status": "SUCCESS",
    "errorMessage": null,
    "errorMessageDetail": null,
    "data": [
        {
            "id": 7,
            "title": "The Simpsons in Plusaversary",
            "release_date": "2021-11-12",
            "original_title": "The Simpsons in Plusaversary",
            "original_language": "en",
            "popularity": 1861,
            "vote_average": 6,
            "vote_count": 108
        },
        {
            "id": 8,
            "title": "Ciao Alberto",
            "release_date": "2021-11-12",
            "original_title": "Ciao Alberto",
            "original_language": "en",
            "popularity": 1794,
            "vote_average": 7,
            "vote_count": 195
        },
        {
            "id": 10,
            "title": "Amina",
            "release_date": "2021-11-04",
            "original_title": "Amina",
            "original_language": "en",
            "popularity": 1593,
            "vote_average": 7,
            "vote_count": 26
        }
             ]
 }
```
##### GET http://localhost:8080/api/movies/7
```bash
{
    "status": "SUCCESS",
    "errorMessage": null,
    "errorMessageDetail": null,
    "data": {
        "id": 7,
        "title": "The Simpsons in Plusaversary",
        "release_date": "2021-11-12",
        "original_title": "The Simpsons in Plusaversary",
        "original_language": "en",
        "popularity": 1861,
        "vote_average": 6,
        "vote_count": 108
    }
}
```
##### GET http://localhost:8080/api/users
```bash
{
    "status": "SUCCESS",
    "errorMessage": null,
    "errorMessageDetail": null,
    "data": [
        {
            "id": 1,
            "username": "huseyin",
            "email": "hk@deneme.com",
            "password": "$2a$10$Ih1KEq/ulipSvEprQxAb3OZeLoO1KehTmcTxHk/OivgEmSwzhLkVy",
            "roles": [
                {
                    "id": 2,
                    "name": "ROLE_MODERATOR"
                },
                {
                    "id": 1,
                    "name": "ROLE_USER"
                },
                {
                    "id": 3,
                    "name": "ROLE_ADMIN"
                }
            ]
        },
        {
            "id": 2,
            "username": "huseyin2",
            "email": "hk2@deneme.com",
            "password": "$2a$10$u.EOKOAbiufiEH3/9a9zruJH/oVNtCX5PW.SGvntqYBc7bRE./ZZe",
            "roles": [
                {
                    "id": 3,
                    "name": "ROLE_ADMIN"
                }
            ]
        },
        {
            "id": 3,
            "username": "huseyin3",
            "email": "hk3@deneme.com",
            "password": "$2a$10$yamIKhcqt9DOqo64N85FvOvZoecLB3s9.88ALs1YLUgwYe3QhQXjm",
            "roles": [
                {
                    "id": 1,
                    "name": "ROLE_USER"
                }
            ]
        }
        
    ]
}
```
###### Exception Responses
Services will return exception response like this
```bash
{
    "status": "ERROR",
    "errorMessage": "",
    "errorMessageDetail": "",
    "data": null
}
```
##### POST http://localhost:8080/api/auth/signin
###### Request
```bash
{
    "username":"huseyin",
     "password":"123456"
}
```
###### Response
```bash
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJodXNleWluIiwiaWF0IjoxNjM4MTk2NDIzLCJleHAiOjE2MzgxOTcyODd9.MKxj05k2u7HptSipDbFIOw5kkF_apXWmKXjLBtjJ5GMVQRNUD8i12iMzWmCFeTJ-UAuPfJWpc1B_HInyvTdibA",
    "id": 1,
    "username": "huseyin",
    "password": "$2a$10$Ih1KEq/ulipSvEprQxAb3OZeLoO1KehTmcTxHk/OivgEmSwzhLkVy",
    "email": "hk@deneme.com",
    "roles": [
        "ROLE_USER",
        "ROLE_MODERATOR",
        "ROLE_ADMIN"
    ]
}
```
###### Exception Response
```bash
{
    "status": "ERROR",
    "errorMessage": "BadCredentialsException: Bad credentials",
    "errorMessageDetail": "org.springframework.security.authentication.BadCredentialsException: Bad credentials",
    "data": null
}
```
##### POST http://localhost:8080/api/auth/signup
###### Request
```bash
{
    "username":"admin",
    "email":"admin@deneme.com",
    "password":"123456",
    "roles":["user","admin","mod"]
}
```
###### Response
```bash
{
    "status": "SUCCESS",
    "errorMessage": null,
    "errorMessageDetail": null,
    "data": null
}
```
###### Exception Response
```bash
{
    "status": "ERROR",
    "errorMessage": "UserException: Username is already taken!",
    "errorMessageDetail": "com.koctas.movie.service.exception.model.UserException: Username is already taken!",
    "data": null
}
```
### Authorization Notes 
--------
Users have 3 different roles in this project.
  - ROLE_ADMIN:Users with this role will be able to access the users and movies services
  - ROLE_MODERATOR:Users with this role will be able to list and edit movies.
  - ROLE_USER:Users with this role will be able to list movies.
  
### Frontend Notes
- If the logged in user is admin: she/he will see the users and movies menu.
- If the logged in user is user or moderator: she/he will see the movies menu.

### Logging Notes

Spring logback was used for logging in the application.
  
