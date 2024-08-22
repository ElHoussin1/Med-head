import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading: boolean = false;

  constructor(private authService: AuthService, private router: Router) { }

  onSubmit() {
    this.isLoading = true;
    this.errorMessage = '';
    console.log('Attempting login with:', { username: this.username, password: this.password });
    
    this.authService.login(this.username, this.password).subscribe(
      response => {
        console.log('Login successful', response);
        localStorage.setItem('token', response.token); // Store the JWT token in localStorage
        this.router.navigate(['/home']); // Navigate to the home page
      },
      error => {
        console.error('Login failed', error);
        this.errorMessage = 'Login failed. Error: ' + JSON.stringify(error);
        this.isLoading = false;
      },
      () => {
        this.isLoading = false;
      }
    );
  }
}
