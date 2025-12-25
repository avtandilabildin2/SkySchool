select s.name,s.age,f.name as faculty_name
from student s join faculty  f on s.faculty_id = f.id;

select s.name,s.age from student s join avatar a on s.id = a.student_id;