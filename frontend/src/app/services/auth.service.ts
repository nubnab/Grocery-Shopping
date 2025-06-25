import {inject, Injectable, signal} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = "localhost:8080"; //placeholder
  private router = inject(Router);
  private http = inject(HttpClient);
  private loginState = signal<boolean>(this.isLoggedIn());
  private currentUser = signal<string | null>(localStorage.getItem("username"));
  private jwtHelper = new JwtHelperService();

  get loginStateSignal() {
    return this.loginState;
  }

  get currentUserSignal() {
    return this.currentUser;
  }

  constructor() {}

  register(name: string,
          email: string,
          telephone: string,
          address: string,
        password: string): Observable<any> {
    const url = `${this.apiUrl}/register`;
    const body = {name, email, telephone, address, password};

    return this.http.post(url, body);
  }


  login(email: string, password: string): Observable<any> {
    const url = `${this.apiUrl}/register`;
    const body = {email, password};

    return this.http.post(url, body);
  }

  isLoggedIn() {
    const token = this.getAuthToken();
    if (!token) {return false;}
    return !this.isTokenExpired(token);
  }

  isTokenExpired(token: string) {
    const expiry = (JSON.parse(atob(token.split('.')[1]))).exp;
    return Math.floor(Date.now() / 1000) >= expiry;
  }

  getAuthToken(): string | null {
    return localStorage.getItem("auth_token");
  }

  getRefreshToken(): string | null {
    return localStorage.getItem("refresh_token");
  }

  refreshToken() {
    const url = `${this.apiUrl}/refresh`;
    const body = { refresh_token: this.getRefreshToken()};
    return this.http.post(url, body);
  }

  logout() {
    localStorage.removeItem("auth_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("username");

    this.loginStateSignal.set(false);
    this.currentUser.set(null);

    void this.router.navigateByUrl("/");
  }

  setAuthToken(token: string | null) {
    if (token !== null) {
      localStorage.setItem("auth_token", token);
    } else {
      this.removeAuthToken();
    }
  }

  setRefreshToken(refreshToken: string | null) {
    if (refreshToken !== null) {
      localStorage.setItem("refresh_token", refreshToken);
    } else {
      this.removeRefreshToken();
    }
  }

  removeAuthToken() {
    localStorage.removeItem("auth_token");
  }

  removeRefreshToken() {
    localStorage.removeItem("refresh_token");
  }

  getCurrentUserRoles(): string[] {
    const token = this.getAuthToken();
    if(!token || this.isTokenExpired(token)) { return [];}

    const decodedToken = this.jwtHelper.decodeToken(token);

    return decodedToken?.role || [];
  }

  isAdmin(): boolean {
    return this.getCurrentUserRoles().includes("ADMIN");
  }

}
