import {Component} from "@angular/core";
import {StateService} from "../services/state.service";

@Component({
  selector: "topbar",
  templateUrl: "./topbar.html"
})
export class Topbar {
  constructor(public state: StateService) {}

  serviceName() {
    let retVal = this.state.service;
    if (retVal == null) {
      retVal = 'All';
    }
    return retVal;
  }

  hostName() {
    let retVal = this.state.hostname;
    if (retVal == null) {
      retVal = 'All';
    }
    return retVal;
  }

}
