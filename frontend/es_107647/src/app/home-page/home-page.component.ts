import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent {
  onButtonClick() {
    console.log('Button clicked! Redirecting to login...');

    const url = 'https://tasks-identity.auth.eu-north-1.amazoncognito.com/login?client_id=3gdohg3a8qd001knvhiuo5h84n&response_type=code&scope=email+openid&redirect_uri=http%3A%2F%2Flocalhost%3A4200%2Fcallback';

    // Redirect to the login page
    window.location.assign(url);
  }

}
