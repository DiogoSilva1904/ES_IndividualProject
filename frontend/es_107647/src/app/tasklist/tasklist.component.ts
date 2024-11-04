import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TaskModalComponent } from '../task-modal/task-modal.component';
import { ApiService } from '../api.service';
import {DatePipe, NgClass, NgForOf, NgIf} from '@angular/common';
import {TaskDetailsComponent} from "../task-details/task-details.component";

@Component({
  selector: 'app-tasklist',
  standalone: true,
  imports: [
    NgForOf,
    DatePipe,
    NgIf,
    NgClass,
  ],
  templateUrl: './tasklist.component.html',
  styleUrls: ['./tasklist.component.css'],
})
export class TasklistComponent implements OnInit {
  tasks: any[] = [];
  lowPriorityTasks: any[] = [];
  mediumPriorityTasks: any[] = [];
  highPriorityTasks: any[] = [];

  filteredLowPriorityTasks: any[] = [];
  filteredMediumPriorityTasks: any[] = [];
  filteredHighPriorityTasks: any[] = [];

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

        this.filteredLowPriorityTasks = this.lowPriorityTasks;
        this.filteredMediumPriorityTasks = this.mediumPriorityTasks;
        this.filteredHighPriorityTasks = this.highPriorityTasks;

        this.tasks = [
          ...this.filteredLowPriorityTasks,
          ...this.filteredMediumPriorityTasks,
          ...this.filteredHighPriorityTasks,
        ];
      } catch (error) {
        console.error('Error loading tasks:', error);
      }
    }
  }

  onSortChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement; // Cast to HTMLSelectElement
    const selectedValue = selectElement.value; // Get the selected value
    console.log('Selected value:', selectedValue); // Debugging
    this.sortTasks(selectedValue); // Pass the selected value to the sorting function
  }

  onFilterChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement; // Cast to HTMLInputElement
    const value = selectElement.value; // Get the search text
    console.log('Select value', value); // Debugging
    this.filterCompletition(value); // Pass the search text to the filtering function
  }

  private sortTasks(option:string) {
    switch (option) {
      case 'date':
        this.tasks.sort((a, b) => new Date(a.creation_date).getTime() - new Date(b.creation_date).getTime());
        break;
      case 'deadline':
        this.tasks.sort((a, b) => new Date(a.deadline).getTime() - new Date(b.deadline).getTime());
        break;
      case 'completion':
        this.tasks.sort((a, b) => (a.completion_status === 'Completed' ? 1 : 0) - (b.completion_status === 'Completed' ? 1 : 0));
        break;
      default:
        console.log('No sorting applied.'); // For debugging
        break;
    }

    this.filteredLowPriorityTasks = this.tasks.filter(task => task.priority === 'LOW');
    this.filteredMediumPriorityTasks = this.tasks.filter(task => task.priority === 'MEDIUM');
    this.filteredHighPriorityTasks = this.tasks.filter(task => task.priority === 'HIGH');

    this.applySearchFilter();
  }
  private applySearchFilter() {
    const searchText = this.currentSearchText.toLowerCase(); // Store your current search text

    this.filteredLowPriorityTasks = this.filteredLowPriorityTasks.filter(task =>
      task?.category && task.category.toLowerCase().includes(searchText)
    );

    this.filteredMediumPriorityTasks = this.filteredMediumPriorityTasks.filter(task =>
      task?.category && task.category.toLowerCase().includes(searchText)
    );

    this.filteredHighPriorityTasks = this.filteredHighPriorityTasks.filter(task =>
      task?.category && task.category.toLowerCase().includes(searchText)
    );
  }

// Modify your filterResults method to save the current search text
  currentSearchText: string = '';

  filterCompletition(text: string) {
    if (!text) {
      this.filteredLowPriorityTasks = this.lowPriorityTasks;
      this.filteredMediumPriorityTasks = this.mediumPriorityTasks;
      this.filteredHighPriorityTasks = this.highPriorityTasks;
      return;
    }
    this.filteredLowPriorityTasks = this.lowPriorityTasks.filter(task => task.completion_status === text);
    this.filteredMediumPriorityTasks = this.mediumPriorityTasks.filter(task => task.completion_status === text);
    this.filteredHighPriorityTasks = this.highPriorityTasks.filter(task => task.completion_status === text);
  }

  filterResults(text: string) {
    console.log('Filtering tasks by:', text); // Debugging
    this.currentSearchText = text; // Save the current search text
    if (!text) {
      this.filteredLowPriorityTasks = this.lowPriorityTasks;
      this.filteredMediumPriorityTasks = this.mediumPriorityTasks;
      this.filteredHighPriorityTasks = this.highPriorityTasks;
      return;
    }

    const lowerCaseText = text.toLowerCase(); // Store the lower case version of the text

    this.filteredLowPriorityTasks = this.lowPriorityTasks.filter(task =>
      task?.category && task.category.toLowerCase().includes(lowerCaseText)
    );

    this.filteredMediumPriorityTasks = this.mediumPriorityTasks.filter(task =>
      task?.category && task.category.toLowerCase().includes(lowerCaseText)
    );

    this.filteredHighPriorityTasks = this.highPriorityTasks.filter(task =>
      task?.category && task.category.toLowerCase().includes(lowerCaseText)
    );
  }


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

  viewTask(task: any) {
    const dialogRef = this.dialog.open(TaskDetailsComponent, {
      width: '400px',
      data: { task }
    });

    dialogRef.afterClosed().subscribe(async (result) => {
      console.log("result",result);
      if (result) {
        if (result.action === 'save') {
          await this.apiService.updateTask(result.task);
        } else if (result.action === 'delete') {
          console.log("enter delete");
          await this.apiService.deleteTask(result.task.id);
        }
        this.loadTasksByPriority();  // Refresh tasks
      }
    });
  }
}
