import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterRequest } from './register.model';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  private readonly apiUrl = 'http://localhost:8080/api/account';

  constructor(private http: HttpClient) {}

  createAccount(accountData: RegisterRequest): Observable<string> {
    return this.http.post(this.apiUrl, accountData, { responseType: 'text' });
  }
}

