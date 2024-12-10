import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router, RouterLink} from '@angular/router';
import { LoginService } from '../../data-access/login.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css'],
})
export class LoginFormComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      this.loginService.login(credentials).subscribe({
        next: (token: string) => {
          console.log('Token received:', token);
          localStorage.setItem('token', token);
          localStorage.setItem('userEmail', credentials.email);
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error('Login failed:', err);
          alert('Invalid login credentials.');
        },
      });
    }
  }

}
