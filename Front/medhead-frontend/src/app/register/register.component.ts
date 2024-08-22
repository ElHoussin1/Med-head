import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    this.authService.register(this.username, this.password).subscribe(
      response => {
        console.log('Registration successful', response);
        // Handle successful registration (e.g., navigate to login)
        this.router.navigate(['/login']);
      },
      error => {
        console.error('Registration failed', error);
        // Handle registration error (e.g., show error message)
      }
    );
  }
}