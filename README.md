## GET /

Enter a username and as many todo tasks

## POST /task

Insert todo tasks into a MySQL database table (named task), with a foreign key referencing the username's primary key in another table in the same database (named user)

- if the username does not already exist in the user table, automatically create a record of the new username and insert into the user table, before proceeding to insert todo tasks
- usernames cannot have spaces
