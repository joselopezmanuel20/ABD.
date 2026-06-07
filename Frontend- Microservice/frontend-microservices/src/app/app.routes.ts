import { Routes } from '@angular/router';
import { Students } from './pages/students/students';
import { Courses } from './pages/courses/courses';
import { Home } from './pages/home/home';


export const routes: Routes = [
  {
    path: '',
    redirectTo: 'students',
    pathMatch: 'full'
  },

  {
    path: 'students',
    component: Students
  },

  {
    path: 'courses',
    component: Courses
  },

  {
    path: '',
    component: Home
  },
  
];