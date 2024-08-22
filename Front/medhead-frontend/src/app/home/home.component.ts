import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HospitalService } from '../hospital.service'; 

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  lat: number = 48.8566; // Default latitude for Paris
  lon: number = 2.3522; // Default longitude for Paris
  limit: number = 5; // Number of hospitals to retrieve
  hospitals: any[] = []; // Array to store the search results

  constructor(private hospitalService: HospitalService) {}

  searchHospitals() {
    this.hospitalService.searchHospitals(this.lat, this.lon, this.limit).subscribe({
      next: (data) => {
        this.hospitals = data; // Store the fetched data
      },
      error: (err) => {
        console.error('Failed to retrieve hospitals', err);
      }
    });
  }

  makeReservation(hospitalId: number) {
    // Implement the reservation logic here
    console.log(`Making reservation for hospital with ID: ${hospitalId}`);
  }
}
