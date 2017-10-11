import { Component, ViewContainerRef } from '@angular/core';
import {StateService} from "../../services/state.service";
import {ServerService} from "../../services/server.service";
import { ToastsManager } from 'ng2-toastr/ng2-toastr';

@Component({
  templateUrl: './servers.page.html'
})
export class ServersPage {

  constructor(public state: StateService, public server: ServerService,
              private toastr: ToastsManager, vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  copySSH(server) {
    this.toastr.success("Copied to clipboard", "ssh "+server.address);
    return false;
  }

}
