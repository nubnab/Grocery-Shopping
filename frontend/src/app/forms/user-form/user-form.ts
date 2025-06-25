import {Component, inject, signal} from '@angular/core';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {AuthService} from '../../services/auth.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogActions, MatDialogContent, MatDialogTitle} from '@angular/material/dialog';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import PasswordValidator from '../../validators/password.validator'

@Component({
  selector: 'app-user-form',
  imports: [
    MatFormField,
    MatLabel,
    MatDialogTitle,
    MatDialogContent,
    ReactiveFormsModule,
    MatError,
    MatInput,
    MatIcon,
    MatIconButton,
    MatButton,
    MatDialogActions
  ],
  templateUrl: './user-form.html',
  styleUrl: './user-form.scss'
})
export class UserForm {

  private authService = inject(AuthService);
  private data = inject(MAT_DIALOG_DATA);
  readonly dialog = inject(MatDialog);

  isLogin: boolean = this.data.isLogin;
  loginForm: FormGroup;
  registerForm: FormGroup;

  loginPasswordHidden = signal(true);
  registerPasswordHidden = signal(true);
  confirmPasswordHidden = signal(true);

  constructor(private formBuilder: FormBuilder) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    })

    this.registerForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      telephone: ['', [Validators.required]],
      address: ['', [Validators.required]],
      password: ['', [Validators.required, PasswordValidator.minLengthValidator(6),
      Validators.maxLength(255), PasswordValidator.passwordRequirementValidator()]],
      confirmPassword: ['', [Validators.required, PasswordValidator.confirmPasswordValidator()]]
    })
  }

  toggleForm() {
    this.isLogin = !this.isLogin;
  }

  hideLoginPassword(event: MouseEvent) {
    this.loginPasswordHidden.set(!this.loginPasswordHidden());
    event.stopPropagation();
  }

  hidePassword(event: MouseEvent) {
    this.registerPasswordHidden.set(!this.registerPasswordHidden());
    event.stopPropagation();
  }

  hideConfirmPassword(event: MouseEvent) {
    this.confirmPasswordHidden.set(!this.confirmPasswordHidden());
    event.stopPropagation();
  }

  onLoginSubmit() {

  }

  onRegisterSubmit() {

  }

  hasError(errorName: string): boolean {
    const password = this.registerForm.get('password');
    return !!password?.errors?.[errorName];
  }

}
