import { Component, OnInit,ChangeDetectionStrategy, } from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import { ApiService } from '../api.service'; // Adjust path as needed
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatError, MatFormField, MatHint, MatLabel} from "@angular/material/form-field";
import {
  MatDatepicker,
  MatDatepickerInput,
  MatDatepickerModule,
  MatDatepickerToggle
} from "@angular/material/datepicker";
import {MatOption, MatSelect} from "@angular/material/select";
import {NgIf} from "@angular/common";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button"; // Import FormBuilder and Validators
import { MatNativeDateModule } from '@angular/material/core';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {provideNativeDateAdapter} from '@angular/material/core';

@Component({
  selector: 'app-task-modal',
  standalone: true,
  templateUrl: './task-modal.component.html',
  providers: [provideNativeDateAdapter()],
  imports: [
    MatDialogContent,
    MatFormField,
    MatDatepickerToggle,
    MatDatepicker,
    MatDatepickerInput,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatSelect,
    MatOption,
    MatDialogActions,
    NgIf,
    MatInput,
    MatDialogTitle,
    MatButton,
    MatLabel,
    MatError,
    MatHint,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule
  ],
  styleUrls: ['./task-modal.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TaskModalComponent implements OnInit {
  taskForm!: FormGroup;  // Define a FormGroup for the task form

  constructor(
    public dialogRef: MatDialogRef<TaskModalComponent>,
    private apiService: ApiService,
    private fb: FormBuilder  // Inject FormBuilder
  ) {}

  ngOnInit(): void {
    const formattedCreationDate = new Date().toISOString().split('T')[0]; // Gets only the date part
    // Initialize the task form
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      priority: ['LOW', Validators.required],
      deadline: [null, Validators.required],
      completion_status: ['Incomplete'],
      creation_date: [formattedCreationDate, Validators.required],
      category: ['', Validators.required],
    });
  }

  close(): void {
    this.dialogRef.close();
  }

  async save(): Promise<void> {
    if (this.taskForm.invalid) {
      console.error('Form is invalid');
      return;
    }

    const taskData = this.taskForm.value;

    try {
      if (this.apiService.isPlatformBrowser()) {
        const newTask = await this.apiService.addTask(taskData);
        this.dialogRef.close(newTask);  // Return new task to the caller
      }
    } catch (error) {
      console.error('Error saving task:', error);
    }
  }
}
