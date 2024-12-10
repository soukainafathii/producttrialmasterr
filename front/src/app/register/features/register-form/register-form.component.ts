import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { RegisterService } from '../../data-access/register.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.css'],
})
export class RegisterFormComponent {
  registerForm: FormGroup;
  successMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private registerService: RegisterService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      firstname: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      const accountData = this.registerForm.value;

      this.registerService.createAccount(accountData).subscribe({
        next: (message: string) => {
          this.successMessage = message;
          setTimeout(() => this.router.navigate(['/login']), 1000);
        },
        error: (err) => {
          if (err.status === 409) {
            alert(err.error);
          } else if (err.status === 500) {
            alert('An unexpected error occurred. Please try again later.');
          } else {
            alert('Failed to create account. Please try again.');
          }
        },
      });
    }
  }
}
