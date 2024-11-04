import { Component, OnInit, Inject ,ChangeDetectionStrategy,signal } from '@angular/core';
import {MatDialogRef, MAT_DIALOG_DATA, MatDialogTitle, MatDialogContent, MatDialogActions} from '@angular/material/dialog';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { ApiService } from '../api.service';
import {MatError, MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {NgIf} from "@angular/common";
import {MatOption, MatSelect} from "@angular/material/select";
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle,MatDatepickerModule} from "@angular/material/datepicker";
import {MatButton} from "@angular/material/button";
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {provideNativeDateAdapter} from '@angular/material/core';
import {MatCheckbox} from "@angular/material/checkbox";
import {MatButtonToggle, MatButtonToggleGroup} from "@angular/material/button-toggle";

@Component({
  selector: 'app-task-details',
  standalone: true,
  imports: [
    MatDialogTitle,
    ReactiveFormsModule,
    MatDialogContent,
    MatFormField,
    MatInput,
    NgIf,
    MatSelect,
    MatOption,
    MatDatepickerInput,
    MatDatepicker,
    MatDatepickerToggle,
    MatDialogActions,
    MatButton,
    MatLabel,
    MatError,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatCheckbox,
    MatButtonToggle,
    MatButtonToggleGroup
  ],
  providers: [provideNativeDateAdapter()],
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './task-details.component.html',
  styleUrl: './task-details.component.css'
})
export class TaskDetailsComponent implements OnInit {
  taskForm!: FormGroup;
  isEditMode = false;
  isFormEditable = false; // New variable to control edit mode


  constructor(
    public dialogRef: MatDialogRef<TaskDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, // Injecting task data
    private fb: FormBuilder,

    private apiService: ApiService
  ) {}

  ngOnInit(): void {
    this.isEditMode = !!this.data?.task;
    this.taskForm = this.fb.group({
      id: [this.data.task?.id || null],
      title: [this.data.task?.title || '', Validators.required],
      description: [this.data.task?.description || ''],
      priority: [this.data.task?.priority || 'LOW', Validators.required],
      creation_date: [this.data.task?.creation_date || null],
      deadline: [this.data.task?.deadline || null, Validators.required],
      completion_status: [this.data.task?.completion_status, Validators.required],
      category: [this.data.task?.category || '', Validators.required],
    });
    this.taskForm.disable();
  }

  toggleEdit(): void {
    this.isFormEditable = !this.isFormEditable;
    this.isFormEditable ? this.taskForm.enable() : this.taskForm.disable();
  }


  async saveTask(): Promise<void> {
    console.log("task"+this.taskForm.value)
    if (this.taskForm.valid) {
      try {
        console.log("ahhhhhhh",this.taskForm.value)
        const updatedTask = await this.apiService.updateTask(this.taskForm.value); // Call update API
        this.dialogRef.close({ task: updatedTask, action: 'save' }); // Close dialog and pass updated task
      } catch (error) {
        console.error('Error updating task:', error);
      }
    }
  }

  delete(): void {
    console.log("button delete");
    this.dialogRef.close({ task: this.data.task, action: 'delete' });
  }

  close(): void {
    this.dialogRef.close();
  }

}
