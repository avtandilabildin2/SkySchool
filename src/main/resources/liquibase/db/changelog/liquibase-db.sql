-- liquibase formatted sql

--changeset postgres:1
create index find_student_by_name on  student(name);
--changeset postgres:2
create index faculty_by_title_and_color on faculty(name,color);