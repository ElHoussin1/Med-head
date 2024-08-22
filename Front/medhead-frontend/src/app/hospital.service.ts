import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' // This makes the service available throughout your app
})
export class HospitalService {

  private baseUrl = 'http://localhost:8080/api/hospitals'; // Your Spring Boot base URL

  constructor(private http: HttpClient) { }

  searchHospitals(lat: number, lon: number, limit: number): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem('token')}` // Add JWT token from localStorage
    });
    return this.http.get(`${this.baseUrl}/search`, {
      headers: headers,
      params: {
        lat: lat.toString(),
        lon: lon.toString(),
        limit: limit.toString()
      }
    });
  }
}
