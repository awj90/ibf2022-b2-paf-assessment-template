create database dbtask;

use dbtask;

create table user (
    user_id CHAR(8) NOT NULL,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(50),
    PRIMARY KEY(user_id),
    unique(user_id),
    constraint check_no_spaces
        check(username NOT LIKE '% %')
);

create table task (
    task_id INT auto_increment NOT NULL,
    description CHAR(255) NOT NULL,
    priority INT NOT NULL,
    due_date DATE NOT NULL,
    user_id CHAR(8) NOT NULL,
    PRIMARY KEY(task_id),
    unique(task_id),
    constraint check_priority
        check(priority between 1 and 3),
    constraint fk_user_id
        foreign key(user_id) 
        references user(user_id) 
);