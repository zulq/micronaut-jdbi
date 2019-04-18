# micronaut-jdbi
Demo app showing how to use https://micronaut.io with http://jdbi.org/.

**Features:**
* Custom JWT-Authentication
* User Registration
* Add/Delete/List Students

**Guide**

To use the demo app, do the following:

1. Run `students_db.sql` to create the DB and tables.
2. Change `your_username`, `your_pwd` and `your_port` in DbConfig.kt for the MySQL connection
3. Use gradle or import into Intellij and run.
4. Use *VSCode* with *REST Client* plugin to open `tests.http` and off you go.
