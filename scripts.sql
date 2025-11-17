CREATE USER student WITH PASSWORD 'chocolatefrog';
GRANT ALL PRIVILEGES ON DATABASE hogwarts TO student;
select * from student;
select * from student where age between 10 and 20;
select s.name from student s ;
select * from student where name like '%O%';
select * from student where age<id;
select * from student order by age desc;