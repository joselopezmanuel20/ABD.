import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'app-courses',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './courses.html',
  styleUrls: ['./courses.css']
})
export class Courses implements OnInit {

  courses: any[] = [];

  constructor(private courseService: CourseService) {}

  ngOnInit(): void {
    this.courseService.getAll().subscribe({
      next: (data) => {
        console.log(data);
    
      this.courses = data.sort(
        (a, b) => (a.id ?? 0) - (b.id ?? 0)
      );

    },

    error: (err) => console.error(err)

  });
}
  
  editCourse(course: any) {

  const newName = prompt('Curso:', course.name);
  const newTeacher = prompt('Profesor:', course.teacher);

  if (newName && newTeacher) {

    const updatedCourse = {
      name: newName,
      teacher: newTeacher
    };

    this.courseService
      .update(course.id, updatedCourse)
      .subscribe({

        next: (resp) => {
          console.log('ACTUALIZADO:', resp);

          course.name = newName;
          course.teacher = newTeacher;

          alert('Curso actualizado');
        },

        error: (err) => {
          console.log('ERROR COMPLETO:', err);
          alert('Error al actualizar');
        }

      });
  }
}
createCourse() {

  const name = prompt('Nombre del curso:');
  const teacher = prompt('Nombre del profesor:');

  if(name && teacher){

    const course = {
      name,
      teacher
    };

    this.courseService.create(course)
      .subscribe({

        next: () => {

          alert('Curso creado correctamente');

          this.courseService.getAll()
            .subscribe(data => {

              this.courses = data.sort(
                (a, b) => (a.id ?? 0) - (b.id ?? 0)
              );

            });

        },

        error: (err) => {

          console.error(err);
          alert('Error al crear curso');

        }

      });

  }
}
}