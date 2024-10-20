import { Component, OnInit } from '@angular/core';
import {MatDialogActions, MatDialogContent, MatDialogRef, MatDialogTitle} from '@angular/material/dialog';
import { ApiService } from '../api.service'; // Adjust path as needed
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from "@angular/material/datepicker";
import {MatOption, MatSelect} from "@angular/material/select";
import {NgIf} from "@angular/common";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button"; // Import FormBuilder and Validators

@Component({
  selector: 'app-task-modal',
  standalone: true,
  templateUrl: './task-modal.component.html',
  imports: [
    MatDialogContent,
    MatFormField,
    MatDatepickerToggle,
    MatDatepicker,
    MatDatepickerInput,
    ReactiveFormsModule,
    MatSelect,
    MatOption,
    MatDialogActions,
    NgIf,
    MatInput,
    MatDialogTitle,
    MatButton,
    MatLabel,
    MatError
  ],
  styleUrls: ['./task-modal.component.css']
})
export class TaskModalComponent implements OnInit {
  taskForm!: FormGroup;  // Define a FormGroup for the task form

  constructor(
    public dialogRef: MatDialogRef<TaskModalComponent>,
    private apiService: ApiService,
    private fb: FormBuilder  // Inject FormBuilder
  ) {}

  ngOnInit(): void {
    // Initialize the task form
    this.taskForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      priority: ['LOW', Validators.required],
      completion_status: ['Incomplete']
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
