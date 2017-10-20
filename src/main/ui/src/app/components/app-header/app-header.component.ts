import { Component } from '@angular/core';
import {StateService} from "../../services/state.service";

@Component({
  selector: 'app-header',
  templateUrl: './app-header.component.html'
})
export class AppHeaderComponent {
  constructor(public state: StateService) {}

  setEnv(env) {
    this.state.setEnv(env);
    this.state.setHostname(null);
  }

  serviceName() {
    let retVal = this.state.service;
    if (retVal == null) {
      retVal = 'All';
    }
    return retVal;
  }

  setService(service) {
    this.state.setService(service);
    this.state.setHostname(null);
  }

  hostName() {
    let retVal = this.state.hostname;
    if (retVal == null) {
      retVal = 'All';
    }
    return retVal;
  }

  setHostname(hostname) {
    this.state.setHostname(hostname);
  }

}
