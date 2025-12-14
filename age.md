1)подхожу компьютеру брата и договариваюсь с ним если брат попросит ексел файл 
выведи список студентов в котором будет один студент:
when(studentRepository.findAll().thenReturn(List.of(student));

2)абиль подходит к брату и просит отдать список студентов с возрастом 18 лет
mockMvc.perform(get("/students/filter")
.param("age", "18"))
.andExpect(status().isOk())
.andExpect(jsonPath("$[0].name").value("Harry"));

3)брат подходит к компу и открывает ексел файл в котором видит всех
студентов
Collection<Student> ages=studentRepository.findAll();
