package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.exceptions.LessThanSixException;
import ru.hogwarts.school.exceptions.StudentPrintException;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student){
        return studentService.createStudent(student);
    }
    @GetMapping("/find/{id}")
    public Student findStudent(@PathVariable Long id){
        return studentService.findStudent(id);
    }
    @PutMapping("/update/{id}")
    public Student updateStudent(@PathVariable Long id,@RequestBody Student student){
        return studentService.updateStudent(id,student);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
    @GetMapping("/filter")
    public Collection<Student> filterByAge(@RequestParam int age) {
        return studentService.filterByAge(age);
    }
    @GetMapping("/age-between")
    public Collection<Student> filterByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.filterByAgeBetween(min,max);
    }
    @GetMapping("/{id}/faculty")
    public Faculty getStudentFaculty(@PathVariable Long id) {
        return studentService.getStudentFaculty(id);
    }
    @GetMapping("/get-student-count")
    public Long getStudentCount() {
        return studentService.getStudentCount();
    }
    @GetMapping("/get-average-age")
    public Double getAverageAge() {
        return studentService.getAverageAge();
    }
    @GetMapping("/get-last-five-students")
    public Collection<Student> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }
    @GetMapping("/names-starting-with-a")
    public List<String> getStudentsNamesStartingWithA() {
        return studentService.getStudentsNamesStartingWithA();
    }
    @GetMapping("/average-age")
    public double getAverageStudentsAge() {
        return studentService.getAverageStudentsAge();
    }
    @GetMapping("/print-parallel")
    public void printParallel() throws InterruptedException {
        List<Student> students=studentService.getAllStudents();
        if(students.size()<6){
            throw new LessThanSixException("Студентов мало чем шесть!!!");
        }
        for (int i = 0; i <2 ; i++) {
            System.out.println(Thread.currentThread().getName()+" "+students.get(i).getName());
        }
        Thread thread1 = new Thread(()->{
            for (int i = 2; i <4 ; i++) {
                System.out.println(Thread.currentThread().getName()+" "+students.get(i).getName());
            }
        });
        Thread thread2 = new Thread(()->{
            for (int i = 4; i <6 ; i++) {
                System.out.println(Thread.currentThread().getName()+" "+students.get(i).getName());
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

    }
    private synchronized void printSynchronizedStudentsNames(Student student){
        try {

            Thread.sleep(50);

            System.out.println(
                    Thread.currentThread().getName() + " " + student.getName()
            );
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new StudentPrintException("Ошибка при выводе студента", e);
        }
    }

    @GetMapping("/print-synchronized")
    public void printSynchronized() throws InterruptedException {
        List<Student> students=studentService.getAllStudents();
        if(students.size()<6){
            throw new LessThanSixException("Студентов мало чем шесть!!!");
        }
        for (int i = 0; i <2 ; i++) {
            printSynchronizedStudentsNames(students.get(i));
        }
        Thread thread1 = new Thread(()->{
            for (int i = 2; i <4 ; i++) {
                printSynchronizedStudentsNames(students.get(i));

            }
        });
        Thread thread2 = new Thread(()->{
            for (int i = 4; i <6 ; i++) {
                printSynchronizedStudentsNames(students.get(i));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
    }


}
