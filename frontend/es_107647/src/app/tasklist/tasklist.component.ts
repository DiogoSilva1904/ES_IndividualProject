import {Component, OnInit} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import {TaskModalComponent} from "../task-modal/task-modal.component";
import {ApiService} from "../api.service";
import {DatePipe, NgForOf} from "@angular/common";

@Component({
  selector: 'app-tasklist',
  standalone: true,
  imports: [
    NgForOf,
    DatePipe
  ],
  templateUrl: './tasklist.component.html',
  styleUrl: './tasklist.component.css'
})
export class TasklistComponent implements OnInit{
  tasks: any[] = [];
  constructor(private dialog: MatDialog,private apiService:ApiService) {}

  ngOnInit(): void {
    this.loadTasks();
  }

  // Load tasks from API
  async loadTasks() {
    if (this.apiService.isPlatformBrowser()) {
      try {
        const tasks = await this.apiService.getTasks();
        this.tasks = tasks ?? [];
      } catch (error) {
        console.error('Error loading tasks:', error);
      }
    }
  }

  openModal() {
    const dialogRef = this.dialog.open(TaskModalComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
       this.loadTasks();
      }
    });
  }

}
