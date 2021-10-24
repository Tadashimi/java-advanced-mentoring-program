You can run project use Maven tool by running spring-boot:run command.

By uncommenting annotation from different classes application will use different security configurations:
1. Homework2ApplicationSecurityConfigurationWithInMemoryUsers.java demonstrate in-memory stored users.
2. Homework2ApplicationSecurityConfigurationWithDBUsersWithNoPasswordEncoder.java use stored in DB users but doesn't use any encoder for password. So password is in the DB in naked form.
3. Homework2ApplicationSecurityConfigurationWithDBUsersWithPasswordEncoder.java use bCryptPasswordEncoder that allows save only password hash in DB.

There is an ability to use embedded DB h2 or postreSQL.
* To use h2 DB uncomment com.h2database dependency in pom.xml.
* To use postgre DB:
    * uncomment org.postgresql dependency in pom.xml
    * download and install postgreSQL from [PostgreSQL Downloads](https://www.postgresql.org/download/)
    * copy and run script to create and fill DB and tables (any of *_postgres_script_)
    * change the settings for DB in the application.properties

* Blocked users will be recovered using DB schedule job [intro in jobs scheduling for PostgreSQL](http://www.postgresonline.com/journal/archives/19-Setting-up-PgAgent-and-Doing-Scheduled-Backups.html).