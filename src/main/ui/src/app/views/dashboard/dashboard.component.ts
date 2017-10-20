import { Component, ViewChild } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent {
  @ViewChild("largeModal") largeModal;
  @ViewChild("smallModal") smallModal;
  value="VALUE";
  public filterVisible=false;


  constructor( ) { }

  openModal() {
    this.value = "HELP";
    this.largeModal.show();
  }

  openSmall() {
    this.value = "Small";
    this.smallModal.show();
  }

 toggleFilter() {
  this.filterVisible = !this.filterVisible;
 }

}
