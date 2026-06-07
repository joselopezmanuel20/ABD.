import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Course } from '../models/course';

@Injectable({
  providedIn: 'root'
})
export class CourseService {

private api = 'http://localhost:9090/api/course';
  constructor(private http: HttpClient) {}

  getAll(): Observable<Course[]> {
    return this.http.get<Course[]>(
      `${this.api}/all`
    );
  }
   update(id: number, course: any) {
  return this.http.put(
    `${this.api}/update/${id}`,
    course
  );
}
create(course: any) {
  return this.http.post(
    `${this.api}/create`,
    course
  );
}
}