
alter table student add constraint student_age check ( age>=16 );
alter table student alter  column name set not null,
                    add constraint name_unique unique(name);
alter table faculty add constraint unique_name_color unique (name,color);
alter table student alter column age set  default 20;