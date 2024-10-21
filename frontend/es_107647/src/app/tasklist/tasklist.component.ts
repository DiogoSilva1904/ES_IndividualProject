import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TaskModalComponent } from '../task-modal/task-modal.component';
import { ApiService } from '../api.service';
import { DatePipe, NgForOf, NgIf } from '@angular/common';

@Component({
  selector: 'app-tasklist',
  standalone: true,
  imports: [
    NgForOf,
    DatePipe,
    NgIf,
  ],
  templateUrl: './tasklist.component.html',
  styleUrls: ['./tasklist.component.css'],
})
export class TasklistComponent implements OnInit {
  tasks: any[] = [];
  lowPriorityTasks: any[] = [];
  mediumPriorityTasks: any[] = [];
  highPriorityTasks: any[] = [];

  constructor(private dialog: MatDialog, private apiService: ApiService) {}

  ngOnInit(): void {
    this.loadTasksByPriority();
  }

  // Load tasks from API grouped by priority
  async loadTasksByPriority() {
    if (this.apiService.isPlatformBrowser()) {
      try {
        const lowPriorityTasks = await this.apiService.getLowPriorityTasks();
        const mediumPriorityTasks = await this.apiService.getMediumPriorityTasks();
        const highPriorityTasks = await this.apiService.getHighPriorityTasks();
        this.highPriorityTasks = highPriorityTasks ?? [];
        this.mediumPriorityTasks = mediumPriorityTasks ?? [];
        this.lowPriorityTasks = lowPriorityTasks ?? [];
        //const lowPriorityTasks = await this.apiService.getTasks();
        //const mediumPriorityTasks = await this.apiService.getTasks();
        //const highPriorityTasks = await this.apiService.getTasks();

        this.tasks = [
          ...lowPriorityTasks,
          ...mediumPriorityTasks,
          ...highPriorityTasks,
        ];
      } catch (error) {
        console.error('Error loading tasks:', error);
      }
    }
  }

  /*async loadTasks() {
    if (this.apiService.isPlatformBrowser()) {
      try {
        const tasks = await this.apiService.getTasks();
        this.tasks = tasks ?? [];
      } catch (error) {
        console.error('Error loading tasks:', error);
      }
    }
    // Load tasks from API

    // Assume this.tasks is populated
    this.lowPriorityTasks = this.tasks.filter(task => task.priority === 'LOW');
    this.mediumPriorityTasks = this.tasks.filter(task => task.priority === 'MEDIUM');
    this.highPriorityTasks = this.tasks.filter(task => task.priority === 'HIGH');
  }*/

  openModal() {
    const dialogRef = this.dialog.open(TaskModalComponent, {
      width: '400px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.loadTasksByPriority();
      }
    });
  }
}
