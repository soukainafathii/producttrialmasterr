import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatSnackBar} from "@angular/material/snack-bar";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-contact',
  standalone: true,
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
  imports: [ReactiveFormsModule, NgIf],
})
export class ContactComponent {
  contactForm: FormGroup;

  constructor(private fb: FormBuilder, private snackBar: MatSnackBar) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]],
    });
  }

  sendContact(): void {
    if (this.contactForm.valid) {
      console.log('Contact request:', this.contactForm.value);
      this.snackBar.open('Demande de contact envoyée avec succès', 'Dismiss', {
        duration: 3000,
        verticalPosition: 'top',
        horizontalPosition: 'center',
      });
      this.contactForm.reset();
    } else {
      this.snackBar.open('Veuillez vérifier le formulaire', 'Dismiss', {
        duration: 3000,
        verticalPosition: 'top',
        horizontalPosition: 'center',
      });
    }
  }
}
