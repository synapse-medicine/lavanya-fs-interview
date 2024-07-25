# Code assessment

## Provided material

* A Java 21 Spring backend with a populated SQLite in memory database and endpoints to:
    * get a Patient by id
    * get the list of allergies for a given Patient
    * update a Patient's allergies
* A Node 20 React application with the following pages:
    * a home page that lists all patients
    * a page that displays the patient's information
    * an error page
* A Dockerfile and a Docker compose
* A basic CI using GitHub Actions

## Instructions

### Adding a new feature

Your first task will be to develop a new feature.
The goal is to add a search molecule component to the patient profile page.
This component will show a list of molecules that match the input text.
When the user selects a molecule from this list, it should be added to the patient allergies using the existing endpoint.

This gives the following user workflow:
1. The user clicks on the search field and search for a molecule name using free text.
2. The user selects the molecule from a list of results that match the search text.
3. The user chooses the molecule by clicking on it.
4. The chosen molecule is added to the patient’s allergy list.

You are free to develop the feature as you want. The UX and the design are taken into account, but you should not spend too much
time on it.

### Updating the CI

Your second task is to change the current GitHub Actions CI to build the Docker images provided in the repository.

### Going further

Your last task is to be critical :smile:.

The provided code is not representative of the expectations we have for production code (on purpose).
We instead encourage you to build the feature in a way that you think is the best.
We **don't** expect you to fix the current code though, just to add the new feature.

List what you would improve in the codebase (current or in your new feature) in the `Improvements section`.

## Improvements:

> List here what you would improve in the provided codebase as well as what you could improve in your new feature
> if you had more time

* In the Existing code base
     1. Implementing the repository classes using the JPA framework, so that the boilerplate code can be reduced.
     2. Implementing the Exception handling in backend classes like Controller and Repository etc, so that exceptions can be handled gracefully.
     3. Implementing the Unit test cases for backend classes like Controller and Repository.
* In the new feature implemented
     1. Implementing the repository classes using the JPA framework.
     2. Completed the search and github actions CI updation.
     3. Implemented the Exception handling and unit test cases, would like to implement more unit test cases in terms of negative test cases.
* Other improvements made
     1. Implemented logger for error and info mode. Would implement debug level logs if had more time.
     2. Used actuators and exposed the url for health checks.
     3. Dockerfile changes - using the non root user and exposing health checks.
     4. Updated the CI to build docker images, login to my dockerhub account repo and push the images there.
* Other improvements could be made in future
     1. Improvising the docker file to make it efficient by using non root user, multilayer building and using lower base images etc.
     2. Separating the configuration details of the application from the docker image and using configmaps if deploying in kubernetes environment so that we dont 
        have to build docker images every time a configuration change is to be made.
  
