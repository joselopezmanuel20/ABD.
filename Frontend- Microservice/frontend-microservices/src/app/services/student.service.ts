import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from '../models/student';

@Injectable({
  providedIn: 'root'
})
export class StudentService {

private api = 'http://localhost:8090/api/student';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Student[]> {
    return this.http.get<Student[]>(
      `${this.api}/all`
    );
  }

update(id: number, student: any) {
  return this.http.put(
    `${this.api}/update/${id}`,
    student
  );
}
create(student: any) {
  return this.http.post(
    `${this.api}/create`,
    student
  );
}
}