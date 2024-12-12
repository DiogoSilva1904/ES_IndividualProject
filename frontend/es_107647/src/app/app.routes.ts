import { Routes } from '@angular/router';
import { TasklistComponent } from './tasklist/tasklist.component';
import {HomePageComponent} from "./home-page/home-page.component";
import {CallbackComponent} from "./callback/callback.component";

export const routes: Routes = [
  { path: '', component: HomePageComponent },
  {path: 'home', component: TasklistComponent},
  { path: 'callback', component: CallbackComponent },
];

