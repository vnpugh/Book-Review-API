# Book Review API :open_book::feather:
<h2>Introduction:books:</h2>
<p>Are you ready to embark on a journey to learn about the powerful world of APIs? An API, or Application 
Programming Interface, is a collection of protocols, routines, and tools that enable software applications 
to communicate and exchange information with one another. The goal of this mini project is to build a 
robust backend using Spring Boot and its various modules to create a Book Review API. The building approach
for this project is monolithic, utilizing PostgreSQL as the database and deploying the app on the Tomcat server.</p>
<h2>Installation</h2>
<p>First, you will need to install the following:</p>
<ol>
<li> First, download IntelliJ IDE if you don't have it on your computer. </li>
<li> Next, download PostgreSQL and create a database called "bookapi". </li>
<li> Then, download Postman to test the API. </li>
<li> Also, make sure to include the Spring dependencies in your pom.xml file.</li>
<li> After downloading your tools, clone this repository.</li>
<li> Open the files in IntelliJ and run the application.</li>
</ol>
<h2>Technical Requirements</h2>
<p>For this project, the following technologies were included:</p>
<ul>
<li>The PostgreSQL database includes five models: Author, Book, Review, User, and User Profile:white_check_mark:</li>
<li>The environment settings are set up using Spring Profiles:white_check_mark:</li>
<li>A combination of Spring Security and JWT tokens used to authenticate and personalize the API endpoints:white_check_mark:</li>
<li>API endpoints and their functionality included to complete CRUD operations (create, read, update, and delete):white_check_mark:</li>
<li>Additional API endpoints are included to perform CRUD tasks based on the business logic and user stories:white_check_mark:</li>
<li>CRUD routes built on rest conventions are provided:white_check_mark:</li>
<li>Various exception handles are included to handle unexpected occurrences with the appropriate message:white_check_mark:</li>
<li>The controllers and services are separate to conform to the MVC design pattern:white_check_mark:</li>
</ul>
<h1>The Planning Process🖋️</h1>
<p> To keep track of the project's progress, I created a Kanban board in Github projects. The user stories were
added to the board and broken down into smaller tasks. The tasks were then assigned to the appropriate
milestone and due date. This allowed me to also map out the API endpoints and the database models. </p>
<img align="center" width="900" height="700" src="images/kanban.png" alt="kanban">

<h2>User Stories:busts_in_silhouette:</h2>
<p>The following user stories included:</p>
<ul>
<li>As a user, I want to search for books and book reviews by using various parameters.</li>
<li>As a user, I want to retrieve a list of book reviews by other readers like myself.</li>
<li>As a user, I want to retrieve a list of all book review ratings.</li>
<li>As a user, I want to securely find another fellow reader by their email address.</li>
<li>As a user, I want to securely update a review that I;ve previously written if I make a mistake.</li>
<li>As a user, I want to securely update my user profile and information.</li>
<li>As a user, I want to securely log in to my account.</li>
<li>As a user, I want to securely register another profile for a family member.</li>
<li>As a user, I want to securely delete a previously written book review when I'm logged in the app.</li>
</ul>
<h2>API Endpoints</h2>
<center><img align="center" width="700" height="500" src="images/api.png" alt="api"></center>


