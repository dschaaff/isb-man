import { Component, ViewContainerRef } from '@angular/core';
import {StateService} from "../../services/state.service";
import {ServerService} from "../../services/server.service";
import { ToastsManager } from 'ng2-toastr/ng2-toastr';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';
import { BsModalService } from 'ngx-bootstrap/modal';
import {EnvModal} from "./env.modal";
import {EnvsUtils} from "../../utils/envs.utils";
import {TraceModal} from "./trace.modal";

@Component({
  templateUrl: './servers.page.html'
})
export class ServersPage {
  public filterVisible=false;
  bsModalRef: BsModalRef;

  constructor(public state: StateService, public server: ServerService,
              private toastr: ToastsManager, vcr: ViewContainerRef, private modalService: BsModalService) {
    this.toastr.setRootViewContainerRef(vcr);

  }

  toggleFilter() {
    this.filterVisible = !this.filterVisible;
  }


  copySSH(server) {
    this.toastr.success("Copied to clipboard", "ssh "+server.address);
    return false;
  }

  showEnv(selectedServer) {
    this.server.getEnv(selectedServer).subscribe( envs => {
      this.bsModalRef.content.envs = EnvsUtils.toArray(envs);
    });

    this.bsModalRef =this.modalService.show(EnvModal, {class: 'modal-lg'} );
    this.bsModalRef.content.server = selectedServer;
  }

  showTrace(selectedServer) {
    this.server.getTrace(selectedServer).subscribe( trace => {
      this.bsModalRef.content.trace = trace;
    });

    this.bsModalRef =this.modalService.show(TraceModal, {class: 'modal-lg'} );
    this.bsModalRef.content.server = selectedServer;
  }

}
