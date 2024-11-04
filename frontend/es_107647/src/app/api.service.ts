import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  isPlatformBrowser() {
    return isPlatformBrowser(this.platformId);
  }

  private getAuthToken(): string | null {
    if (isPlatformBrowser(this.platformId)) {
      return localStorage.getItem('token');
    } else {
      return null;
    }
  }

  private getHeaders(withAuth: boolean = true,isMultipart: boolean = false): Headers {
    const headers = new Headers({
    });
    if (!isMultipart) {
      headers.append('Content-Type', 'application/json');
    }
    if (withAuth) {
      const token = this.getAuthToken();
      if (token) {

        headers.append('Authorization', `Bearer ${token}`);
      }
    }
    return headers;
  }



  async addTask(task:any) {
    const url = 'http://localhost:8080/api/tasks';
    console.log('task',task);
    if (task.deadline instanceof Date) {
      task.deadline = task.deadline.toISOString().split('T')[0];
    }
    const response = await fetch(url,{
      method: 'POST',
      headers: this.getHeaders(true),
      body: JSON.stringify(task)
    });
    return await response.json() ?? undefined;
  }

  async getLowPriorityTasks() {
    const url = 'http://localhost:8080/api/tasks/priority/low';
    const response = await fetch(url,{
      method: 'GET',
      headers:this.getHeaders(true)
    });
    return await response.json() ?? undefined;
  }

  async getHighPriorityTasks() {
    const url = 'http://localhost:8080/api/tasks/priority/high';
    const response = await fetch(url,{
      method: 'GET',
      headers: this.getHeaders(true)
    });
    return await response.json() ?? undefined;
  }

  async getMediumPriorityTasks() {
    const url = 'http://localhost:8080/api/tasks/priority/medium';
    const response = await fetch(url,{
      method: 'GET',
      headers: this.getHeaders(true)
    });
    return await response.json() ?? undefined;
  }

  async gettoken(code: string) {
    // Construct the GET request URL with the code parameter
    const url = `http://localhost:8080/api/token/get?code=${code}`;


    // Make the GET request
    const response = await fetch(url, {
      method: 'GET',
      headers: this.getHeaders(true)
    });

    const data = await response.json();
    console.log('Token:', data.id_token);

    return data;
  }

  async deleteTask(id: string) {
    const url = `http://localhost:8080/api/tasks/${id}`;
    const response = await fetch(url, {
      method: 'DELETE',
      headers: this.getHeaders(true)
    });
    return response.statusText ?? undefined;
  }

  async updateTask(task: any) {
    const url = `http://localhost:8080/api/tasks/${task.id}`;
    const response = await fetch(url, {
      method: 'PUT',
      headers: this.getHeaders(true),
      body: JSON.stringify(task)
    });
    return await response.json() ?? undefined;
  }
}
