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


  async getTasks() {
    const url = 'http://localhost:8080/api/tasks';
    const response = await fetch(url,{
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      }
    });
    return await response.json() ?? undefined;
  }

  async addTask(task:any) {
    const url = 'http://localhost:8080/api/tasks';
    console.log('task',task);
    const response = await fetch(url,{
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(task)
    });
    return await response.json() ?? undefined;
  }
}
