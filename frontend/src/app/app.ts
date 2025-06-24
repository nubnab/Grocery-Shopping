import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {NavigationComponent} from './navigation/navigation.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import {AddressFormComponent} from './address-form/address-form.component';
import {TableComponent} from './table/table.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, NavigationComponent, DashboardComponent, AddressFormComponent, TableComponent],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected title = 'frontend';
}
