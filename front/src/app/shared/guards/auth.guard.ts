import { inject } from '@angular/core';
import { Router } from '@angular/router';
import {LoginService} from "../../login/data-access/login.service";


export const authGuard = () => {
  const loginService = inject(LoginService);
  const router = inject(Router);

  if (loginService.isAuthenticated()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
