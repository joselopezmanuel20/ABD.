import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { StudentService } from '../../services/student.service';

@Component({
  selector: 'app-students',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './students.html',
  styleUrls: ['./students.css']
})
export class Students implements OnInit {

  students: any[] = [];

  constructor(private studentService: StudentService) {}

  ngOnInit(): void {
    this.studentService.getAll().subscribe({
      next: (data) => {
        console.log(data);
        this.students = data;
      },
      error: (err) => console.error(err)
    });
  }

editStudent(student: any) {

  const newName =
    prompt('Nombre:', student.name);

  const newLastName =
    prompt('Apellido:', student.lastName);

  const newEmail =
    prompt('Email:', student.email);

  const newCourse =
    prompt('Curso ID:', student.courseId);

  if (
    newName &&
    newLastName &&
    newEmail &&
    newCourse
  ) {

    const updatedStudent = {
      name: newName,
      lastName: newLastName,
      email: newEmail,
      courseId: Number(newCourse)
    };

    this.studentService
      .update(student.id, updatedStudent)
      .subscribe({
        next: () => {

          student.name = newName;
          student.lastName = newLastName;
          student.email = newEmail;
          student.courseId = Number(newCourse);

          alert('Estudiante actualizado');
        },
      });
  }
}
createStudent() {

  const name = prompt('Nombre:');
  const lastName = prompt('Apellido:');
  const email = prompt('Email:');
  const courseId = prompt('ID Curso:');

  if(name && lastName && email && courseId){

    const student = {
      name,
      lastName,
      email,
      courseId: Number(courseId)
    };

    this.studentService.create(student)
      .subscribe({

        next: () => {

          alert('Estudiante creado');

          this.studentService.getAll().subscribe(data => {
            this.students = data;
          });

        },

        error: (err) => {
          console.error(err);
          alert('Error al crear estudiante');
        }

      });

  }
}
}